package com.nzl.server.service.impl;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.StringUtil;
import com.nzl.dao.ArticleBlogMapper;
import com.nzl.dao.UserMapper;
import com.nzl.server.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleBlogMapper blogMapper;

    @Override
    public ZeusResponseBean titleSearch(String title) {
        if (StringUtil.isNotBlank(title)) {
            return ZeusResponseBean.ok(blogMapper.searchByTitle(title));
        } else {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "搜索关键词有误");
        }
    }

    @Override
    public ZeusResponseBean contentSearch(String content) {
        if (StringUtil.isNotBlank(content)) {
            return ZeusResponseBean.ok(blogMapper.searchByContent(content));
        } else {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "搜索关键词有误");
        }
    }

    @Override
    public ZeusResponseBean userSearch(String username) {
        if (StringUtil.isNotBlank(username)) {
            return ZeusResponseBean.ok(userMapper.searchUser(username));
        } else {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "搜索关键词有误");
        }
    }
}
