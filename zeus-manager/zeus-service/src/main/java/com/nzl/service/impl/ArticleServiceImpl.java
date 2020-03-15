package com.nzl.service.impl;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.dao.ArticleBlogMapper;
import com.nzl.dao.UserMapper;
import com.nzl.pojo.ArticleBlog;
import com.nzl.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @classname: ArticleServiceImpl
 * @description:
 * @author: nizonglong
 * @date: 2019/8/8 15:55
 * @version: 1.0
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleBlogMapper articleMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public List<ArticleBlog> getAllArticle(String uid) {
        return articleMapper.getAllArticleByUid(uid);
    }

    @Override
    public ZeusBlogResult getAuthor(String uid) {

        return ZeusBlogResult.ok(userMapper.selectByPrimaryKey(uid));
    }


}
