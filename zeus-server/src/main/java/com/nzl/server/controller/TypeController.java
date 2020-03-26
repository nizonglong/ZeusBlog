package com.nzl.server.controller;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.server.service.ServerTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/26 20:38
 * @desc:
 * @version: 0.1
 **/
@RestController
@RequestMapping("/type")
public class TypeController {
    @Resource
    private ServerTypeService typeService;

    @GetMapping("/getAllType")
    public ZeusResponseBean getAllType() {
        return typeService.getAllType();
    }
}
