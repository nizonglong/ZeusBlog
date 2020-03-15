package com.nzl.sso.shiro;

import com.nzl.common.util.JWTUtil;
import com.nzl.pojo.Permission;
import com.nzl.pojo.User;
import com.nzl.sso.service.SsoUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @classname: MyRealm
 * @description: MyRealm
 * @author: nizonglong
 * @date: 2019/7/30 14:11
 * @version: 1.0
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Resource
    private SsoUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("doGetAuthorizationInfo() 权限认证");

        String username = JWTUtil.getUsername(principals.toString());
        User user = userService.getUser(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 角色 Role
        simpleAuthorizationInfo.addRole(user.getRole().getRole());

        // 权限 Permission
        Set<String> permission = new HashSet<>();
        for (Permission perm:user.getRole().getPermissions()) {
            permission.add(perm.getPermission());
        }

        simpleAuthorizationInfo.addStringPermissions(permission);

        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户正确与否验证，错误抛出异常即可
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo()  身份认证");

        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token 无效！");
        }

        User user = userService.getUser(username);
        if (user == null) {
            throw new AuthenticationException("用户" + username + "不存在");
        }

        if (!JWTUtil.verify(token, username)) {
            throw new AuthenticationException("账户密码错误!");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

}
