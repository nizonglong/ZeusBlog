package com.nzl.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author nizonglong
 */
@Configuration
@SpringBootApplication
@ComponentScan("com.nzl")
@tk.mybatis.spring.annotation.MapperScan("com.nzl.dao")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
