package com.nzl.dao;

import com.nzl.model.dto.ReplyDto;
import com.nzl.model.pojo.ReplyComment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReplyCommentMapper extends Mapper<ReplyDto> {
    /**
     * 通过评论id查找回复
     * @param commentId
     * @return
     */
    List<ReplyComment> selectByCommentId(Long commentId);

    /**
     * 根据回复id查找回复
     * @param replyId
     * @return
     */
    List<ReplyComment> selectByReplyId(Long replyId);

    /**
     * 根据文章id删除相关回复
     * @param id
     * @return
     */
    int deleteRepliesByArticleId(Long id);
}