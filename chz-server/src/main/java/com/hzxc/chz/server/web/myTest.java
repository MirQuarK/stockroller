package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.annotation.AopTest;
import com.hzxc.chz.server.annotation.CheckLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class myTest {

    private static Logger logger = LoggerFactory.getLogger(myTest.class);

    @AopTest
    @RequestMapping(value = "myTest1", produces = "application/json")
    public JsonResult test(HttpServletRequest request) {
        logger.info("in myTest test");
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @CheckLogin
    @RequestMapping(value = "myTest2", produces = "application/json")
    public JsonResult test2(HttpServletRequest request) {
        logger.info("in myTest test2");
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @RequestMapping(value = "myTest", produces = "application/json")
    public JsonResult getProductById(
            HttpServletRequest request) {
        test(request);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }


}
