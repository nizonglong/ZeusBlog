package com.nzl.sso.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author: nizonglong
 * @date: 2020/3/17 0:09
 * @desc:
 * @version: 0.1
 **/
@Component
@Lazy(false)//启动便加载
public class SpringBeanUtils implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }


    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        SpringBeanUtils.applicationContext = arg0;
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}