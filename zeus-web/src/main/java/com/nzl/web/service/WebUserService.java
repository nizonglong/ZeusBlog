package com.nzl.web.service;


import com.nzl.common.service.IBaseService;
import com.nzl.model.dto.UserDto;

/**
 * @author: nizonglong
 * @date: 2020/3/20 14:14
 * @desc: WebUserService
 * @version: 0.1
 **/
public interface WebUserService extends IBaseService<UserDto> {
    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UserDto getUserByToken(String token);
}