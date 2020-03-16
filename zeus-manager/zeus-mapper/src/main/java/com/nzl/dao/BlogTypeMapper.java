package com.nzl.dao;


import com.nzl.model.pojo.BlogType;

public interface BlogTypeMapper {
    
    int deleteByPrimaryKey(Byte blogTypeId);
    
    int insert(BlogType record);
    
    int insertSelective(BlogType record);
    
    BlogType selectByPrimaryKey(Byte blogTypeId);

    int updateByPrimaryKeySelective(BlogType record);

    int updateByPrimaryKey(BlogType record);
}