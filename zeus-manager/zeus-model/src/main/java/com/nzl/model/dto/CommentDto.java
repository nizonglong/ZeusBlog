package com.nzl.model.dto;

import com.nzl.model.pojo.ArticleComment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author: nizonglong
 * @date: 2020/3/23 14:00
 * @desc:
 * @version: 0.1
 **/
@Getter
@Setter
@Table(name = "article_comment")
public class CommentDto extends ArticleComment {
    @Transient
    private String username;

    private List<ReplyDto> reply;
}
