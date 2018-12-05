package com.xujing.miaosha.service;

import com.xujing.miaosha.entity.MiaoshaOrder;
import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.entity.OrderInfo;
import com.xujing.miaosha.redis.MiaoshaKey;
import com.xujing.miaosha.redis.RedisService;
import com.xujing.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    private static Logger log = LoggerFactory.getLogger(MiaoshaService.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo){
        /**
         * 1. 减库存
         * 2.下订单
         * 3.写入秒杀订单
         * */
        //log.info(goodsVo.toString());
        boolean success = goodsService.reduceStock(goodsVo);
        log.info("判断是否成功！"+success);
        if(success){
            return orderService.createOrder(user,goodsVo);
        }else {
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }


    public long getMiaoShaResult(Long userId,Long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        if (null != order){ // 秒杀成功
            return order.getId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    public void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    public boolean getGoodsOver(Long goodsId) {
       return redisService.exists(MiaoshaKey.isGoodsOver, "" + goodsId);
    }

}
