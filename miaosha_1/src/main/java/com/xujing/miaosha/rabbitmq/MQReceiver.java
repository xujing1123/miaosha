package com.xujing.miaosha.rabbitmq;

import com.xujing.miaosha.entity.MiaoshaOrder;
import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.redis.RedisService;
import com.xujing.miaosha.service.GoodsService;
import com.xujing.miaosha.service.MiaoshaService;
import com.xujing.miaosha.service.OrderService;
import com.xujing.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;


    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String message) {
        log.info("receive message:"+message);
        MiaoShaMessage miaoShaMessage = RedisService.stringToBean(message,MiaoShaMessage.class);
        MiaoshaUser user = miaoShaMessage.getUser();
        long goodsId = miaoShaMessage.getGoodsId();

        // 判断库存是否足
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() <=0){
            return;
        }
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return ;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user,goodsVo);
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);

    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:"+message);
    }


    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:"+message);
    }

    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
    public void receiveHeaders(byte [] message) {
        log.info(" haders queue message:"+new String(message));
    }

}
