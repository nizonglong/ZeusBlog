package com.nzl.common.constant;

/**
 * @classname: MailConstants
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 15:44
 * @version: 1.0
 */
public class MailConstant {
    public static final String REGISTER_SUBJECT = "ZeusBlog 注册邮件";

    public static String registerContent(String email, String code) {
        return "欢迎注册ZeusBlog<br><br>" +
                "您的ZeusBlog注册验证码为: " +
                "<b>" + code + "</b>" + "，请在 10 分钟内使用。<br><br>" +
                "(这是一封系统自动发送的邮件，请勿回复)";
    }

    /**
     * 注册验证码持续时间，10分钟 = 600
     */
    public static final int TIME_10_MINS = 600;
}

