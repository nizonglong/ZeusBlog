package com.nzl.sso.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.UserDto;
import com.nzl.sso.service.SsoUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Resource
    private SsoUserService userService;

    /**
     * 新增游客
     */
    @PostMapping("/login")
    public ZeusResponseBean login(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response) {

        try {
            return userService.loginVisitor(userDto.getUid(), userDto.getPassword(), request, response);
        } catch (Exception e) {
            e.printStackTrace();
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "游客失败(Visitor Failure)");
        }
    }

    /**
     * 新增游客
     */
    @PostMapping("/register")
    public ZeusResponseBean register() {

        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setPassword("123456");

        try {
            return userService.createVisitor(userDtoTemp);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "游客失败(Visitor Failure)");
        }
    }
}
