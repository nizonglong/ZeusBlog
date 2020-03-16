package com.nzl.dao;


import com.nzl.model.pojo.ParentBlogType;

public interface ParentBlogTypeMapper {
    
    int deleteByPrimaryKey(Integer parentBlogTypeId);

    int insert(ParentBlogType record);

    int insertSelective(ParentBlogType record);

    ParentBlogType selectByPrimaryKey(Integer parentBlogTypeId);
    
    int updateByPrimaryKeySelective(ParentBlogType record);

    int updateByPrimaryKey(ParentBlogType record);
}