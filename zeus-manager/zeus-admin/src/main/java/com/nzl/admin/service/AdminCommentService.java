package com.nzl.admin.service;

import com.nzl.common.pojo.ZeusResponseBean;

public interface AdminCommentService {
    ZeusResponseBean getComments(String uid);

    ZeusResponseBean deleteComment(Long id, Integer type);

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    ZeusResponseBean getUserByToken(String token);
}
