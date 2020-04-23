package com.nzl.dao;


import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<UserDto> {

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    UserDto getUser(String username);

    /**
     * 动态查询参数
     *
     * @param param
     * @param value
     * @return
     */
    Integer dynamicCountQuery(String param, String value);

    /**
     * 邮件查询用户
     *
     * @param email
     * @return
     */
    UserDto getUserByEmail(String email);

    /**
     * 通过uid获取用户名称
     * @param uid
     * @return
     */
    String getUsernameByUid(String uid);

    /**
     * 根据replyId获得用户名
     * @param replyId
     * @return
     */
    String getUsernameByReplyId(Long replyId);

    List<User> searchUser(String username);
}