package com.hzxc.chz.server.service.impl;

import com.hzxc.chz.server.service.SmsService;
import com.hzxc.chz.server.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by hdwang on 2017/12/18.
 * 短信服务
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${sms.url}")
    private String url;

    @Value("${sms.corpId}")
    private String corpId;

    @Value("${sms.corpPwd}")
    private String corpPwd;

    @Value("${sms.corpService}")
    private String corpService;

    @Value("${sms.smsCodeTemplate}")
    private String smsCodeTemplate;

    @Override
    public String sendSmsCode(String phone) {

        String smsCode = this.generateSmsCode();
        String content = smsCodeTemplate.replace("{smsCode}",smsCode);

        Map<String,Object> params = new HashMap<>();
        params.put("corp_id",corpId);
        params.put("corp_pwd",corpPwd);
        params.put("corp_service",corpService);
        params.put("mobile",phone);
        params.put("msg_content",content);
        try {
            String result = HttpUtils.doPost(url, params);
            if ("0#1".equals(result)) {
                return smsCode;
            }
        }catch (Exception ex){
            //吃掉异常
        }
        //发送失败
        return null;
    }

    /**
     * 产生一个手机验证码
     * @return 验证码
     */
    private String generateSmsCode() {
        String smsCode = StringUtils.EMPTY;
        Random random = new Random();
        for(int i=0;i<6;i++){
            smsCode += random.nextInt(10);
        }
        return smsCode;
    }
}
