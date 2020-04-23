package com.nzl.web.service.impl;

import com.nzl.common.constant.Constant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.HttpClientUtil;
import com.nzl.common.util.JsonUtils;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.UserDto;
import com.nzl.model.pojo.User;
import com.nzl.web.service.WebUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: nizonglong
 * @date: 2020/3/20 14:14
 * @desc: WebUserServiceImpl
 * @version: 0.1
 **/
@Service
public class WebUserServiceImpl implements WebUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDto getUserByToken(String token) {
        try {
            //调用sso系统的服务，根据token取用户信息
            String json = HttpClientUtil.doGet(Constant.SSO_BASE_URL
                    + Constant.SSO_USER_TOKEN + token);
            //把json转换成 ZeusResponseBean
            ZeusResponseBean result = ZeusResponseBean.formatToPojo(json, UserDto.class);
            assert result != null;
            if (result.getStatus() == HttpStatus.OK.value()) {
                return (UserDto) result.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateUser(UserDto user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public ZeusResponseBean checkUpdateData(String data, int type) {
        UserDto userDto = new UserDto();
        int count = 0;
        if (1 == type) {
            userDto.setUsername(data);
            count = userMapper.selectCount(userDto);
        } else if (2 == type) {
            userDto.setPhone(data);
            count = userMapper.selectCount(userDto);
        }

        if (count == 0) {
            return ZeusResponseBean.ok();
        }
        return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(),"数据重复");
    }

    @Override
    public UserDto getUser(String uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
