package com.nzl.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.service.impl.BaseServiceImpl;
import com.nzl.common.util.JsonUtils;
import com.nzl.dao.ArticleBlogMapper;
import com.nzl.dao.ArticleCommentMapper;
import com.nzl.dao.ReplyCommentMapper;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ArticleDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.ArticleComment;
import com.nzl.model.pojo.ReplyComment;
import com.nzl.server.model.ArticleVo;
import com.nzl.server.service.ServerArticleService;
import com.nzl.server.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
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
public class ServerArticleServiceImpl extends BaseServiceImpl<ArticleDto> implements ServerArticleService {

    @Resource
    private ArticleBlogMapper blogMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleCommentMapper commentMapper;
    @Resource
    private ReplyCommentMapper replyMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public ZeusResponseBean getPageArticles(int index, int pageSize) {

        PageHelper.startPage(index, Constant.DEFAULT_PAGE_SIZE);
        List<ArticleDto> articles = blogMapper.getPageArticles(index, pageSize);
        PageInfo<ArticleDto> pageInfo = new PageInfo<>(articles);

        return ZeusResponseBean.ok(pageInfo);
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
            commentDto.setArticleCommentId(comment.getArticleCommentId());
            commentDto.setUid(comment.getUid());
            commentDto.setArticleBlogId(comment.getArticleBlogId());
            commentDto.setContent(comment.getContent());
            commentDto.setCommentTime(comment.getCommentTime());
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
     * @param replies
     * @return
     */
    private List<ReplyDto> getReplyInfo(List<ReplyComment> replies) {
        List<ReplyDto> replyDtoList = new ArrayList<>();
        for (ReplyComment reply : replies) {
            // 进行评论信息赋值转换，只添加需要的信息
            ReplyDto replyDto = new ReplyDto();
            replyDto.setReplyCommentId(reply.getReplyCommentId());
            replyDto.setReplyId(reply.getReplyId());
            replyDto.setUid(reply.getUid());
            replyDto.setArticleCommentId(reply.getArticleCommentId());
            replyDto.setContent(reply.getContent());
            replyDto.setTime(reply.getTime());
            replyDto.setUsername(userMapper.getUsernameByUid(reply.getUid()));
            replyDto.setReplyUsername(userMapper.getUsernameByReplyId(reply.getReplyId()));

            // 添加到LIST
            replyDtoList.add(replyDto);
        }

        return replyDtoList;
    }

}
