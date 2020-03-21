package com.nzl.service.impl;


import com.nzl.model.dto.UserDto;
import com.nzl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @classname: UserServiceImpl
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 14:08
 * @version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Override
    public UserDto getUser() {
        return null;
    }
}
