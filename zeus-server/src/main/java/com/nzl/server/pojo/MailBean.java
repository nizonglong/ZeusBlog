package com.nzl.server.pojo;

import lombok.Data;

/**
 * @classname: MailBean
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 15:43
 * @version: 1.0
 */
@Data
public class MailBean {
    /**
     * 邮件接收人
     */
    private String recipient;
    /**
     * 收件人昵称
     */
    private String nickname;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
}
