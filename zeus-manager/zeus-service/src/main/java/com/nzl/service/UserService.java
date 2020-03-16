package com.nzl.service;


import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.pojo.User;
import com.nzl.server.pojo.MailBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @classname: UserService
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 14:08
 * @version: 1.0
 */
public interface UserService {

    /**
     * 注册时用于检测数据
     *
     * @param content 要检测的数据
     * @param type    数据类型:手机号码、用户名、邮箱
     * @return ZeusBlogResult
     */
    ZeusResponseBean checkData(String content, Integer type);

    /**
     * 修改个人信息时用于检测数据
     *
     * @param content 要检测的数据
     * @param type    数据类型:手机号码、用户名
     * @return ZeusBlogResult
     */
    ZeusResponseBean checkUpdateData(String content, Integer type);

    /**
     * 发送激活邮件
     *
     * @param mailBean 邮件bean
     * @param request  http请求
     * @return ZeusBlogResult
     */
    ZeusResponseBean sendActiveEmail(MailBean mailBean, HttpServletRequest request);

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return ZeusBlogResult
     */
    ZeusResponseBean createUser(User user);

    /**
     * 更新用户头像
     * @param head 用户头像文件
     * @return ZeusBlogResult
     */
    ZeusResponseBean updatePortrait(MultipartFile head);


    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return User
     */
    User getUserByEmail(String email);


    /**
     * 更新用户信息
     *
     * @param user 用户类User
     * @return int
     */
    int updateUser(User user);

    /**
     * 通过用户id查询用户所有信息
     *
     * @param uid 用户id
     * @return User
     */
    User queryUserById(String uid);


}
