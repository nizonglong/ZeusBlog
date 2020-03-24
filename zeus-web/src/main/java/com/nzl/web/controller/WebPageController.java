package com.nzl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: nizonglong
 * @date: 2020/3/20 2:45
 * @desc:
 * @version: 0.1
 **/
@Controller
public class WebPageController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/article/{id}")
    public String detailArticle(@PathVariable String id) {
        return "article";
    }

}
