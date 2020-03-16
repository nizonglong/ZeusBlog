/**
 * @author: nizonglong
 * @date: 2020/3/15 23:25
 * @desc:
 * @version: 0.1
 **/
package com.nzl.sso.service;

import com.nzl.model.pojo.User;

public interface SsoUserService {
    User getUser(String username);
}
