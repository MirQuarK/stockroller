package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class GxqProductControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqProductControler.class);

    @Autowired
    DistributionLock distributionLock;

    @CheckLogin
    @RequestMapping(value = "getProductByDate", produces = "application/json")
    public JsonResult getProductByDate(
            @RequestParam Date start,
            @RequestParam Date end,
            HttpServletRequest request) {
        int userId = getUserId(request);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @CheckLogin
    @RequestMapping(value = "getProductById", produces = "application/json")
    public JsonResult getProductById(
            @RequestParam int id,
            HttpServletRequest request) {
        int userId = getUserId(request);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }
}
