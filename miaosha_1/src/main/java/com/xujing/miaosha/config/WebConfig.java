package com.xujing.miaosha.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xujing.miaosha.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter{
	
	@Autowired
	UserArgumentResolver userArgumentResolver;

	/**
	 * 配置使用FastJson返回Json视图
	 *
	 * FastJson SerializerFeatures
	 * WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
	 * WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
	 * DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
	 * WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
	 * WriteMapNullValue：是否输出值为null的字段,默认为false。
	 *
	 * */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		// 创建FastJson消息转换器
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 创建配置类
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// 修改配置返回内容的过滤
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.DisableCircularReferenceDetect, //消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
				SerializerFeature.WriteMapNullValue, // 是否输出值为null的字段,默认为false。
				SerializerFeature.WriteNullStringAsEmpty);

		// 中文乱码解决方案
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);//设定json格式且编码为UTF-8
		fastConverter.setSupportedMediaTypes(mediaTypes);

		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 将FastJson添加到视图转换器列表内
		converters.add(fastConverter);
	}

	/**
	 * 自定参数解析器
	 * */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}

	/**
	 * 关键，将拦截器作为bean写入配置中
	 * 在addInterceptors方法中需要使用此方法，不然UserInterceptor注入的service是空的
	 * */
	@Bean
	public UserInterceptor userInterceptor() {
		return new UserInterceptor();
	}

	/**
	 * resources配置
	 * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
	 * @param registry
	 * */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/").addResourceLocations("/**");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 拦截器
	 * 注册自定义拦截器，添加拦截路径和排除拦截路径
	 * registry.addInterceptor(new InterceptorConfig()).addPathPatterns("api/path/**").excludePathPatterns("api/path/login");
	 * */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(userInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
		//registry.addInterceptor(userInterceptor());
		//registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
		// registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
		/**
		 * <mvc:interceptors>
		 *     <bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor"/>
		 *     <mvc:interceptor>
		 *         <mvc:mapping path="/**"/>
		 *         <mvc:exclude-mapping path="/login/**"/>
		 *         <bean class="org.springframework.web.servlet.theme.ThemeChangeInterceptor"/>
		 *     </mvc:interceptor>
		 * </mvc:interceptors>
		 * */
		super.addInterceptors(registry);
	}

	/**
	    * 配置servlet处理
	    */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable(); // 默认Servlet
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
		//configurer.mediaType("json", MediaType.APPLICATION_JSON);
		//configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}



}
