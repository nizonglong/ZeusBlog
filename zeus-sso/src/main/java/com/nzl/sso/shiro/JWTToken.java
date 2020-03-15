package com.nzl.sso.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @classname: JWTToken
 * @description: JWTToken
 * @author: nizonglong
 * @date: 2019/7/30 15:00
 * @version: 1.0
 */
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    /**
     * 秘钥
     */
    private String token;

    public JWTToken(String token) {
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

