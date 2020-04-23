package com.nzl.web.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.CookieUtils;
import com.nzl.common.util.HttpClientUtil;
import com.nzl.common.util.JsonUtils;
import com.nzl.model.dto.UserDto;
import com.nzl.web.service.WebUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static com.nzl.common.constant.Constant.REDIS_USER_SESSION_KEY;
import static com.nzl.common.constant.Constant.SSO_SESSION_EXPIRE;


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

    @PostMapping("/update")
    public ZeusResponseBean updateUser(@RequestBody UserDto user) {
        try {
            userService.updateUser(user);
            // update redis
            UserDto userDto = userService.getUser(user.getUid());

            Map<String, String> map = new HashMap<>(2);
            map.put("key", REDIS_USER_SESSION_KEY + ":" + user.getToken());
            map.put("value", JsonUtils.objectToJson(userDto));
            map.put("time", SSO_SESSION_EXPIRE.toString());
            HttpClientUtil.doPost(Constant.SSO_BASE_URL + Constant.SSO_REDIS_SET + "/1", map);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "exception");
        }
        return ZeusResponseBean.ok();
    }

    @GetMapping("/checkUpdateData/{data}/{type}")
    public ZeusResponseBean checkUpdateData(@PathVariable String data, @PathVariable int type) {
        return userService.checkUpdateData(data, type);
    }

}
