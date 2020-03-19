package com.nzl.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;

/**
 * @author: nizonglong
 * @date: 2020/3/17 17:02
 * @desc: 邮件工具类
 * @version: 0.1
 **/
@Component
public class MailUtil {

    @Resource
    private JavaMailSenderImpl mailSender;

    /**
     * JavaMailSenderImpl支持MimeMessages和SimpleMailMessages。
     * MimeMessages为复杂邮件模板，支持文本、附件、html、图片等。
     * SimpleMailMessages实现了MimeMessageHelper，为普通邮件模板，支持文本
     */
//    @Autowired
//    private SimpleMailMessage simpleMailMessage;
//
//    @Autowired
//    private MimeMessage mimeMessage;

    /**
     * 单发
     *
     * @param recipient 收件人
     * @param subject   主题
     * @param content   内容
     */
    public void singleSend(String recipient, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        System.out.println(simpleMailMessage);
        simpleMailMessage.setFrom(mailSender.getUsername());
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 富文本单发
     *
     * @param recipient 收件人
     * @param subject   主题
     * @param content   内容,可以是富文本
     */
    public void singleHtmlSend(String recipient, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        System.out.println(mimeMessage);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(mailSender.getUsername());
        mimeMessageHelper.setTo(recipient);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        mailSender.send(mimeMessage);
    }

    /**
     * 群发
     *
     * @param recipients 收件人
     * @param subject    主题
     * @param content    内容
     */
    public void multiSend(List<String> recipients, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailSender.getUsername());
        simpleMailMessage.setTo(recipients.toArray(new String[0]));
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 发送带附件的邮件
     *
     * @param recipient
     * @param subject
     * @param content
     * @param file      void
     * @throws
     * @author malizhi
     * @version 1.0
     */
    public void sendWithFile(String recipient, String subject, String content, MultipartFile file) {
        //使用JavaMail的MimeMessage，支持更加复杂的邮件格式和内容
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            //创建MimeMessageHelper对象，处理MimeMessage的辅助类
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            //使用辅助类MimeMessage设定参数
            helper.setFrom(Objects.requireNonNull(mailSender.getUsername()));
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            //加入附件
            helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 发送邮件
        mailSender.send(msg);
    }
}
