package com.nzl.admin.controller;

import com.nzl.admin.service.AdminCommentService;
import com.nzl.common.pojo.ZeusResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class AdminCommentController {
    @Resource
    private AdminCommentService commentService;

    @GetMapping("/getComments")
    public ZeusResponseBean getComments(String uid) {

        return commentService.getComments(uid);
    }
}
