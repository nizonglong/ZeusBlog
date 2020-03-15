package com.nzl.server.service.impl;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.server.pojo.MailBean;
import com.nzl.server.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.nzl.common.constant.MailConstants.MAIL_SEND_FAILED;

/**
 * @classname: EmailServiceImpl
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 20:08
 * @version: 1.0
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * 邮件发送者
     */
    @Value("${lance.mail.sender}")
    private String mailSender;

    @Override
    public ZeusBlogResult sendSimpleMail(MailBean mailBean) throws Exception {
        ZeusBlogResult result;
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(mailSender);
            //邮件接收人
            simpleMailMessage.setTo(mailBean.getRecipient());
            //邮件主题
            simpleMailMessage.setSubject(mailBean.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailBean.getContent());
            javaMailSender.send(simpleMailMessage);

            result = ZeusBlogResult.ok("邮件发送成功");
        } catch (Exception e) {
            log.error(MAIL_SEND_FAILED, e.getMessage());
            e.printStackTrace();
            throw new Exception("邮件发送失败");
        }

        return result;
    }

}
