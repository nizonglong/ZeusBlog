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

    @Resource
    private MailUtil mailUtil;


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
        userDtoTemp.setUsername(userDto.getUsername());
        userDtoTemp.setUsername(userDto.getEmail());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp != null && StringUtil.isNotBlank(userDtoTemp.getPassword())) {
            return ZeusResponseBean.build(HttpStatus.OK.value(), "该帐号已存在(Account exist.)");
        }
        userDto.setJoinTime(new Date());
        // 密码以邮箱+密码的形式进行AES加密
        if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "密码最多16位(Password up to 16 bits.)");
        }
        String key = AesCipherUtil.enCrypto(userDto.getEmail() + userDto.getPassword());
        userDto.setPassword(key);

        String uid = "zeus_" + UUID.randomUUID();
        userDto.setUid(uid);

        int count = userService.insert(userDto);
        if (count <= 0) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "新增失败(Insert Failure)");
        }
        return ZeusResponseBean.ok("新增成功(Insert Success)");
    }

    @GetMapping("/sendActiveCode")
    public ZeusResponseBean sendActiveCode(String email) {
        if (StringUtil.isBlank(email)) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "邮箱不能为空");
        }

        try {
            // 生成验证码，加入redis
            String code = VerifyUtil.getRandonString(6);
            JedisUtil.setObject(Constant.PREFIX_REGISTER_CODE + email, code, ServerConstant.TIME_10_MINS);
            // 发送邮件
            mailUtil.singleHtmlSend(email, ServerConstant.REGISTER_SUBJECT, ServerConstant.registerContent(email, code));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ZeusResponseBean.ok("验证码发送失败");
        }

        return ZeusResponseBean.ok("验证码已发送");
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
