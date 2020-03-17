package com.nzl.sso.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.exception.ZeusException;
import com.nzl.common.exception.ZeusUnauthorizedException;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.AesCipherUtil;
import com.nzl.common.util.StringUtil;
import com.nzl.model.dto.UserDto;
import com.nzl.sso.service.SsoUserService;
import com.nzl.sso.util.JedisUtil;
import com.nzl.sso.util.JwtUtil;
import com.nzl.sso.util.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
    @PostMapping("/login")
    public ZeusResponseBean login(@RequestBody UserDto userDto, HttpServletResponse httpServletResponse) {
        // 查询数据库中的帐号信息
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setUsername(userDto.getUsername());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp == null) {
            throw new ZeusUnauthorizedException("该帐号不存在(The account does not exist.)");
        }
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(userDto.getUsername() + userDto.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userDto.getUsername())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userDto.getUsername());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getUsername(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userDto.getUsername(), currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new ZeusResponseBean(HttpStatus.OK.value(), "登录成功(Login Success.)", null);
        } else {
            throw new ZeusUnauthorizedException("帐号或密码错误(Account or Password Error.)");
        }
    }

    /**
     * 测试登录
     *
     * @param
     * @return com.wang.model.common.ZeusResponseBean
     * @author dolyw.com
     * @date 2018/8/30 16:18
     */
    @GetMapping("/article")
    public ZeusResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
        if (subject.isAuthenticated()) {
            return new ZeusResponseBean(HttpStatus.OK.value(), "您已经登录了(You are already logged in)", null);
        } else {
            return new ZeusResponseBean(HttpStatus.OK.value(), "你是游客(You are guest)", null);
        }
    }

    /**
     * 新增用户
     *
     * @param userDto
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    @PostMapping("/addOne")
//    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public ZeusResponseBean add(@RequestBody UserDto userDto) {
        // 判断当前帐号是否存在
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setUsername(userDto.getUsername());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp != null && StringUtil.isNotBlank(userDtoTemp.getPassword())) {
            throw new ZeusUnauthorizedException("该帐号已存在(Account exist.)");
        }
        userDto.setJoinTime(new Date());
        // 密码以帐号+密码的形式进行AES加密
        if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            throw new ZeusException("密码最多8位(Password up to 8 bits.)");
        }
        String key = AesCipherUtil.enCrypto(userDto.getUsername() + userDto.getPassword());
        userDto.setPassword(key);
        int count = userService.insert(userDto);
        if (count <= 0) {
            throw new ZeusException("新增失败(Insert Failure)");
        }
        return new ZeusResponseBean(HttpStatus.OK.value(), "新增成功(Insert Success)", userDto);
    }
}
