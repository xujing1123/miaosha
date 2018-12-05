package com.xujing.miaosha.controller;

import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.entity.OrderInfo;
import com.xujing.miaosha.result.CodeMsg;
import com.xujing.miaosha.result.Result;
import com.xujing.miaosha.service.GoodsService;
import com.xujing.miaosha.service.MiaoshaService;
import com.xujing.miaosha.service.MiaoshaUserService;
import com.xujing.miaosha.service.OrderService;
import com.xujing.miaosha.vo.GoodsVo;
import com.xujing.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }

        long goodsId = order.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goodsVo);
        return Result.success(vo);
    }

}
