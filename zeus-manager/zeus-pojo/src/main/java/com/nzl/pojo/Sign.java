package com.nzl.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Sign implements Serializable {
    /**
     * 签到id
     */
    private Long signId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 签到状态：'已签到','未签到','签到超时'
     */
    private String status;

    /**
     * 当月签到天数
     */
    private Byte monthDay;

    /**
     * 签到总天数
     */
    private Integer totalDay;

    /**
     * 签到时间
     */
    private Date time;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    private static final long serialVersionUID = 1L;


}