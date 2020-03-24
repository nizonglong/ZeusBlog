package com.nzl.dao;

import com.nzl.model.dto.CommentDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author nizonglong
 */
public interface ArticleCommentMapper extends Mapper<CommentDto>  {
    /**
     * 通过博客id查找评论
     * @param articleId
     * @return
     */
    List<CommentDto> selectByArticleId(Long articleId);
}