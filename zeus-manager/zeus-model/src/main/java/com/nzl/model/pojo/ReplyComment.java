package com.nzl.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "reply_comment")
public class ReplyComment implements Serializable {
    /**
     * 回复评论id
     */
    @Id
    private Long replyCommentId;

    /**
     * 回复-回复的id
     */
    private Long replyId;

    /**
     * 回复评论时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date time;

    /**
     * 被评论的文章的id
     */
    private Long articleCommentId;

    /**
     * 评论人
     */
    private String uid;

    /**
     * 需要回复评论的id
     */
    private String content;


    private static final long serialVersionUID = 1L;

}