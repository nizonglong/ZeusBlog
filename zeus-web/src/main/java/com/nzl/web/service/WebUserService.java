package com.nzl.web.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.User;

/**
 * @author: nizonglong
 * @date: 2020/3/20 14:14
 * @desc: WebUserService
 * @version: 0.1
 **/
public interface WebUserService{
    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    UserDto getUserByToken(String token);

    int updateUser(UserDto user);

    ZeusResponseBean checkUpdateData(String data, int type);

    User getUser(String uid);
}
