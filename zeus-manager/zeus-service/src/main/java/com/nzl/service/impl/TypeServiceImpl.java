package com.nzl.service.impl;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.pojo.UserType;
import com.nzl.dao.UserTypeMapper;
import com.nzl.pojo.BlogType;
import com.nzl.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @classname: TypeServiceImpl
 * @description:
 * @author: nizonglong
 * @date: 2019/8/8 19:31
 * @version: 1.0
 */
@Service
@Slf4j
public class TypeServiceImpl implements TypeService {
    @Resource
    private UserTypeMapper userTypeMapper;


    @Override
    public ZeusBlogResult getAllUserType(String uid) {
        List<UserType> userTypes = userTypeMapper.getAllUserType(uid);
        return ZeusBlogResult.ok(userTypes);
    }

    @Override
    public List<BlogType> getAllBlogType() {
        return null;
    }
}
