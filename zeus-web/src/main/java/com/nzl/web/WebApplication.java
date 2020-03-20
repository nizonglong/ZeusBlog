package com.nzl.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @classname: WebApplication
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 15:09
 * @version: 1.0
 */
@Configuration
@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.nzl.dao")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
