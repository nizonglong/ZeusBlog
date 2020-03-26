
package com.nzl.sso.service.impl;

import com.nzl.common.constant.Constant;
import com.nzl.common.constant.MailConstant;
import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.service.impl.BaseServiceImpl;
import com.nzl.common.util.AesCipherUtil;
import com.nzl.common.util.CookieUtils;
import com.nzl.common.util.JsonUtils;
import com.nzl.common.util.VerifyUtil;
import com.nzl.dao.UserMapper;
import com.nzl.model.dto.UserDto;
import com.nzl.server.util.MailUtil;
import com.nzl.sso.service.SsoUserService;
import com.nzl.sso.util.JedisUtil;
import com.nzl.sso.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @author: nizonglong
 * @date: 2020/3/15 23:26
 * @desc:
 * @version: 0.1
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class SsoUserServiceImpl extends BaseServiceImpl<UserDto> implements SsoUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private MailUtil mailUtil;

    /**
     * Redis User key
     */
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;


    @Override
    public ZeusResponseBean checkData(String content, Integer type) {
        int count;
        // 对数据进行校验：1、2、3分别代表username、phone、email
        // 用户名校验
        if (1 == type) {
            count = userMapper.dynamicCountQuery("username", content);
            // 电话校验
        } else if (2 == type) {
            count = userMapper.dynamicCountQuery("phone", content);
            // email校验
        } else {
            count = userMapper.dynamicCountQuery("email", content);
        }

        // 用于用户注册，数据库无对应的值代表正常可以注册，否则就是不能注册
        if (0 == count) {
            return ZeusResponseBean.ok(true);
        }

        return ZeusResponseBean.build(400, "信息重复，无法注册");
    }

    @Override
    public ZeusResponseBean sendActiveCode(String email) {
        try {
            // 生成验证码，加入redis
            String code = VerifyUtil.getRandonString(6);
            JedisUtil.setObject(Constant.PREFIX_REGISTER_CODE + email, code, MailConstant.TIME_10_MINS);
            // 发送邮件
            mailUtil.singleHtmlSend(email, MailConstant.REGISTER_SUBJECT, MailConstant.registerContent(email, code));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ZeusResponseBean.ok("验证码发送失败");
        }

        return ZeusResponseBean.ok("验证码已发送");
    }

    @Override
    public ZeusResponseBean creatUser(UserDto userDto) {
        userDto.setJoinTime(new Date());

        // 密码以邮箱+密码的形式进行AES加密
        if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "密码最多16位(Password up to 16 bits.)");
        }
        String key = AesCipherUtil.enCrypto(userDto.getEmail() + userDto.getPassword());
        userDto.setPassword(key);

        String uid = "zeus_" + UUID.randomUUID();
        userDto.setUid(uid);

        // insert
        userMapper.insert(userDto);

        return ZeusResponseBean.ok("新增成功(Insert Success)");
    }

    @Override
    public ZeusResponseBean userLogin(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        // 查询数据库中的帐号信息
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setEmail(email);
        userDtoTemp = userMapper.selectOne(userDtoTemp);
        if (userDtoTemp == null) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "该帐号不存在(The account does not exist.)");
        }
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以邮箱+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(email + password)) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + email)) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + email);
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(email, currentTimeMillis);

            //保存用户之前，把用户对象中的密码清空。
            userDtoTemp.setPassword(null);
            //把用户信息写入redis, 设置session的过期时间
            JedisUtil.setJson(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userDtoTemp), SSO_SESSION_EXPIRE);

            //添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效
            CookieUtils.setCookie(request, response, Constant.ZEUS_TOKEN, token);
            CookieUtils.setCookie(request, response, Constant.ZEUS_UID, userDtoTemp.getUid());

            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return ZeusResponseBean.ok("登录成功(Login Success.)");
        } else {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "帐号或密码错误(Account or Password Error.)");
        }
    }

    @Override
    public ZeusResponseBean getUserByToken(String token) {
        //根据token从redis中查询用户信息
        String json = JedisUtil.getJson(REDIS_USER_SESSION_KEY + ":" + token);
        //判断是否为空
        if (StringUtils.isBlank(json)) {
            return ZeusResponseBean.build(HttpStatus.BAD_REQUEST.value(), "此session已经过期，请重新登录");
        }
        //更新过期时间
        JedisUtil.setExpire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

        //返回用户信息
        return ZeusResponseBean.ok(JsonUtils.jsonToPojo(json, UserDto.class));
    }
}
