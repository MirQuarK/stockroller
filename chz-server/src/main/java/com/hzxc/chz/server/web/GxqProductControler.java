package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dao.GxqProductRepository;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
        try {
            lp = gxqProductService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success").data(lp);
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
}
