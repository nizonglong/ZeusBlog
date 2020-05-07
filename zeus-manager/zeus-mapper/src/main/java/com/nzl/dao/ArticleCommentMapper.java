package com.nzl.dao;

import com.nzl.model.dto.CommentDto;
import com.nzl.model.pojo.ArticleComment;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author nizonglong
 */
@org.apache.ibatis.annotations.Mapper
public interface ArticleCommentMapper extends Mapper<CommentDto>  {
    /**
     * 通过博客id查找评论
     * @param articleId
     * @return
     */
    List<ArticleComment> selectByArticleId(Long articleId);

    /**
     * 根据文章id删除相关评论
     * @param id
     * @return
     */
    int deleteCommentsByArticleId(Long id);
}