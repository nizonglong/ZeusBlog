package com.nzl.sso.util;

import com.nzl.common.constant.Constant;
import com.nzl.common.exception.ZeusException;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/17 1:30
 * @desc: 获取当前登录用户工具类
 * @version: 0.1
 **/
@Component
public class UserUtil {
    @Resource
    private UserMapper userMapper;

    /**
     * 获取当前登录用户
     *
     * @param
     * @return UserDto
     * @author nizonglong
     * @date 2019/3/15 11:48
     */
    public UserDto getUser() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得 email
        String email = JwtUtil.getClaim(token, Constant.EMAIL);
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto = userMapper.selectOne(userDto);
        // 用户是否存在
        if (userDto == null) {
            throw new ZeusException("该帐号不存在(The account does not exist.)");
        }
        return userDto;
    }

    /**
     * 获取当前登录用户Id
     *
     * @param
     * @return UserDto
     * @author nizonglong
     * @date 2019/3/15 11:48
     */
    public String getUserId() {
        return getUser().getUid();
    }

    /**
     * 获取当前登录用户Token
     *
     * @param
     * @return UserDto
     * @author nizonglong
     * @date 2019/3/15 11:48
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户Account
     *
     * @param
     * @return UserDto
     * @author nizonglong
     * @date 2019/3/15 11:48
     */
    public String getUsername() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 解密获得 USERNAME
        return JwtUtil.getClaim(token, Constant.EMAIL);
    }
}

