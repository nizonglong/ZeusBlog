package com.nzl.server.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.service.IBaseService;
import com.nzl.model.dto.ArticleDto;
import org.springframework.stereotype.Service;

/**
 * @author: nizonglong
 * @date: 2020/3/21 15:56
 * @desc:
 * @version: 0.1
 **/
@Service
public interface ServerArticleService extends IBaseService<ArticleDto> {
    /**
     * 分页获取 Article
     * @param index
     * @param pageSize
     * @return
     */
    ZeusResponseBean getPageArticles(int index, int pageSize);
}
