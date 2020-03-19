package com.nzl.dao;


import com.nzl.model.dto.PermissionDto;
import com.nzl.model.dto.RoleDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<PermissionDto> {

    /**
     * 根据Role查询Permission
     *
     * @param roleDto
     * @return
     */
    List<PermissionDto> findPermissionByRole(RoleDto roleDto);
}