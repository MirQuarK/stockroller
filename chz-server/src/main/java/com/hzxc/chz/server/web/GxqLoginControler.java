package com.hzxc.chz.server.web;

import com.hzxc.chz.common.Constant;
import com.hzxc.chz.common.enums.Enumes;
import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dao.GxqCurrencyRepository;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.dto.LoginResponse;
import com.hzxc.chz.entity.GxqLoginRecord;
import com.hzxc.chz.entity.GxqUser;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.service.*;
import com.hzxc.chz.service.DistributionLock;
import com.hzxc.chz.util.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class GxqLoginControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqLoginControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqUserService gxqUserService;

    @Autowired
    GxqCurrencyRepository gxqCurrencyRepository;

    @Value(value = "${utils.redis-prefix}")
    private String redisPrefix;

    @Autowired
    GxqRecordService gxqRecordService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DebugService debugService;

    @PostConstruct
    private void redisPrefixConfig() {
        Constant.REDIS_KEY_PREFIX = redisPrefix;
    }

    // 验证码登陆的,如果不存在,创建新用户.密码登陆的,不创建新用户.
    @RequestMapping(value = "login", produces = "application/json")
    public JsonResult<LoginResponse> Login(@RequestParam String mobile,
                                             @RequestParam(required = false) String pass,
                                             @RequestParam(required = false) String sms,
                                             HttpServletRequest request) {
        logger.info("login forward,mobile:[{}], pass:[{}], sms[{}]", pass, sms);
        debugService.debug(logger, "test");

        long nowTime = System.currentTimeMillis();
        JsonResult<LoginResponse> result = new JsonResult<>();

        GxqUser user1 = gxqUserService.getUserByMobile(Constant.REDIS_KEY_PREFIX, mobile);

        if (request.getSession().getAttribute(Constant.SESSION_USER_ID_KEY) != null) {
            result.setCode(ResultCodeEnum.SERVER_ERROR).msg("已登陆");
            return result;
        }

        if (mobile.length() != 11) {
            result.setCode(ResultCodeEnum.PARAMS_ERROR);
        }
        LoginResponse resp = new LoginResponse();
        String lockKey = "LOGIN_" + HttpHelper.getRemoteIp(request);

        boolean lret = distributionLock.tryLock(lockKey, 15);
        if(!lret) {
            return result.setCode(ResultCodeEnum.SERVER_ERROR).msg("请勿频繁登陆");
        }

        try {
            do {
                result.setCode(ResultCodeEnum.SUCCESS);
                GxqUser user = gxqUserService.getUserByMobile(Constant.REDIS_KEY_PREFIX, mobile);

                boolean newUser = false;

//                if(user != null) {
//                    if(sms != null) {
//                        if (!checkMobileCode(mobile, sms)) {
//                            result.setCode(ResultCodeEnum.SERVER_ERROR).msg("验证码错误");
//                            break;
//                        }
//                    } else {
//                        if(pass == null || !pass.equalsIgnoreCase(user.getPassword())) {
//                            result.setCode(ResultCodeEnum.SERVER_ERROR).msg("密码错误");
//                            break;
//                        }
//                    }
//                }

                // 用户不存在，有手机验证码可以创建新用户。
                if (user == null) {
                    if (!checkMobileCode(mobile, sms)) {
                        result.setCode(ResultCodeEnum.SERVER_ERROR).msg("用户不存在，验证码错误");
                        break;
                    }

                    //createUser
                    user = new GxqUser();
                    user.setCreateTime(new Date(nowTime));
                    user.setMobile(mobile);
                    user.setRole(Enumes.UserTypeEnum.USER.msg());
                    newUser = true;
                }

                user.setLastLoginTime(new Date(nowTime));

                gxqUserService.saveUser(Constant.REDIS_KEY_PREFIX, user);//saveOrUpdate

                resp.setUserId(user.getId());
                resp.setNewUser(newUser);

                request.getSession().setAttribute(Constant.SESSION_USER_ID_KEY, user.getId());
                request.getSession().setAttribute(Constant.SESSION_USER_MOBILE, user.getMobile());
                request.getSession().setAttribute(Constant.SESSION_USER_ROLE, user.getRole());

                logger.info("user login userid = " + user.getId());
                resp.setToken(request.getSession().getId());
                logger.debug("user login success,userId=[{}],sessionId:[{}]", user.getId(), resp.getToken());

                if (newUser) {
//                    gxqCurrencyRepository.initUserCurrency(user.getId());
                }

                GxqLoginRecord record = new GxqLoginRecord();
                record.setLoginTime(new Date(nowTime));
                record.setUserId(user.getId());
                record.setIp(HttpHelper.getRemoteIp(request));
                gxqRecordService.saveLoginRecord(record);

            } while (false);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("wechat login Exception " + e.getMessage());
            result.setCode(ResultCodeEnum.SERVER_ERROR).msg(e.getMessage());
        } finally {
            distributionLock.unLock(lockKey);
        }

        result.data(resp);
        logger.info("wechat login response:[{}]", result);

        if(result.getCode() != ResultCodeEnum.SUCCESS.val()) {
            request.getSession().invalidate();
        }

        return result;
    }

    @CheckLogin
    @RequestMapping(value = "logout", produces = "application/json")
    public JsonResult<LoginResponse> Logout(HttpServletRequest request) {
        JsonResult<LoginResponse> result = new JsonResult<>();

        LoginResponse resp = new LoginResponse();
        String lockKey = "LOGOUT_" + HttpHelper.getRemoteIp(request);
        try {
            distributionLock.lock(lockKey, 15);
            result.setCode(ResultCodeEnum.SUCCESS);

            int userId = getUserId(request);
            request.getSession().invalidate();

            GxqUser user = gxqUserService.getUserById(Constant.REDIS_KEY_PREFIX, userId);
            if(user != null) {
                logger.debug("user logout success,username=[{}]", user.getMobile());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResultCodeEnum.SERVER_ERROR).msg(e.getMessage());
        } finally {
            distributionLock.unLock(lockKey);
        }

        result.data(resp);
        logger.debug("wechat logout response:[{}]", result);

        return result;
    }

    @CheckLogin
    @RequestMapping(value = "setpass", produces = "application/json")
    public JsonResult setPass(@RequestParam String sms,
                                             @RequestParam String pass,
                                             HttpServletRequest request) {
        JsonResult result = new JsonResult<>().success();
        String mobile = getUserMobile(request);
        GxqUser user = gxqUserService.getUserByMobile(Constant.REDIS_KEY_PREFIX, mobile);
        if(user != null && checkMobileCode(mobile, sms)) {
            user.setPassword(pass);
            gxqUserService.saveUser(Constant.REDIS_KEY_PREFIX, user);

            // todo 记录修改密码日志。
        }

        return result;
    }

    private boolean checkMobileCode(String mobile, String code) {
        String key = Constant.getSmsKey(mobile);
        String cachecode = ""+redisTemplate.opsForValue().get(key);

        if (cachecode == null || cachecode.length() < 6) {
            return false;
        } else if (!cachecode.equalsIgnoreCase(code)) {
            return false;
        }

        return true;
    }
}
