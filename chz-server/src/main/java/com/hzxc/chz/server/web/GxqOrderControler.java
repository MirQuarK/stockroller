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
import java.util.*;

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
        int c = 0;
        try {
            c = gxqOrderService.getCount(userId, sdf.parse(start), sdf.parse(end));
            lo = gxqOrderService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("total_count", c);
        retmap.put("data", lo);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(retmap);
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

    @CheckLogin
    @RequestMapping(value = "addOrder", produces = "application/json")
    public JsonResult addOrder(@RequestParam int gainmoney,
                               @RequestParam int redeemmoney,
                               @RequestParam int stockcount,
                               @RequestParam int stockid,
                               @RequestParam int subscribemoney,
                               @RequestParam(required = false, defaultValue = "") String comment,
                               @RequestParam(required = false, defaultValue = "0") int productid,
                               HttpServletRequest request) {

        GxqOrder go = new GxqOrder();
        go.setCreateUser(getUserId(request));
        go.setGainMoney(gainmoney);
        go.setGxqProductId(productid);
        go.setComment(comment);
        go.setRedeemMoney(redeemmoney);
        go.setStockCount(stockcount);
        go.setStockId(stockid);
        go.setSubscribeMoney(subscribemoney);
        go.setStatus(1);
        go.setUserId(getUserId(request));
        long nowTime = System.currentTimeMillis();
        go.setCreateTime(new Date(nowTime));

        boolean ret = gxqOrderService.saveOrder(go);

        return new JsonResult().success().data(go.getId());
    }

    @CheckLogin(role = "ADMIN")
    @RequestMapping(value = "modifyOrder", produces = "application/json")
    public JsonResult modifyOrder(@RequestParam int userid,
                                 @RequestParam int orderid,
                                 HttpServletRequest request) {
        return new JsonResult().success();
    }

    @CheckLogin
    @RequestMapping(value = "delOrder", produces = "application/json")
    public JsonResult delOrder(@RequestParam int orderid,
                               HttpServletRequest request) {
        GxqOrder go = gxqOrderService.getByUserIdAndId(getUserId(request), orderid);
        if(go != null) {
            go.setStatus(0);
            gxqOrderService.saveOrder(go);
        }
        return new JsonResult().success();
    }
}
