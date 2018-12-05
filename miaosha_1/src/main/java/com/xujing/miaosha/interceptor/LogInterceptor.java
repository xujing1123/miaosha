package com.xujing.miaosha.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进入controller层之前拦截请求
 * 继承HandlerInterceptor接口
 * 说明：
 * 1. preHandle 表示方法请求前的处理，若其返回值为FALSE，就中断请求目标方法了，只有返回值为TRUE时才继续执行方法内容。
 * 2. postHandle 是在目标方法执行完之后执行的。
 * */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("============== log request  ==============");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("============== log request completion  ==============");
    }
}
