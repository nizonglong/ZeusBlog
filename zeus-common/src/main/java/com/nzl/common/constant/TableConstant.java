package com.nzl.common.constant;

/**
 * @classname: TableConstant
 * @description: 数据库表的常量类
 * @author: nizonglong
 * @date: 2019/7/30 14:12
 * @version: 1.0
 */
public class TableConstant {
    /**
     * 数据库表常量
     */
    public static final String USER_TABLE = "user";
    public static final String ROLE_TABLE = "role";
    public static final String PERMISSION_TABLE = "permission";
    public static final String PARENT_TYPE_TABLE = "parent_type";
    public static final String BLOG_TYPE_TABLE = "blog_type";
    public static final String USER_TYPE_TABLE = "user_type";
    public static final String ARTICLE_BLOG_TABLE = "article_blog";
    public static final String NOTICE_TABLE = "notice";
    public static final String PHOTO_TABLE = "photo";
    public static final String USER_ROLE_TABLE = "user_role";
    public static final String ROLE_PERMISSION_TABLE = "role_permission";
    public static final String NOTICE_FILE_TABLE = "notice_file";


    public static final String USER_STATUS_NORMAL = "正常";
    public static final String USER_STATUS_UNACTIVE =  "未激活";
    public static final String USER_STATUS_BANNER =  "封禁";


    public static final String IP = "localhost";
    public static final int TOMCAT_PORT_8080 =8080;
    public static final String ACTIVE_HOST = IP + ":" + TOMCAT_PORT_8080 + "/" + "activeUser?activeCode=";

    public static final String CURRENT_USER = "current_user";


    public static final String DEFAULT_PORTRAIT = "default.jpg";

    /**
     * 本地上传
     */
    public static final String LOCAL_IP = "http://localhost";
    public static final String LOCAL_ROOT_DIR = "G:/Curriculum design/BigDataManager/src/main/resources/static/tjupload";
    public static final String DEFAULT_LOCAL_PORTAL_DIR = "upload/portrait";
}
