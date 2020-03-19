
package com.nzl.sso.service.impl;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.User;
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
public class SsoUserServiceImpl extends BaseServiceImpl<UserDto> implements SsoUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public ZeusResponseBean checkData(String content, Integer type) {
        int count;
        // 对数据进行校验：1、2、3分别代表username、phone、email
        // 用户名校验
        if (1 == type) {
            count = userMapper.dynamicCountQuery("username", content);
            // 电话校验
        } else if (2 == type) {
            count = userMapper.dynamicCountQuery("phone", content);
            // email校验
        } else {
            count = userMapper.dynamicCountQuery("email", content);
        }

        // 用于用户注册，数据库无对应的值代表正常可以注册，否则就是不能注册
        if (0 == count) {
            return ZeusResponseBean.ok(true);
        }

        return ZeusResponseBean.build(400, "信息重复，无法注册");
    }
}
