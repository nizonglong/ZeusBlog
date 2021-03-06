package com.nzl.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "article_blog")
public class ArticleBlog implements Serializable {
    /**
     * 博客id
     */
    @Id
    private Long articleBlogId;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 发表博客时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date blogTime;

    /**
     * 发博客用户id
     */
    private String uid;

    /**
     * 博客所属类别
     */
    private String blogTypeId;

    /**
     * 博客状态：草稿，发布
     */
    private Integer blogStatus;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 博客文章内容
     */
    private String content;

    /**
     * 博客文章摘要
     */
    private String digest;


    private static final long serialVersionUID = 1L;


}