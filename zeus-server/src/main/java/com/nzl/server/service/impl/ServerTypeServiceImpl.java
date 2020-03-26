package com.nzl.server.service.impl;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.dao.BlogTypeMapper;
import com.nzl.server.service.ServerTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/26 20:39
 * @desc:
 * @version: 0.1
 **/
@Service
public class ServerTypeServiceImpl implements ServerTypeService {
    @Resource
    private BlogTypeMapper typeMapper;

    @Override
    public ZeusResponseBean getAllType() {
        return ZeusResponseBean.ok(typeMapper.selectAll());
    }
}
