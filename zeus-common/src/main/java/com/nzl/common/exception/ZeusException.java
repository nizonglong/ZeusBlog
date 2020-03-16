package com.nzl.common.exception;

/**
 * @author: nizonglong
 * @date: 2020/3/16 13:46
 * @desc: 自定义异常(CustomException)
 * @version: 0.1
 **/
public class ZeusException extends RuntimeException {

    public ZeusException(String msg){
        super(msg);
    }

    public ZeusException() {
        super();
    }
}
