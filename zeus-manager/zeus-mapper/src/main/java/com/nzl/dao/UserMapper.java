package com.nzl.dao;


import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<UserDto> {
    /**
     * 自动生成的接口
     */
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    User getUser(String username);

    /**
     * 动态查询参数
     *
     * @param param
     * @param value
     * @return
     */
    int dynamicCountQuery(String param, String value);

    /**
     * 邮件查询用户
     *
     * @param email
     * @return
     */
    User getUserByEmail(String email);


}