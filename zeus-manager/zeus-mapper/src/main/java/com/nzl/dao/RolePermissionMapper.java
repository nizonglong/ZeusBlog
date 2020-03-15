package com.nzl.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @classname: RolePermissionMapper
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 16:19
 * @version: 1.0
 */
@Mapper
public interface RolePermissionMapper {
    @Insert("insert into role_permission(rid, pid) values(#{rid}, #{pid})")
    int insertRolePerm(int rid, int pid);

    @Insert("insert into user_role(uid, rid) values(#{uid}, #{rid})")
    int insertUserRole(int uid, int rid);


}
