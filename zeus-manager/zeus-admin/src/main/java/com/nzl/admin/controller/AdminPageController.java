package com.nzl.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: nizonglong
 * @date: 2020/3/26 0:26
 * @desc:
 * @version: 0.1
 **/
@Controller
public class AdminPageController {
    /**
     * 打开首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "article-list";
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

    @RequestMapping("edit/{id}")
    public String edit(@PathVariable String id) {
        return "edit-article";
    }
}
