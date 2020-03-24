package com.nzl.web.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.CommentDto;
import com.nzl.model.dto.ReplyDto;
import com.nzl.model.pojo.ArticleComment;
import com.nzl.web.service.WebArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/25 0:40
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/article")
public class WebArticleController {
    @Resource
    private WebArticleService articleService;

    @PostMapping("/comment")
    public ZeusResponseBean comment(@RequestBody CommentDto comment) {
        return articleService.comment(comment);
    }

    @PostMapping("/reply")
    public ZeusResponseBean reply(@RequestBody ReplyDto reply) {
        return articleService.reply(reply);
    }

}
