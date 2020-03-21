package com.nzl.server.service.impl;

import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.service.impl.BaseServiceImpl;
import com.nzl.common.util.HttpClientUtil;
import com.nzl.model.dto.UserDto;
import com.nzl.server.service.ServerUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author: nizonglong
 * @date: 2020/3/21 21:39
 * @desc:
 * @version: 0.1
 **/
@Service
public class ServerUserServiceImpl extends BaseServiceImpl<UserDto> implements ServerUserService {
    @Override
    public ZeusResponseBean getUserByToken(String token) {
        try {
            //调用sso系统的服务，根据token取用户信息
            String json = HttpClientUtil.doGet(Constant.SSO_BASE_URL
                    + Constant.SSO_USER_TOKEN + token);
            //把json转换成 ZeusResponseBean
            ZeusResponseBean result = ZeusResponseBean.formatToPojo(json, UserDto.class);
            if (result.getStatus() == HttpStatus.OK.value()) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
