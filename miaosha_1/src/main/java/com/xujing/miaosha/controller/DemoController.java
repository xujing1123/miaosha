package com.xujing.miaosha.controller;

import com.xujing.miaosha.result.CodeMsg;
import com.xujing.miaosha.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController
{

    @RequestMapping("/")
    @ResponseBody
    public String home(){
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
}
