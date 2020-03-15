package com.nzl.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户md5密码
     */
    private String password;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户性别，男性，女性，保密。默认保密
     */
    private String gender;

    /**
     * 用户生日日期
     */
    private Date birthday;

    /**
     * 头像文件存储地址，有默认头像
     */
    private String headPortraitUrl;

    /**
     * 用户个性短介绍
     */
    private String introduction;

    /**
     * 用户注册时间
     */
    private Date joinTime;

    /**
     * 用户的邮箱地址，注册时填写
     */
    private String email;

    /**
     * 用户的手机号码
     */
    private String phone;

    /**
     * 密码盐，默认usr name的md5
     */
    private String salt;

    /**
     * 用户状态，'0未激活','1正常','2封禁'。默认为未激活
     */
    private String status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 立即从数据库中进行加载数据;
     * 用户角色集合
     */
    private Role role;

//    /**
//     * 密码盐.
//     * 重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
//     *
//     * @return username+salt
//     */
//    public String getCredentialsSalt() {
//        return this.username + this.salt;
//    }


}