package com.xujing.miaosha.service;

import com.xujing.miaosha.dao.GoodsDao;
import com.xujing.miaosha.entity.MiaoshaGoods;
import com.xujing.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    private static Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    GoodsDao goodsDao;


    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }
    public GoodsVo getGoodsVoByGoodsId(long goodsId){
        return  goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goodsVo){
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(goodsVo.getId());
        int result = goodsDao.reduceStock(goods);
        //log.info("result:"+result);
        return result > 0;
    }
}
