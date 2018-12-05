package com.xujing.miaosha.interceptor;

import com.xujing.miaosha.constant.LoginConstant;
import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * 进入controller层之前拦截请求
 * 继承HandlerInterceptor接口
 * 说明：
 * 1. preHandle 表示方法请求前的处理，若其返回值为FALSE，就中断请求目标方法了，只有返回值为TRUE时才继续执行方法内容。
 * 2. postHandle 是在目标方法执行完之后执行的。
 * */
//@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    MiaoshaUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("==============  request before  ==============");
        // 判断用户是否为空
        String paramToken = request.getParameter(LoginConstant.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, LoginConstant.COOKI_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return false;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        MiaoshaUser user = userService.getByToken(response, token);
        if(user == null) {
            PrintWriter printWriter = response.getWriter();
            printWriter.write("{code:0,message:\"session is invalid,please login again!\"}");
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("==============  request handle==============");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("==============  request completion  ==============");
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[]  cookies = request.getCookies();
        if (null != cookies && cookies.length > 0){
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
