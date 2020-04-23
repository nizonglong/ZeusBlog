package com.nzl.server.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.server.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @RequestMapping("/getSearch")
    public ZeusResponseBean getSearch(String name, String value) {
        switch (name) {
            case "title":
                return searchService.titleSearch(value);
            case "content":
                return searchService.contentSearch(value);
            case "username":
                return searchService.userSearch(value);
            default:
                return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(),"error");
        }
    }
}
