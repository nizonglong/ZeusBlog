package com.nzl.service;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.pojo.BlogType;

import java.util.List;

/**
 * @classname: TypeService
 * @description:
 * @author: nizonglong
 * @date: 2019/8/8 19:31
 * @version: 1.0
 */
public interface TypeService {
    ZeusBlogResult getAllUserType(String uid);

    List<BlogType> getAllBlogType();
}
