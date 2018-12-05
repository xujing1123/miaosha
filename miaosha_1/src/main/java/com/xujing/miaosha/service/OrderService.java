package com.xujing.miaosha.service;

import com.xujing.miaosha.dao.OrderDao;
import com.xujing.miaosha.entity.MiaoshaOrder;
import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.entity.OrderInfo;
import com.xujing.miaosha.redis.OrderKey;
import com.xujing.miaosha.redis.RedisService;
import com.xujing.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId,Long goodsId){
       /* MiaoshaOrder order = redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
        if(null != order ){
            return order;
        }
        order = orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        if(null != order){
            redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, order);
        }*/
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
    }

    public OrderInfo getOrderById(Long orderId){
        return orderDao.getOrderById(orderId);
    }

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setAddressId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+user.getId()+"_"+goodsVo.getId(), miaoshaOrder);

        return  orderInfo;
    }

}
