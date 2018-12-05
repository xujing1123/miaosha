package com.xujing.miaosha.controller;

import com.xujing.miaosha.entity.User;
import com.xujing.miaosha.redis.RedisService;
import com.xujing.miaosha.redis.UserKey;
import com.xujing.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/get")
    @ResponseBody
    public Result<User> redisGet(Model model){
        User user = redisService.get(UserKey.getById,"1",User.class);
        return Result.success(user);
    }

    @RequestMapping("/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user  = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, ""+1, user);//UserKey:id1
        return Result.success(true);
    }
}
