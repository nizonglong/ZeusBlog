package com.nzl.common.exception;

/**
 * @author: nizonglong
 * @date: 2020/3/16 13:47
 * @desc: 自定义401无权限异常(UnauthorizedException)
 * @version: 0.1
 **/
public class ZeusUnauthorizedException extends RuntimeException {

    public ZeusUnauthorizedException(String msg) {
        super(msg);
    }

    public ZeusUnauthorizedException() {
        super();
    }
}
