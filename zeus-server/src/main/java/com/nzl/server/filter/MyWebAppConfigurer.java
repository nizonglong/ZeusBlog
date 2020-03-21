package com.nzl.server.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @classname: MyWebAppConfigurer
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 14:59
 * @version: 1.0
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //addPathPatterns 用于添加拦截规则
//        //excludePathPatterns 用于排除拦截
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/index") // 主页
//                .excludePathPatterns("/user/login"); //用户登录
//    }

}