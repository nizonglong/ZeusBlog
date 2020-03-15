package com.nzl.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests {
    /**
     * 邮件发送者
     */
    @Value("${lance.mail.sender}")
    private String mailSender;


    /**
     * 配置文件注入值
     */
    @Value("${upload.path.portrait}")
    private String UPLOAD_PATH_PORTRAIT;


    @Test
    public void contextLoads() {
        System.out.println(mailSender);
        System.out.println(UPLOAD_PATH_PORTRAIT);
    }


}
