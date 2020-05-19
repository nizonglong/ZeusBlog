package com.nzl.common.constant;

import lombok.NoArgsConstructor;

/**
 * @author: nizonglong
 * @date: 2020/3/16 13:58
 * @desc: 常量
 * @version: 0.1
 **/
@NoArgsConstructor
public class Constant {

    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * redis-key-前缀-register:code:
     */
    public static final String PREFIX_REGISTER_CODE = "register:code:";

    /**
     * JWT-email:
     */
    public static final String EMAIL = "email";

    /**
     * JWT-currentTimeMillis:
     */
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 16;

    /**
     * 默认 page size 15
     */
    public static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * ZEUS_TOKEN
     */
    public static final String ZEUS_TOKEN = "ZEUS_TOKEN";

    /**
     * ZEUS_UID
     */
    public static final String ZEUS_UID = "ZEUS_UID";
    public static final String VISITOR_UID = "VISITOR_UID";
    public static final String VISITOR_FLAG = "VISITOR_FLAG";

    /**
     * SSO 一些通用路径
     */
    public static final String SSO_BASE_URL = "http://localhost:8081";
    public static final String SSO_USER_TOKEN = "/user/token/";
    public static final String SSO_PAGE_LOGIN = "/login";
    public static final String SSO_REDIS_SET = "/user/redisSet";


    /**
     * REDIS_ARTICLE_KEY
     */
    public static final String REDIS_ARTICLE_KEY = "REDIS_ARTICLE_KEY";
    public static final String REDIS_USER_SESSION_KEY = "REDIS_USER_SESSION";
    public static final Integer SSO_SESSION_EXPIRE = 18000;
}
