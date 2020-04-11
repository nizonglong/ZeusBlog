package com.nzl.admin.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.ArticleDto;

/**
 * @author: nizonglong
 * @date: 2020/3/31 21:50
 * @desc:
 * @version: 0.1
 **/
public interface AdminArticleService {
    /**
     * 新增文章
     *
     * @param articleDto
     * @return
     */
    ZeusResponseBean addArticle(ArticleDto articleDto);

    /**
     * 更新文章
     * @param articleDto
     * @return
     */
    ZeusResponseBean updateArticle(ArticleDto articleDto);

    /**
     * 根据文章id删除文章
     * @param id
     * @return
     */
    ZeusResponseBean deleteArticle(Long id);
}
