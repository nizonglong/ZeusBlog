package com.nzl.dao;


import com.nzl.model.dto.PermissionDto;
import com.nzl.model.dto.RoleDto;
import com.nzl.model.pojo.Permission;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface PermissionMapper extends Mapper<PermissionDto> {
    
    int deleteByPrimaryKey(Byte permissionId);

    
    int insert(Permission record);

    
    int insertSelective(Permission record);

    
    Permission selectByPrimaryKey(Byte permissionId);

    
    int updateByPrimaryKeySelective(Permission record);

    
    int updateByPrimaryKey(Permission record);

    /**
     * 根据Role查询Permission
     *
     * @param roleDto
     * @return
     */
    List<PermissionDto> findPermissionByRole(RoleDto roleDto);
}