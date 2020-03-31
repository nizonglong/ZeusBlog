package com.nzl.sso.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:25
 * @desc:
 * @version: 0.1
 **/
public interface SsoUserService {
    /**
     * 注册时用于检测数据
     *
     * @param content 要检测的数据
     * @param type    数据类型:手机号码、用户名、邮箱
     * @return ZeusBlogResult
     */
    ZeusResponseBean checkData(String content, Integer type);

    /**
     * 发送验证码邮件
     *
     * @param email
     * @return
     */
    ZeusResponseBean sendActiveCode(String email);

    /**
     * 创建用户
     *
     * @param userDto
     * @return
     */
    ZeusResponseBean creatUser(UserDto userDto);

    /**
     * 邮箱登录接口
     *
     * @param email
     * @param password
     * @param request
     * @param response
     * @return
     */
    ZeusResponseBean userLogin(String email, String password,
                               HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    ZeusResponseBean getUserByToken(String token);

    UserDto selectOne(UserDto userDto);
}
