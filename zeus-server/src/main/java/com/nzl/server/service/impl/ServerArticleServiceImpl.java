package com.nzl.server.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.JsonUtils;
import com.nzl.dao.*;
import com.nzl.model.dto.ArticleDto;
import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.ArticleBlog;
import com.nzl.model.pojo.ArticleComment;
import com.nzl.model.pojo.ReplyComment;
import com.nzl.server.model.ArticleVo;
import com.nzl.server.service.ServerArticleService;
import com.nzl.server.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: nizonglong
 * @date: 2020/3/21 15:57
 * @desc:
 * @version: 0.1
 **/
@Service
public class ServerArticleServiceImpl implements ServerArticleService {

    @Resource
    private ArticleBlogMapper blogMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleCommentMapper commentMapper;
    @Resource
    private ReplyCommentMapper replyMapper;
    @Resource
    private BlogTypeMapper typeMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public PageInfo<ArticleDto> getPageArticles(int index, int pageSize) {
        try {
            PageHelper.startPage(index, pageSize);
            List<ArticleBlog> articles = blogMapper.getPageArticles(index, pageSize);
            return new PageInfo<>(getArticleInfo(articles));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ZeusResponseBean getArticleById(String id) {
        try {
            //添加缓存逻辑
            //从缓存中取信息
            Object articleObj = redisUtil.getObject(Constant.REDIS_ARTICLE_KEY + id);
            String json = JsonUtils.objectToJson(articleObj);
            //判断是否有值
            if (!StringUtils.isBlank(json)) {
                //把json转换成java对象
                ArticleDto article = JsonUtils.jsonToPojo(json, ArticleDto.class);
                return ZeusResponseBean.ok(article);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 没有Article的话就从数据库提取
        ArticleDto article = blogMapper.selectByPrimaryKey(id);
        // 加入缓存Redis
        redisUtil.setObject(Constant.REDIS_ARTICLE_KEY + article.getArticleBlogId(),
                JsonUtils.objectToJson(article));
        // 文章作者信息
        UserDto author = userMapper.selectByPrimaryKey(article.getUid());
        // 文章评论信息
        List<ArticleComment> comments = commentMapper.selectByArticleId(article.getArticleBlogId());
        // 文章的VO类
        ArticleVo articleVo = new ArticleVo(article, author, getCommentInfo(comments));

        return ZeusResponseBean.ok(articleVo);
    }

    @Override
    public ZeusResponseBean getArticlesByUid(String uid, int index, int pageSize) {
        PageHelper.startPage(index, pageSize);
        List<ArticleBlog> articles = blogMapper.getArticlesByUid(uid, index, pageSize);
        PageInfo<ArticleDto> pageInfo = new PageInfo<>(getArticleInfo(articles));
        return ZeusResponseBean.ok(pageInfo);
    }

    /**
     * 添加用户名到评论里,转换评论信息为必要的输出DTO
     *
     * @param comments
     * @return
     */
    private List<CommentDto> getCommentInfo(List<ArticleComment> comments) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (ArticleComment comment : comments) {
            // 进行评论信息赋值转换，只添加需要的信息
            CommentDto commentDto = new CommentDto();
            // 拷贝数据
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUsername(userMapper.getUsernameByUid(comment.getUid()));

            // 添加回复
            List<ReplyComment> replies = replyMapper.selectByCommentId(commentDto.getArticleCommentId());
            commentDto.setReply(getReplyInfo(replies));

            // 添加到LIST
            commentDtoList.add(commentDto);
        }

        return commentDtoList;
    }

    /**
     * 添加用户名到回复里,转换回复信息为必要的输出DTO
     *
     * @param replies
     * @return
     */
    private List<ReplyDto> getReplyInfo(List<ReplyComment> replies) {
        List<ReplyDto> replyDtoList = new ArrayList<>();
        for (ReplyComment reply : replies) {
            // 进行评论信息赋值转换，只添加需要的信息
            ReplyDto replyDto = new ReplyDto();
            // 拷贝数据
            BeanUtils.copyProperties(reply, replyDto);
            replyDto.setUsername(userMapper.getUsernameByUid(reply.getUid()));
            replyDto.setReplyUsername(userMapper.getUsernameByReplyId(reply.getReplyId()));

            // 添加到LIST
            replyDtoList.add(replyDto);
        }

        return replyDtoList;
    }

    /**
     * 转换Article信息添加作者username
     *
     * @param articles
     * @return
     */
    private List<ArticleDto> getArticleInfo(List<ArticleBlog> articles) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (ArticleBlog article : articles) {
            // 进行评论信息赋值转换，只添加需要的信息
            ArticleDto articleDto = new ArticleDto();
            // 拷贝数据
            BeanUtils.copyProperties(article, articleDto);
            if (article.getUid() != null) {
                articleDto.setAuthorName(userMapper.getUsernameByUid(article.getUid()));
            }

            if (article.getBlogTypeId() != null) {
                articleDto.setTypeName(typeMapper.getNameById(article.getBlogTypeId()));
            }

            articleDto.setStatus(article.getBlogStatus() == 0 ? "草稿" : "发布");

            // 添加到LIST
            articleDtoList.add(articleDto);
        }

        return articleDtoList;
    }
}
