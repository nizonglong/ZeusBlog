package com.nzl.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "blog_type")
public class BlogType implements Serializable {
    /**
     * 课程小类id
     */
    private Byte blogTypeId;

    /**
     * 课程父类id
     */
    private Byte parentBlogTypeId;

    /**
     * 课程名称
     */
    private String blogTypeName;

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