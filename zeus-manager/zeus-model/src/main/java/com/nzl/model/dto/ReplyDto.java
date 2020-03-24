package com.nzl.model.dto;

import com.nzl.model.pojo.ReplyComment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: nizonglong
 * @date: 2020/3/23 14:01
 * @desc:
 * @version: 0.1
 **/
@Getter
@Setter
@Table(name = "reply_comment")
public class ReplyDto extends ReplyComment {
    @Transient
    private String username;
}
