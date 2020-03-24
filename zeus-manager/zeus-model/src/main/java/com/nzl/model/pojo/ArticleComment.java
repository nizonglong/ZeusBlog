package com.nzl.model.pojo;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_comment")
public class ArticleComment implements Serializable {
    /**
     *   评论id
     */
    @Id
    private Long articleCommentId;

    /**
     *   评论时间
     */
    private Date commentTime;

    /**
     *   被评论的文章id
     */
    private Long articleBlogId;

    /**
     *   发表评论用户
     */
    private String uid;

    /**
     *   修改时间
     */
    private Date gmtmodified;

    /**
     *   创建时间
     */
    private Date gmtcreate;

    /**
     *   评论内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

   }