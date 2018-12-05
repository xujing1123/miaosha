package com.xujing.miaosha.config;

import com.xujing.miaosha.constant.LoginConstant;
import com.xujing.miaosha.entity.MiaoshaUser;
import com.xujing.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定参数解析器
 * 实现HandlerMethodArgumentResolver接口
 *
 * */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	MiaoshaUserService userService;

	/**
	 * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument
	 * */
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz==MiaoshaUser.class;
	}

	/**
	 * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象
	 * */
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		
		String paramToken = request.getParameter(LoginConstant.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request, LoginConstant.COOKI_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return userService.getByToken(response, token);
	}

	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if (null != cookies && cookies.length > 0){
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookiName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
