package com.nzl.sso.config.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: nizonglong
 * @date: 2020/3/16 14:04
 * @desc: JwtToken
 * @version: 0.1
 **/
public class JwtToken implements AuthenticationToken {
    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
