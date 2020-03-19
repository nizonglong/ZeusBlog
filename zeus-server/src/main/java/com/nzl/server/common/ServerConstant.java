package com.nzl.server.common;

/**
 * @author: nizonglong
 * @date: 2020/3/19 15:05
 * @desc: 常量
 * @version: 0.1
 **/
public class ServerConstant {

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
