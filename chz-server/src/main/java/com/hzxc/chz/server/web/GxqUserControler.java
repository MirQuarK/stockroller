package com.hzxc.chz.server.web;

import com.hzxc.chz.common.Constant;
import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class GxqUserControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqUserControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "sendsms", produces = "application/json")
    public JsonResult sendSms(
            @RequestParam String mobile,
            HttpServletRequest request) {
        if(mobile != null && mobile.length() != 11) {
            return new JsonResult().setCode(ResultCodeEnum.PARAMS_ERROR).msg("请输入正确的手机号");
        }

        String key = Constant.getSmsKey(mobile);
        String code = (String)redisTemplate.opsForValue().get(key);

        if(code == null) {
//            code = smsService.sendSmsCode(mobile);//"" + (int)((Math.random()*9+1)*100000);
            redisTemplate.opsForValue().set(key, code, 60 * 200, TimeUnit.SECONDS);
        }

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }
}
