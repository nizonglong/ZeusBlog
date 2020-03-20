package com.nzl.web.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: nizonglong
 * @date: 2020/3/20 11:03
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/main")
public class IndexController {
    @GetMapping("/test")
    public ZeusResponseBean test() {
        return ZeusResponseBean.ok("OK");
    }
}
