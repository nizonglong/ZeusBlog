package com.nzl.service.impl;


import com.nzl.common.pojo.ZeusResponseBean;
import com.nzl.common.util.VerifyUtil;
import com.nzl.dao.PermissionMapper;
import com.nzl.dao.RoleMapper;
import com.nzl.dao.UserMapper;
import com.nzl.model.pojo.User;
import com.nzl.server.pojo.MailBean;
import com.nzl.server.service.EmailService;
import com.nzl.server.service.FileService;
import com.nzl.server.util.RedisUtil;
import com.nzl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.nzl.common.constant.MailConstants.MAIL_REGISTER_ACTIVECODE_CONTENT;
import static com.nzl.common.constant.TableConstant.*;

/**
 * @classname: UserServiceImpl
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 14:08
 * @version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private EmailService emailService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private FileService fileService;

    /**
     * redis过期时间
     */
    @Value("${sso.SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    /**
     * 邮件发送者
     */
    @Value("${lance.mail.sender}")
    private String mailSender;


    /**
     * 配置文件注入值
     */
    @Value("${upload.path.portrait}")
    private String UPLOAD_PATH_PORTRAIT;

    /*--------------------具体实现方法--------------------*/

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
    public ZeusResponseBean checkUpdateData(String content, Integer type) {
        int count;
        // 对数据进行校验：1、2、3分别代表username、phone、email
        if (1 == type) {
            // 用户名校验
            count = userMapper.dynamicCountQuery("username", content);
            return count <= 1 ? ZeusResponseBean.ok() :
                    ZeusResponseBean.build(400, "用户名重复");
        } else if (2 == type) {
            // 电话校验
            count = userMapper.dynamicCountQuery("phone", content);
            return count <= 1 ? ZeusResponseBean.ok() :
                    ZeusResponseBean.build(400, "手机号重复");
        }

        return ZeusResponseBean.ok();
    }

    @Override
    public ZeusResponseBean sendActiveEmail(MailBean mailBean, HttpServletRequest request) {
        ZeusResponseBean result = null;
        /*
         * 设置验证码
         */
        String activeCode = VerifyUtil.getRandonString(6);

        // 缓存设置
        redisUtil.set("aCode_" + mailBean.getRecipient(), activeCode, 5 * 60);

        // 设置邮件内容
        mailBean.setContent(mailBean.getNickname() + "您好." + MAIL_REGISTER_ACTIVECODE_CONTENT
                + activeCode + "。 验证码有效时间为5分钟，请尽快注册");

        // 发送激活邮件
        try {
            result = emailService.sendSimpleMail(mailBean);
        } catch (Exception e) {
            e.printStackTrace();
            return ZeusResponseBean.build(500, e.getMessage());
        }

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ZeusResponseBean createUser(User user) {
        userMapper.insertSelective(user);
        return ZeusResponseBean.ok();
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(User user) {
        int update = userMapper.updateByPrimaryKeySelective(user);

        User newUser = userMapper.selectByPrimaryKey(user.getUid());
        redisUtil.set(user.getUid(), newUser,SSO_SESSION_EXPIRE);

        return update;
    }

    @Override
    public User queryUserById(String uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
