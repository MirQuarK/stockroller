package com.hzxc.chz.common.exception;

/**
 * Created by hdwang on 2017/11/1.
 * 业务异常，要通知给客户端的异常
 */
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}
