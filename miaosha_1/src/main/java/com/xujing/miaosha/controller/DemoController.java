package com.xujing.miaosha.controller;

import com.xujing.miaosha.entity.User;
import com.xujing.miaosha.rabbitmq.MQSender;
import com.xujing.miaosha.result.CodeMsg;
import com.xujing.miaosha.result.Result;
import com.xujing.miaosha.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController
{

    private static Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    UserService userService;

    @Autowired
    MQSender mqSender;

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mqhome(){
        mqSender.sendMiaoshaMessage("hello world!");
        return Result.success("hello world!");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
        mqSender.sendTopic("hello,imooc");
        return Result.success("Hello，world");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
        mqSender.sendFanout("hello,imooc fanout");
        return Result.success("Hello，world fanout");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendHeader("hello,imooc header");
        return Result.success("Hello，world header");
    }


    @RequestMapping("/")
    @ResponseBody
    public String home(){
        log.info("info测试");
        log.error("error test!");
        return "hello world!";
    }

    // 1.rest api json 输出；2.页面
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello world!");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError(){
        return Result.error(CodeMsg.SERVER_ERROE);
    }

    @RequestMapping("/templates")
    public String thymeleaf(Model model){
        model.addAttribute("name","xujing");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(Model model, Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Integer> dbTx(Model model, Integer id){
        userService.tx();
        return Result.success(id);
    }


    @RequestMapping("/db/list")
    @ResponseBody
    public Result<List<User>> dbGet(Model model){
        List<User> users = userService.findAll();
        return Result.success(users);
    }



}
