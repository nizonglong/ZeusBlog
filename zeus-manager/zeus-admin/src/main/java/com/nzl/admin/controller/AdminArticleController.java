package com.nzl.admin.controller;

import com.nzl.admin.service.AdminArticleService;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.ArticleDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/31 21:51
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/article")
public class AdminArticleController {
    @Resource
    private AdminArticleService articleService;

    /**
     * 新建文章
     *
     * @param articleDto
     * @return
     */
    @PostMapping("/new")
    public ZeusResponseBean addArticle(@RequestBody ArticleDto articleDto) {
        return articleService.addArticle(articleDto);
    }

    /**
     * 更新文章
     * @param articleDto
     * @return
     */
    @PostMapping("/update")
    public ZeusResponseBean updateArticle(@RequestBody ArticleDto articleDto) {
        return articleService.updateArticle(articleDto);
    }

    @GetMapping("/delete/{id}")
    public ZeusResponseBean updateArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }
}
