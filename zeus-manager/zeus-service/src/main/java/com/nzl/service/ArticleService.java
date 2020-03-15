package com.nzl.service;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.pojo.ArticleBlog;

import java.util.List;

/**
 * @classname: ArticleService
 * @description:
 * @author: nizonglong
 * @date: 2019/8/8 15:55
 * @version: 1.0
 */
public interface ArticleService {
    List<ArticleBlog> getAllArticle(String uid);

    ZeusBlogResult getAuthor(String uid);
//
//    boolean writeToMarkdown(ArticleBlog blog, String path, String fileName);
//
//    ArticleBlog getArticleById(long articleId);
}
