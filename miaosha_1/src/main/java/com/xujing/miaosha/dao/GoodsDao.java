package com.xujing.miaosha.dao;

import com.xujing.miaosha.entity.MiaoshaGoods;
import com.xujing.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(Long goodsId);

    @Update("update miaosha_goods set STOCK_COUNT = STOCK_COUNT-1 where GOODS_ID = #{goodsId} and STOCK_COUNT > 0")
    public int reduceStock(MiaoshaGoods goods);

}
