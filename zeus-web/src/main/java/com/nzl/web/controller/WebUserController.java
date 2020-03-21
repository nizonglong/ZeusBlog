package com.nzl.web.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.CookieUtils;
import com.nzl.web.service.WebUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: nizonglong
 * @date: 2020/3/21 21:48
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/user")
public class WebUserController {
    @Resource
    private WebUserService userService;

    @GetMapping("/getUser")
    public ZeusResponseBean getUser(HttpServletRequest request) {
        //判断用户是否登录
        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, Constant.ZEUS_TOKEN);

        return ZeusResponseBean.ok(userService.getUserByToken(token));
    }

}
