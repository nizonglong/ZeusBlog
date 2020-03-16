package com.nzl.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserType implements Serializable {
    /**
     * 用户自定义标签id
     */
    private Integer userTypeId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


    private static final long serialVersionUID = 1L;


}