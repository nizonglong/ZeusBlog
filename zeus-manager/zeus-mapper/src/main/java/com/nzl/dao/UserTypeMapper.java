package com.nzl.dao;


import com.nzl.model.pojo.UserType;

public interface UserTypeMapper {
    
    int deleteByPrimaryKey(Integer userTypeId);

    
    int insert(UserType record);

    
    int insertSelective(UserType record);

    
    UserType selectByPrimaryKey(Integer userTypeId);

    
    int updateByPrimaryKeySelective(UserType record);

    
    int updateByPrimaryKey(UserType record);
}