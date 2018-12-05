package com.xujing.miaosha.dao;

import com.xujing.miaosha.entity.MiaoshaOrder;
import com.xujing.miaosha.entity.OrderInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where USER_ID = #{userId} and GOODS_ID = #{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    //@SelectKey(keyProperty = "id", keyColumn = "id", resultType=long.class, before=false, statement="select last_insert_id()")
    @Insert("insert into order_info(USER_ID,GOODS_ID,ADDRESS_ID,GOODS_NAME,GOODS_COUNT,GOODS_PRICE,ORDER_CHANNEL,STATUS,CREATE_DATE) " +
            "values(#{userId}, #{goodsId},#{addressId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate})")
    @Options(useGeneratedKeys=true,keyColumn="id")
    public Integer insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(USER_ID,ORDER_ID,GOODS_ID) values(#{userId}, #{orderId}, #{goodsId})")
    public void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")Long orderId);

}
