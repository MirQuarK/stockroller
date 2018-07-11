package com.hzxc.chz.server.web;

import com.hzxc.chz.common.Constant;
import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dao.GxqUserRepository;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.entity.GxqUser;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.service.SmsService;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class GxqUserControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqUserControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqUserRepository gxqUserRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SmsService smsService;

    @CheckLogin(role = "ADMIN")
    @RequestMapping(value = "emp/js/getuserlist", produces = "application/json")
    public JsonResult getUserList(@RequestParam int page,
                                  @RequestParam int count,
                                  HttpServletRequest request) {
        Map<String, Object> retmap = new HashMap<>();
        int c = gxqUserRepository.getCount();

        Page<GxqUser> lu = gxqUserRepository.findAll(new PageRequest(page, count));
        retmap.put("total_count", c);
        retmap.put("data", lu.getContent());
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(retmap);
    }

    @CheckLogin(role = "ADMIN")
    @RequestMapping(value = "emp/js/deluser", produces = "application/json")
    public JsonResult delUser(@RequestParam int userid,
                              HttpServletRequest request) {
        GxqUser u = gxqUserRepository.findById(userid);
        u.setStatus(0);
        gxqUserRepository.save(u);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @CheckLogin()
    @RequestMapping(value = "user/js/userinfo", produces = "application/json")
    public JsonResult getUserInfo(HttpServletRequest request) {
        int userId = getUserId(request);

        GxqUser lu = gxqUserRepository.findById(userId);
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(lu);
    }



    // 发送手机验证码。
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
            code = smsService.sendSmsCode(mobile);//"" + (int)((Math.random()*9+1)*100000);
            redisTemplate.opsForValue().set(key, code, 60 * 200, TimeUnit.SECONDS);
        }

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }
}
