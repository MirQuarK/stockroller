package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.entity.GxqOrder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam int page,
            @RequestParam int count,
            HttpServletRequest request) {
        int userId = getUserId(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<GxqOrder> lo = new LinkedList<>();
        try {
            lo = gxqOrderService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(lo);
    }

    @CheckLogin
    @RequestMapping(value = "getOrderById", produces = "application/json")
    public JsonResult getOrderById(
            @RequestParam int id,
            HttpServletRequest request) {
        int userId = getUserId(request);

        GxqOrder o = gxqOrderService.getByUserIdAndId(userId, id);
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(o);
    }
}
