package com.nzl.admin.controller;


import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.pojo.ArticleBlog;
import com.nzl.service.ArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @classname: AdminMainController
 * @description:
 * @author: nizonglong
 * @date: 2019/8/8 20:38
 * @version: 1.0
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminMainController {
    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/getArticleTable")
    public ZeusBlogResult getArticleTable() {
        List<ArticleBlog> articleBlogs = articleService.getAllArticle("zeus_a68d7316-b982-4e53-ac0e-f533ce0c38ab");

        try {
            return ZeusBlogResult.ok(articleBlogs);
        } catch (Exception e) {
            return ZeusBlogResult.build(400, e.getMessage());
        }
    }


}
