
package com.nzl.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:13
 * @desc: 控制页面跳转
 * @version: 0.1
 **/
@Controller
public class PageController {
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
}
