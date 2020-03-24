package com.nzl.dao;

import com.nzl.model.dto.ReplyDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReplyCommentMapper extends Mapper<ReplyDto> {
    /**
     * 通过评论id查找回复
     * @param commentId
     * @return
     */
    List<ReplyDto> selectByArticleId(Long commentId);
}