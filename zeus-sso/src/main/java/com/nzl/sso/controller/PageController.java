
package com.nzl.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:13
 * @desc: 控制页面跳转
 * @version: 0.1
 **/
@Controller
public class PageController {
    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String to403() {
        return "error/403";
    }
}
