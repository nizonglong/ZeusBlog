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
public class ParentBlogType implements Serializable {
    /**
     * 博客父类id
     */
    private Byte parentBlogTypeId;

    /**
     * 父类博客名称
     */
    private String name;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;


    private static final long serialVersionUID = 1L;


}