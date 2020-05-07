package com.nzl.dao;


import com.nzl.model.dto.PermissionDto;
import com.nzl.model.dto.RoleDto;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface PermissionMapper extends Mapper<PermissionDto> {

    /**
     * 根据Role查询Permission
     *
     * @param roleDto
     * @return
     */
    List<PermissionDto> findPermissionByRole(RoleDto roleDto);
}