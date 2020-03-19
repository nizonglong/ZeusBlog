package com.nzl.sso;

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
public class SsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoApplication.class, args);
	}

}
