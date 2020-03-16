package com.nzl.dao;


import com.nzl.model.pojo.ArticleBlog;

/**
 * @author nizonglong
 */
public interface ArticleBlogMapper {
    
    int deleteByPrimaryKey(Long articleBlogId);

    
    int insert(ArticleBlog record);

    
    int insertSelective(ArticleBlog record);

    
    ArticleBlog selectByPrimaryKey(Long articleBlogId);

    
    int updateByPrimaryKeySelective(ArticleBlog record);

    
    int updateByPrimaryKeyWithBLOBs(ArticleBlog record);

    
    int updateByPrimaryKey(ArticleBlog record);
}