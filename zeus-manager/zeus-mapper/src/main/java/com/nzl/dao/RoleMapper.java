package com.nzl.dao;

import com.nzl.model.dto.RoleDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author nizonglong
 */
@org.apache.ibatis.annotations.Mapper
public interface RoleMapper extends Mapper<RoleDto> {

    /**
     * 列出用户的角色
     *
     * @param userDto
     * @return
     */
    List<RoleDto> findRoleByUser(UserDto userDto);


}