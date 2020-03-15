package com.nzl.admin.controller;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.dao.ArticleBlogMapper;
import com.nzl.pojo.ArticleBlog;
import com.nzl.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @classname: AdminArticleController
 * @description: 文章控制器
 * @author: nizonglong
 * @date: 2019/9/9 9:50
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/adminArticle")
public class AdminArticleController {
    @Resource
    private ArticleBlogMapper articleBlogMapper;
    @Resource
    private ArticleService articleService;


    @RequestMapping(value = "/submitMd")
    public ZeusBlogResult submitMd(String mdData, String title, String digest, String status, String keyword) {
        if (!"".equals(mdData) && mdData != null) {
            log.info(mdData);

            ArticleBlog blog = new ArticleBlog();
            blog.setTitle(title);
            blog.setDigest(digest);

            String[] keywords = keyword.split(",");
            StringBuilder typeIds = new StringBuilder();
            for (String key :
                    keywords) {
                typeIds.append(key).append(",");
            }
            typeIds.deleteCharAt(typeIds.length() - 1);
            blog.setBlogTypeId(typeIds.toString());

            blog.setBlogTime(new Date());
            blog.setGmtCreate(new Date());
            blog.setGmtModified(new Date());

            blog.setUid("zeus_a68d7316-b982-4e53-ac0e-f533ce0c38ab");

            if (status == null || "".equals(status)) {
                blog.setBlogStatus("草稿");
            } else {
                blog.setBlogStatus(status);
            }

            String path = "G:/data/";
            String fileName = "zb-" + System.currentTimeMillis() + ".md";
            blog.setContent(mdData);

            if (articleService.writeToMarkdown(blog, path, fileName)) {
                return ZeusBlogResult.ok();
            } else {
                return ZeusBlogResult.build(400, "文章发布异常");
            }
        } else {
            return ZeusBlogResult.build(400, "文章内容为空");
        }
    }
}
