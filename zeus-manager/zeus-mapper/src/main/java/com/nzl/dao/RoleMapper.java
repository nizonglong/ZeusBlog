package com.nzl.dao;

import com.nzl.model.dto.RoleDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author nizonglong
 */
public interface RoleMapper extends Mapper<RoleDto> {

    /**
     * 列出用户的角色
     *
     * @param userDto
     * @return
     */
    List<RoleDto> findRoleByUser(UserDto userDto);


}