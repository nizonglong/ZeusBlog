package com.nzl.admin.service.impl;

import com.nzl.admin.service.AdminArticleService;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.IDUtils;
import com.nzl.dao.ArticleBlogMapper;
import com.nzl.dao.ArticleCommentMapper;
import com.nzl.dao.ReplyCommentMapper;
import com.nzl.model.dto.ArticleDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: nizonglong
 * @date: 2020/3/31 21:50
 * @desc:
 * @version: 0.1
 **/
@Service
public class AdminArticleServiceImpl implements AdminArticleService {
    @Resource
    private ArticleBlogMapper blogMapper;
    @Resource
    private ArticleCommentMapper commentMapper;
    @Resource
    private ReplyCommentMapper replyMapper;

    @Override
    public ZeusResponseBean addArticle(ArticleDto articleDto) {
        try {
            // 增加其他内容
            articleDto.setArticleBlogId(IDUtils.genItemId());

            articleDto.setBlogTime(new Date());
            articleDto.setGmtCreate(new Date());
            articleDto.setGmtModified(new Date());

            blogMapper.insert(articleDto);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文章新增失败!");
        }
        return ZeusResponseBean.ok("文章新增成功！");
    }

    @Override
    public ZeusResponseBean updateArticle(ArticleDto articleDto) {
        try {
            // 更新其他内容
            articleDto.setBlogTime(new Date());
            articleDto.setGmtCreate(new Date());
            articleDto.setGmtModified(new Date());

            blogMapper.updateByPrimaryKey(articleDto);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文章更新失败!");
        }
        return ZeusResponseBean.ok("文章更新成功！");
    }

    @Override
    public ZeusResponseBean deleteArticle(Long id) {
        try {
            // 删除文章，评论，回复
            blogMapper.deleteByPrimaryKey(id);
            commentMapper.deleteCommentsByArticleId(id);
            replyMapper.deleteRepliesByArticleId(id);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "文章删除失败!");
        }
        return ZeusResponseBean.ok("文章删除成功！");
    }

}
