package com.nzl.sso.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.exception.ZeusUnauthorizedException;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.AesCipherUtil;
import com.nzl.common.util.ExceptionUtil;
import com.nzl.model.dto.UserDto;
import com.nzl.sso.service.SsoUserService;
import com.nzl.sso.util.JedisUtil;
import com.nzl.sso.util.JwtUtil;
import com.nzl.sso.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:10
 * @desc: 登录控制类
 * @version: 0.1
 **/
@RestController
public class LoginController {

    @Resource
    private SsoUserService userService;

    /**
     * 登录授权
     *
     * @param userDto
     * @return com.wang.model.common.ZeusResponseBean
     * @author dolyw.com
     * @date 2018/8/30 16:21
     */
    @PostMapping("/doLogin")
    public ZeusResponseBean login(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            return userService.userLogin(userDto.getEmail(), userDto.getPassword(), request, response);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "登录失败(unknown error)");
        }
    }

    @RequestMapping("/token/{token}")
    public Object getUserByToken(@PathVariable String token) {
        ZeusResponseBean result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtil.getStackTrace(e));
        }
        return result;
    }
}
