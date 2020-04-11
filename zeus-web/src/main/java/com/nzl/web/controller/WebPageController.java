package com.nzl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: nizonglong
 * @date: 2020/3/20 2:45
 * @desc:
 * @version: 0.1
 **/
@Controller
public class WebPageController {
    /**
     * 打开首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }
    /**
     * 展示其他页面
     * <p>Title: showpage</p>
     * <p>Description: </p>
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

    @GetMapping("/article/{id}")
    public String detailArticle(@PathVariable String id) {
        return "article";
    }

}
