package com.nzl.server.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.StringUtil;
import com.nzl.server.service.ServerArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/21 15:51
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ServerArticleService articleService;

    /**
     * 按照时间倒序获取 Article
     *
     * @return
     */
    @GetMapping("/timeArticles")
    public ZeusResponseBean getTimeArticles(int index, int pageSize) {
        if (pageSize == 0) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }

        return articleService.getPageArticles(index, pageSize);
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public ZeusResponseBean getArticleDetail(@PathVariable String id) {
        if (StringUtil.isBlank(id)) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "blog id 不能为空");
        }
        return articleService.getArticleById(id);
    }


    /**
     * 获取用户的文章by uid
     *
     * @param uid
     * @return
     */
    @GetMapping("/list")
    public ZeusResponseBean getArticlesByUid(String uid, int index, int pageSize) {
        if (StringUtil.isBlank(uid)) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "请先登录！");
        }

        if (pageSize == 0) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }

        return articleService.getArticlesByUid(uid, index, pageSize);
    }
}
