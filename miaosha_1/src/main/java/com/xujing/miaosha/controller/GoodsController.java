package com.xujing.miaosha.controller;

import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.redis.GoodsKey;
import com.xujing.miaosha.redis.RedisService;
import com.xujing.miaosha.result.Result;
import com.xujing.miaosha.service.GoodsService;
import com.xujing.miaosha.service.MiaoshaUserService;
import com.xujing.miaosha.vo.GoodsDetailVO;
import com.xujing.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

/*    @RequestMapping("/toList")
    public String toList(Model model,MiaoshaUser user){
        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("user", user);
        return "goods_list";
    }*/
    /**
     * 2.3压力测试
     *
     * */
    @RequestMapping(value = "/toList",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        //从缓存中取值
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //return "goods_list";
        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        // 手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }

/*    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0; // 计算多少秒结束
        if(now <startAt){ // 活动未开始
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt-now)/1000);
        }else if( now> endAt) // 活动结束
        {
            miaoshaStatus =2;
            remainSeconds = -1;
        }else  // 正常秒杀
        {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("user", user);
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }*/

    /**
     * 页面缓存
     *
     * */
    @RequestMapping(value = "/to_detail2/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response,Model model,MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId) {
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0; // 计算多少秒结束
        if(now <startAt){ // 活动未开始
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt-now)/1000);
        }else if( now> endAt) // 活动结束
        {
            miaoshaStatus =2;
            remainSeconds = -1;
        }else  // 正常秒杀
        {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("user", user);
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        // 手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }
        return html;
        //return "goods_detail";
    }

    /**
     * 前后端分离
     *
     * */
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVO> detail(HttpServletRequest request, HttpServletResponse response,Model model,MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0; // 计算多少秒结束
        if(now <startAt){ // 活动未开始
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt-now)/1000);
        }else if( now> endAt) // 活动结束
        {
            miaoshaStatus =2;
            remainSeconds = -1;
        }else  // 正常秒杀
        {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVO detailVO = new GoodsDetailVO();
        detailVO.setUser(user);
        detailVO.setGoods(goods);
        detailVO.setMiaoshaStatus(miaoshaStatus);
        detailVO.setRemainSeconds(remainSeconds);
        return Result.success(detailVO);
    }

    @GetMapping("/getById/{goodsId}")
    public ResponseEntity<GoodsVo> getById(@PathVariable("goodsId") Integer goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        String version = UUID.randomUUID().toString();
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .eTag(version).body(goods);
    }

}
