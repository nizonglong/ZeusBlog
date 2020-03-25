package com.nzl.admin.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
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
public class AdminWebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }

}