package com.nzl.model.pojo;

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
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private short roleId;

    /**
     * 用户状态，2封禁，1正常，0封禁
     */
    private Integer available;

    /**
     * role描述
     */
    private String description;

    /**
     * 角色的名称
     */
    private String role;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 角色 -- 权限关系：多对多关系;
     */
    private List<Permission> permissions;

}