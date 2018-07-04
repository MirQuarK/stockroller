package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.service.*;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class GxqLoginControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqLoginControler.class);

    @Autowired
    DistributionLock distributionLock;

    // 验证码登陆的,如果不存在,创建新用户.密码登陆的,不创建新用户.
    @RequestMapping(value = "gxqlogin", produces = "application/json")
    public JsonResult Login(HttpServletRequest request,
                                           @RequestParam String mobile,
                                           @RequestParam(required = false) String code,
                                           @RequestParam(required = false) String pass) {

        return new JsonResult<>().setCode(ResultCodeEnum.SUCCESS);
    }
}
