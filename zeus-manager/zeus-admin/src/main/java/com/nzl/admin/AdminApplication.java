package com.nzl.admin;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configurable
@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.nzl.dao")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
