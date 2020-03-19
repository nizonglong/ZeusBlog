package com.nzl.sso.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.exception.ZeusUnauthorizedException;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.AesCipherUtil;
import com.nzl.model.dto.UserDto;
import com.nzl.sso.service.SsoUserService;
import com.nzl.sso.util.JedisUtil;
import com.nzl.sso.util.JwtUtil;
import com.nzl.sso.util.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:10
 * @desc: 登录控制类
 * @version: 0.1
 **/
@RestController
public class LoginController {
    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Resource
    private UserUtil userUtil;

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
    public ZeusResponseBean login(@RequestBody UserDto userDto, HttpServletResponse httpServletResponse) {
        // 查询数据库中的帐号信息
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setEmail(userDto.getEmail());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp == null) {
            throw new ZeusUnauthorizedException("该帐号不存在(The account does not exist.)");
        }
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以邮箱+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(userDto.getEmail() + userDto.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userDto.getEmail())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userDto.getEmail());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getEmail(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userDto.getEmail(), currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return ZeusResponseBean.ok("登录成功(Login Success.)");
        } else {
            throw new ZeusUnauthorizedException("帐号或密码错误(Account or Password Error.)");
        }
    }

}
