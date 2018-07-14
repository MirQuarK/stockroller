package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.entity.GxqProduct;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.service.GxqProductService;
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
public class GxqProductControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqProductControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqProductService gxqProductService;

    @CheckLogin
    @RequestMapping(value = "getProductByDate", produces = "application/json")
    public JsonResult getProductByDate(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam int page,
            @RequestParam int count,
            HttpServletRequest request) {
        int userId = getUserId(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<GxqProduct> lp = new LinkedList<>();
        int c = 0;
        try {
            c = gxqProductService.getCount(userId, sdf.parse(start), sdf.parse(end));
            lp = gxqProductService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, Object> retmap = new HashMap<>();
        retmap.put("total_count", c);
        retmap.put("data", lp);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(retmap);
    }

    @CheckLogin
    @RequestMapping(value = "getProductById", produces = "application/json")
    public JsonResult getProductById(
            @RequestParam int id,
            HttpServletRequest request) {
        int userId = getUserId(request);

        GxqProduct gp = gxqProductService.getByUserIdAndId(userId, id);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(gp);
    }

    @CheckLogin
    @RequestMapping(value = "addProduct", produces = "application/json")
    public JsonResult addProduct(@RequestParam int subscribtime,
                                 @RequestParam int stockid,
                                 @RequestParam int subscribemoney,
                                 @RequestParam(required = false, defaultValue = "") String comment,
                                 @RequestParam(required = false, defaultValue = "0") String productname,
                                 HttpServletRequest request) {

        GxqProduct gp = new GxqProduct();
        gp.setCreateUser(getUserId(request));
        gp.setComment(comment);
        gp.setStockId(stockid);
        gp.setSubscribeMoney(subscribemoney);
        gp.setProductName(productname);
        gp.setSubscribeTime(subscribtime);
        gp.setStatus(1);
        long nowTime = System.currentTimeMillis();
        gp.setCreateTime(new Date(nowTime));

        boolean ret = gxqProductService.saveProduct(gp);

        return new JsonResult().success().data(ret);
    }

    @CheckLogin(role = "ADMIN")
    @RequestMapping(value = "modifyProduct", produces = "application/json")
    public JsonResult modifyProduct(@RequestParam int userid,
                                  @RequestParam int productid,
                                  HttpServletRequest request) {
        return new JsonResult().success();
    }
}
