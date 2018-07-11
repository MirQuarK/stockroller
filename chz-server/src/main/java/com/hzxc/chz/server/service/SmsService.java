package com.hzxc.chz.server.service;

public interface SmsService {

    /**
     * 发送手机验证码
     * @param phone 手机号
     * @return 验证码
     */
    String sendSmsCode(String phone);
}