package com.nzl.sso.controller;

import com.nzl.common.constant.Constant;
import com.nzl.common.exception.ZeusException;
import com.nzl.common.exception.ZeusUnauthorizedException;
import com.nzl.common.pojo.HttpStatusEnum;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.AesCipherUtil;
import com.nzl.common.util.ExceptionUtil;
import com.nzl.common.util.StringUtil;
import com.nzl.common.util.VerifyUtil;
import com.nzl.model.dto.UserDto;
import com.nzl.server.common.ServerConstant;
import com.nzl.server.util.MailUtil;
import com.nzl.sso.service.SsoUserService;
import com.nzl.sso.util.JedisUtil;
import com.nzl.sso.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: nizonglong
 * @date: 2020/3/17 16:46
 * @desc:
 * @version: 0.1
 **/
@RestController
public class RegisterController {

    @Resource
    private SsoUserService userService;


    /**
     * 新增用户
     *
     * @param userDto
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    @PostMapping("/register")
    public ZeusResponseBean register(@RequestBody UserDto userDto) {
        // 检测验证码
        if (!Objects.equals(JedisUtil.getObject(
                Constant.PREFIX_REGISTER_CODE + userDto.getEmail()), userDto.getActiveCode())) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "验证码有误");
        }

        // 判断当前帐号是否存在
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setEmail(userDto.getEmail());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp != null && StringUtil.isNotBlank(userDtoTemp.getPassword())) {
            return ZeusResponseBean.build(HttpStatus.OK.value(), "该帐号已存在(Account exist.)");
        }

        try {
            return userService.creatUser(userDto);
        } catch (Exception e) {
            return ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败(Insert Failure)");
        }
    }

    /**
     * 发送验证码邮件
     *
     * @param email
     * @return
     */
    @GetMapping("/sendActiveCode")
    public ZeusResponseBean sendActiveCode(String email) {
        if (StringUtil.isBlank(email)) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "邮箱不能为空");
        }

        return userService.sendActiveCode(email);
    }

    @RequestMapping("/check/{param}/{type}")
    public Object checkData(@PathVariable String param, @PathVariable Integer type) {

        ZeusResponseBean result = null;

        //参数有效性校验
        if (StringUtils.isBlank(param)) {
            result = ZeusResponseBean.build(400, "校验内容不能为空");
        }
        if (type == null) {
            result = ZeusResponseBean.build(400, "校验内容类型不能为空");
        }
        if (type != 1 && type != 2 && type != 3) {
            result = ZeusResponseBean.build(400, "校验内容类型错误");
        }
        //校验出错
        if (null != result) {
            return result;
        }
        //调用服务
        try {
            result = userService.checkData(param, type);
        } catch (Exception e) {
            e.printStackTrace();
            result = ZeusResponseBean.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), "server error");
        }

        return result;
    }
}
