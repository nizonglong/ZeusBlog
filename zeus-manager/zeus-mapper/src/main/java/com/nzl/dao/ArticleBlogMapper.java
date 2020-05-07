package com.nzl.dao;

import com.nzl.model.dto.ArticleDto;
import com.nzl.model.pojo.ArticleBlog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author nizonglong
 */
@org.apache.ibatis.annotations.Mapper
public interface ArticleBlogMapper extends Mapper<ArticleDto> {
    /**
     * 获取article by page
     * @param startRow
     * @param pageSize
     * @return
     */
    List<ArticleBlog> getPageArticles(int startRow, int pageSize);

    /**
     * 获取uid所属用户的文章
     * @param uid
     * @return
     */
    List<ArticleBlog> getArticlesByUid(String uid, int startRow, int pageSize);


    List<ArticleBlog> searchByTitle(String title);

    List<ArticleBlog> searchByContent(String content);

    List<ArticleBlog> getArticleIdAndTitle(String uid);

}