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

        UserDto user = userMapper.selectByPrimaryKey(article.getUid());

        List<CommentDto> comments = commentMapper.selectByArticleId(article.getArticleBlogId());
//        List<CommentDto> commentDtos = new ArrayList<>();
//        commentDtos.addAll(comments);
//        for (CommentDto comment: commentDtos) {
//            comment.setUsername(userMapper.getUsernameByUid(comment.getUid()));
//        }

        List<ReplyDto> replies = replyMapper.selectByArticleId(article.getArticleBlogId());
//        for (ReplyDto reply: replies) {
//            reply.setUsername(userMapper.getUsernameByUid(reply.getUid()));
//        }

        ArticleVo articleVo = new ArticleVo(article, user, comments, replies);

        return ZeusResponseBean.ok(articleVo);
    }


}
