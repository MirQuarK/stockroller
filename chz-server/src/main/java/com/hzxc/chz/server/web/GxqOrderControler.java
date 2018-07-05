package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.service.GxqOrderService;
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
public class GxqOrderControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqOrderControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqOrderService gxqOrderService;

    @CheckLogin
    @RequestMapping(value = "getOrderByDate", produces = "application/json")
    public JsonResult getOrderByDate(
            @RequestParam Date start,
            @RequestParam Date end,
            @RequestParam int page,
            @RequestParam int count,
            HttpServletRequest request) {
        int userId = getUserId(request);
        gxqOrderService.getByTimePage(userId, start, end, page * count, count);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @CheckLogin
    @RequestMapping(value = "getOrderById", produces = "application/json")
    public JsonResult getOrderById(
            @RequestParam int id,
            HttpServletRequest request) {
        int userId = getUserId(request);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }
}
