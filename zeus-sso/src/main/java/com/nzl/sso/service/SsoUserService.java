package com.nzl.sso.service;

import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.User;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:25
 * @desc:
 * @version: 0.1
 **/
public interface SsoUserService extends IBaseService<UserDto> {
    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    User getUser(String username);
}
