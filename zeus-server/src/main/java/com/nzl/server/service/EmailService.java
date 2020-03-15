package com.nzl.server.service;

import com.nzl.common.pojo.ZeusBlogResult;
import com.nzl.server.pojo.MailBean;

/**
 * @classname: EmailService
 * @description:
 * @author: nizonglong
 * @date: 2019/8/7 20:08
 * @version: 1.0
 */
public interface EmailService {
    ZeusBlogResult sendSimpleMail(MailBean mailBean) throws Exception;
}
