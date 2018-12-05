package com.xujing.miaosha.controller;

import com.xujing.miaosha.entity.MiaoshaOrder;
import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.entity.OrderInfo;
import com.xujing.miaosha.rabbitmq.MQSender;
import com.xujing.miaosha.rabbitmq.MiaoShaMessage;
import com.xujing.miaosha.redis.GoodsKey;
import com.xujing.miaosha.redis.RedisService;
import com.xujing.miaosha.result.CodeMsg;
import com.xujing.miaosha.result.Result;
import com.xujing.miaosha.service.GoodsService;
import com.xujing.miaosha.service.MiaoshaService;
import com.xujing.miaosha.service.MiaoshaUserService;
import com.xujing.miaosha.service.OrderService;
import com.xujing.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {


    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if(null != goodsVoList && goodsVoList.size() >0){
            for(GoodsVo goods : goodsVoList) {
                redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getStockCount());
                localOverMap.put(goods.getId(),false);
            }
        }
    }

    @RequestMapping("doMiaosha2")
    public String doMiaosha2(Model model, MiaoshaUser user,
                            @RequestParam("goodsId") Long goodsId){
        if(null == user){
            return "login";
        }
        // 判断库存是否足
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() <=0){
            model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(null != order){
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        // 秒杀开始
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
        model.addAttribute("goods", goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("user", user);
        return "order_detail";
    }

    /**
     * 优化之后的
     * */
    @RequestMapping(value = "doMiaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> doMiaosha(HttpServletRequest request, HttpServletResponse response,
                                       Model model, MiaoshaUser user,
                                       @RequestParam("goodsId") Long goodsId){
        if(null == user){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 内存标记，减少redis的访问
        boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);//10
        if(stock < 0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(null != order){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        // 入队
        MiaoShaMessage message = new MiaoShaMessage();
        message.setGoodsId(goodsId);
        message.setUser(user);
        mqSender.sendMiaoshaMessage(message);

        return Result.success(0); // 排队中

        /*// 判断库存是否足
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (goodsVo.getStockCount() <=0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(null != order){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        // 秒杀开始
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
        return Result.success(orderInfo);*/
    }

    /**
     *   * orderId：成功
     *   * -1：秒杀失败
     *   * 0： 排队中
     * */
    @RequestMapping(value = "result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoShaResult(HttpServletRequest request, HttpServletResponse response,
                                     Model model, MiaoshaUser user,
                                     @RequestParam("goodsId") Long goodsId){
        if(null == user){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 判断是否秒杀成功
       long result = miaoshaService.getMiaoShaResult(user.getId(),goodsId);
        System.out.println(Result.success(result));
       return Result.success(result); // 排队中
    }




}
