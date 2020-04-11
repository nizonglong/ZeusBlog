package com.nzl.server.service;

import com.github.pagehelper.PageInfo;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.ArticleDto;
import org.springframework.stereotype.Service;

/**
 * @author: nizonglong
 * @date: 2020/3/21 15:56
 * @desc:
 * @version: 0.1
 **/
@Service
public interface ServerArticleService {
    /**
     * 分页获取 Article
     *
     * @param startRow
     * @param pageSize
     * @return
     */
    PageInfo<ArticleDto> getPageArticles(int startRow, int pageSize);

    /**
     * 根据博客id获取博客内容
     *
     * @param id
     * @return
     */
    ZeusResponseBean getArticleById(String id);

    /**
     * 获取用户发布的文章
     * @param uid
     * @return
     */
    ZeusResponseBean getArticlesByUid(String uid, int startRow, int pageSize);

}
