package com.nzl.sso.filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @classname: LoginFormAuthenticationFilter
 * @description: Shiro登录需要的Filter
 * @author: nizonglong
 * @date: 2019/7/30 14:14
 * @version: 1.0
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String successUrl = "/main/index";
        WebUtils.issueRedirect(request,response,successUrl);
        return false;
    }
}