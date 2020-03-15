package com.nzl.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private Byte permissionId;

    /**
     * 用户状态，限制登录，正常，封禁。默认正常
     */
    private String available;

    /**
     * 对权限名称的描述
     */
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 权限url
     */
    private String url;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 角色集合
     */
    private List<Role> roles;
}