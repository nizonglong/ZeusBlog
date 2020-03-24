package com.nzl.web.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.pojo.ArticleComment;

/**
 * @author: nizonglong
 * @date: 2020/3/25 0:43
 * @desc:
 * @version: 0.1
 **/
public interface WebArticleService {
    /**
     * 提交评论
     * @param comment
     * @return
     */
    ZeusResponseBean comment(CommentDto comment);

    /**
     * 提交回复
     * @param reply
     * @return
     */
    ZeusResponseBean reply(ReplyDto reply);
}
