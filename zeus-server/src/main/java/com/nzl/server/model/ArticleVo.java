package com.nzl.server.model;

import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ArticleDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: nizonglong
 * @date: 2020/3/23 13:37
 * @desc:
 * @version: 0.1
 **/
@Data
@AllArgsConstructor
public class ArticleVo {
    private ArticleDto article;

    private UserDto author;

    private List<CommentDto> comment;

    private List<ReplyDto> reply;
}
