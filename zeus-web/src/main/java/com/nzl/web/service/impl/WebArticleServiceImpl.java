package com.nzl.web.service.impl;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.IDUtils;
import com.nzl.common.util.StringUtil;
import com.nzl.dao.ArticleCommentMapper;
import com.nzl.dao.ReplyCommentMapper;
import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.pojo.ArticleComment;
import com.nzl.web.service.WebArticleService;
import com.nzl.web.service.WebUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: nizonglong
 * @date: 2020/3/25 0:43
 * @desc:
 * @version: 0.1
 **/
@Service
public class WebArticleServiceImpl implements WebArticleService {
    @Resource
    private ArticleCommentMapper commentMapper;
    @Resource
    private ReplyCommentMapper replyMapper;
    @Resource
    private WebUserService userService;

    @Override
    public ZeusResponseBean comment(CommentDto comment) {
        // 补充id数据
        comment.setArticleCommentId(IDUtils.genItemId());
        comment.setUid(userService.getUserByToken(comment.getUid()).getUid());
        // 补充时间数据
        comment.setCommentTime(new Date());
        comment.setGmtcreate(new Date());
        comment.setGmtmodified(new Date());

        commentMapper.insert(comment);

        return ZeusResponseBean.ok("评论发布成功！");
    }

    @Override
    public ZeusResponseBean reply(ReplyDto reply) {
        // 补充id数据
        reply.setReplyCommentId(IDUtils.genItemId());
        reply.setUid(userService.getUserByToken(reply.getUid()).getUid());

        // 补充时间数据
        reply.setTime(new Date());

        replyMapper.insert(reply);

        return ZeusResponseBean.ok("评论发布成功！");
    }
}
