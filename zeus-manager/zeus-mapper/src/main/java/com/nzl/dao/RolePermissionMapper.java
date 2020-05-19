package com.nzl.dao;

import com.nzl.model.pojo.UserRole;
import org.apache.ibatis.annotations.*;

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

    @Insert("insert into user_role(uid, role_id) values(#{uid}, #{rid})")
    int insertUserRole(String uid, int rid);

    @Select("select * from user_role where uid = #{uid}")
    @Results({
            @Result(column = "uid", property = "uid"),
            @Result(column = "role_id", property = "rid"),
    })
    UserRole selectByUid(String uid);
}
