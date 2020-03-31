package com.nzl.model.dto;

import com.nzl.model.pojo.ArticleBlog;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: nizonglong
 * @date: 2020/3/21 15:57
 * @desc:
 * @version: 0.1
 **/
@Getter
@Setter
@Table(name = "article_blog")
public class ArticleDto extends ArticleBlog {
    @Transient
    private String authorName;

    @Transient
    private String typeName;

    @Transient
    private String status;
}
