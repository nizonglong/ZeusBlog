package com.nzl.service.impl;


import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.common.util.VerifyUtil;
import com.nzl.dao.PermissionMapper;
import com.nzl.dao.RoleMapper;
import com.nzl.dao.UserMapper;
import com.nzl.pojo.User;
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
    public ZeusBlogResult checkData(String content, Integer type) {
        int count;
        // 对数据进行校验：1、2、3分别代表username、phone、email
        // 用户名校验
        if (1 == type) {
            count = userMapper.dynamicQuery("username", content);
            // 电话校验
        } else if (2 == type) {
            count = userMapper.dynamicQuery("phone", content);
            // email校验
        } else {
            count = userMapper.dynamicQuery("email", content);
        }

        // 用于用户注册，数据库无对应的值代表正常可以注册，否则就是不能注册
        if (0 == count) {
            return ZeusBlogResult.ok(true);
        }

        return ZeusBlogResult.build(400, "信息重复，无法注册");
    }

    @Override
    public ZeusBlogResult checkUpdateData(String content, Integer type) {
        int count;
        // 对数据进行校验：1、2、3分别代表username、phone、email
        if (1 == type) {
            // 用户名校验
            count = userMapper.dynamicQuery("username", content);
            return count <= 1 ? ZeusBlogResult.ok() :
                    ZeusBlogResult.build(400, "用户名重复");
        } else if (2 == type) {
            // 电话校验
            count = userMapper.dynamicQuery("phone", content);
            return count <= 1 ? ZeusBlogResult.ok() :
                    ZeusBlogResult.build(400, "手机号重复");
        }

        return ZeusBlogResult.ok();
    }

    @Override
    public ZeusBlogResult sendActiveEmail(MailBean mailBean, HttpServletRequest request) {
        ZeusBlogResult result = null;
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
            return ZeusBlogResult.build(500, e.getMessage());
        }

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ZeusBlogResult createUser(User user) {
        userMapper.insertSelective(user);
        return ZeusBlogResult.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ZeusBlogResult updatePortrait(MultipartFile head) {
        User user = redisUtil.getUser();

        String newHead = fileService.uploadOne(head, UPLOAD_PATH_PORTRAIT);
        if (null != newHead && newHead.length() > 0) {
            // 若头像更新完成则删除旧头像
            String oldPic = user.getHeadPortraitUrl();
            oldPic = oldPic.substring(oldPic.lastIndexOf('/') + 1);

            if (!DEFAULT_PORTRAIT.equals(oldPic)) {
                int isDelete = fileService.deleteFile(UPLOAD_PATH_PORTRAIT + "/" + oldPic);

                if (isDelete == 1) {
                    // 原始头像删除成功且上传了新头像则更新数据库和Redis
                    user.setHeadPortraitUrl(LOCAL_IP + ":" + TOMCAT_PORT_8080
                            + "/" + DEFAULT_LOCAL_PORTAL_DIR + "/" + newHead);
                    redisUtil.set(user.getUid(), user, SSO_SESSION_EXPIRE);

                    userMapper.updateByPrimaryKeySelective(user);
                    return ZeusBlogResult.ok();
                } else if (isDelete == 0) {
                    return ZeusBlogResult.build(400, "原始头像删除失败");
                } else {
                    return ZeusBlogResult.build(400, "文件不存在");
                }
            } else {
                // 新上传了新头像则直接更新数据库
                user.setHeadPortraitUrl(LOCAL_IP + ":" + TOMCAT_PORT_8080
                        + "/" + DEFAULT_LOCAL_PORTAL_DIR + "/" + newHead);
                redisUtil.set(user.getUid(), user,SSO_SESSION_EXPIRE);
                userMapper.updateByPrimaryKeySelective(user);
                return ZeusBlogResult.ok();
            }

        }

        return ZeusBlogResult.build(400, "头像更新失败");
    }


    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public Map<String, Object> getRolesAndPermissions(String uid) {
        Set<String> permissions = new HashSet<>();

        Set<String> roles = new HashSet<>(roleMapper.listUserRoles(uid));

        for (String role : roles) {
            Set<String> ps = permissionMapper.listRolePermission(role);
            permissions.addAll(ps);
        }

        Map<String, Object> map = new HashMap<>(2);


        map.put("allRoles", roles);
        map.put("allPermissions", permissions);
        return map;
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
    public int countPhone(String phone) {
        return userMapper.countPhone(phone);
    }

    @Override
    public User queryUserByUsername(String username) {
        return userMapper.queryUserByUsername(username);
    }

    @Override
    public User queryUserById(String uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
