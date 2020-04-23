package com.nzl.sso.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.ExceptionUtil;
import com.nzl.model.dto.UserDto;
import com.nzl.sso.service.SsoUserService;
import com.nzl.sso.util.JedisUtil;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/user")
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

    @RequestMapping("/redisSet/{type}")
    public Object setJsonRedis(String key, String value, int time, @PathVariable int type) {
        ZeusResponseBean result = null;
        if (type == 0) {
            type = 1;
        }
        try {
            if (1 == type) {
                JedisUtil.setJson(key, value, time);
            } else {
                JedisUtil.setObject(key, value, time);
            }
            result = ZeusResponseBean.ok();
        } catch (Exception e) {
            e.printStackTrace();
            result = ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtil.getStackTrace(e));
        }
        return result;
    }

}
