
package com.nzl.sso.service.impl;

import com.nzl.dao.UserMapper;
import com.nzl.pojo.User;
import com.nzl.sso.service.SsoUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:26
 * @desc:
 * @version: 0.1
 **/
@Service
public class SsoUserServiceImpl implements SsoUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser(String username) {
        return userMapper.getUser(username);
    }
}
