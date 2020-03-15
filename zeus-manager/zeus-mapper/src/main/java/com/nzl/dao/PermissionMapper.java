package com.nzl.dao;

import com.nzl.dao.provider.PermissionSqlProvider;
import com.nzl.pojo.Permission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Set;

public interface PermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbg.generated
     */
    @Delete({
        "delete from permission",
        "where permission_id = #{permissionId,jdbcType=TINYINT}"
    })
    int deleteByPrimaryKey(Byte permissionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbg.generated
     */
    @Insert({
        "insert into permission (permission_id, available, ",
        "name, permission, ",
        "url, gmt_modified, ",
        "gmt_create)",
        "values (#{permissionId,jdbcType=TINYINT}, #{available,jdbcType=CHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{permission,jdbcType=VARCHAR}, ",
        "#{url,jdbcType=VARCHAR}, #{gmtModified,jdbcType=TIMESTAMP}, ",
        "#{gmtCreate,jdbcType=TIMESTAMP})"
    })
    int insert(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbg.generated
     */
    @InsertProvider(type= PermissionSqlProvider.class, method="insertSelective")
    int insertSelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "permission_id, available, name, permission, url, gmt_modified, gmt_create",
        "from permission",
        "where permission_id = #{permissionId,jdbcType=TINYINT}"
    })
    @Results({
        @Result(column="permission_id", property="permissionId", jdbcType=JdbcType.TINYINT, id=true),
        @Result(column="available", property="available", jdbcType=JdbcType.CHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="permission", property="permission", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="gmt_modified", property="gmtModified", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="gmt_create", property="gmtCreate", jdbcType=JdbcType.TIMESTAMP)
    })
    Permission selectByPrimaryKey(Byte permissionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbg.generated
     */
    @UpdateProvider(type=PermissionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbg.generated
     */
    @Update({
        "update permission",
        "set available = #{available,jdbcType=CHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "permission = #{permission,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "gmt_modified = #{gmtModified,jdbcType=TIMESTAMP},",
          "gmt_create = #{gmtCreate,jdbcType=TIMESTAMP}",
        "where permission_id = #{permissionId,jdbcType=TINYINT}"
    })
    int updateByPrimaryKey(Permission record);

    /**
     * 列出role拥有的权限
     *
     * @param role
     * @return
     */
    @Select("SELECT permission from permission where permission_id in (" +
            "SELECT permission_id from role_permission rp left join role r on rp.role_id=r.role_id where r.role=#{role});")
    Set<String> listRolePermission(String role);
}