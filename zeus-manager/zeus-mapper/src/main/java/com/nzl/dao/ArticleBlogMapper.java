package com.nzl.dao;

import com.nzl.model.dto.ArticleDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.ArticleBlog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author nizonglong
 */
public interface ArticleBlogMapper extends Mapper<ArticleDto> {
    /**
     * 获取article by page
     * @param startRow
     * @param pageSize
     * @return
     */
    List<ArticleBlog> getPageArticles(int startRow, int pageSize);

}