package com.nzl.admin.controller;

import com.nzl.dao.BlogTypeMapper;
import com.nzl.pojo.BlogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @classname: AdminPageController
 * @description:
 * @author: nizonglong
 * @date: 2019/8/8 20:39
 * @version: 1.0
 */
@Controller
@Slf4j
@RequestMapping(value = "/adp")
public class AdminPageController {
    @Resource
    private BlogTypeMapper blogTypeMapper;

    @RequestMapping(value = "/showIndex")
    public String showIndex() {
        log.info("showIndex()");
        return "index";
    }

    @RequestMapping(value = "/table")
    public String showTable() {
        return "table-list";
    }

    @RequestMapping(value = "/edit")
    public String edit() {
        return "edit";
    }

    @RequestMapping(value = "/edit2")
    public String edit2() {
        return "show";
    }

    @RequestMapping(value = "/editArticle")
    public ModelAndView editArticle(ModelAndView mav) {
        List<BlogType> types = blogTypeMapper.listAllSubTypes();
        log.info(types.toString());
        mav.addObject("types", types);
        mav.setViewName("article_edit");

        return mav;
    }

}
