package com.nzl.sso.service;

import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.model.dto.UserDto;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:25
 * @desc:
 * @version: 0.1
 **/
public interface SsoUserService extends IBaseService<UserDto> {
    /**
     * 注册时用于检测数据
     *
     * @param content 要检测的数据
     * @param type    数据类型:手机号码、用户名、邮箱
     * @return ZeusBlogResult
     */
    ZeusResponseBean checkData(String content, Integer type);
}
