package com.nzl.admin.service.impl;

import com.nzl.admin.service.AdminCommentService;
import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.HttpClientUtil;
import com.nzl.dao.*;
import com.nzl.model.dto.ArticleDto;
import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.ArticleBlog;
import com.nzl.model.pojo.ArticleComment;
import com.nzl.model.pojo.ReplyComment;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCommentServiceImpl implements AdminCommentService {

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


    @Override
    public ZeusResponseBean getComments(String uid) {
        // 获取文章信息
        List<ArticleBlog> articleBlogList = blogMapper.getArticleIdAndTitle(uid);
        // 获取评论信息
        List<CommentDto> commentDtos = getCommentList(articleBlogList);

        return ZeusResponseBean.ok(commentDtos);
    }

    private List<CommentDto> getCommentList(List<ArticleBlog> articleBlogList) {
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (ArticleBlog article: articleBlogList) {
            // 文章评论信息
            List<ArticleComment> comments = commentMapper.selectByArticleId(article.getArticleBlogId());
            commentDtoList.addAll(getCommentInfo(comments));
        }

        return commentDtoList;
    }

    @Override
    public ZeusResponseBean getUserByToken(String token) {
        try {
            //调用sso系统的服务，根据token取用户信息
            String json = HttpClientUtil.doGet(Constant.SSO_BASE_URL
                    + Constant.SSO_USER_TOKEN + token);
            //把json转换成 ZeusResponseBean
            ZeusResponseBean result = ZeusResponseBean.formatToPojo(json, UserDto.class);
            if (result.getStatus() == HttpStatus.OK.value()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            // add
            commentDto.setUsername(userMapper.getUsernameByUid(comment.getUid()));
            commentDto.setBlogName(blogMapper.selectByPrimaryKey(String.valueOf(comment.getArticleBlogId())).getTitle());

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
