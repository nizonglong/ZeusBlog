package com.nzl.server.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.service.IBaseService;
import com.nzl.model.dto.UserDto;

/**
 * @author: nizonglong
 * @date: 2020/3/21 21:35
 * @desc:
 * @version: 0.1
 **/
public interface ServerUserService extends IBaseService<UserDto> {
    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    ZeusResponseBean getUserByToken(String token);
}
