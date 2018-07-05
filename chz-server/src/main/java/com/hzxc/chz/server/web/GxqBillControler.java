package com.hzxc.chz.server.web;

import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.entity.GxqBill;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.service.GxqBillService;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Watchable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
public class GxqBillControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqBillControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqBillService gxqBillService;

    @CheckLogin
    @RequestMapping(value = "getbillpage", produces = "application/json")
    public JsonResult getBillPage(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam int page,
                                  @RequestParam int count,
                                  HttpServletRequest request) {
        int userId = getUserId(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<GxqBill> lbill = new LinkedList<>();
        try {
            lbill = gxqBillService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JsonResult().success().data(lbill);
    }

    @CheckLogin
    @RequestMapping(value = "getbillid", produces = "application/json")
    public JsonResult getBillId(@RequestParam int id,
                                HttpServletRequest request) {
        int userId = getUserId(request);
        GxqBill lbill = gxqBillService.getByUserIdAndId(userId, id);
        return new JsonResult().success().data(lbill);
    }

    @CheckLogin
    @RequestMapping(value = "savebill")
    public JsonResult saveBill(HttpServletRequest request) {
        return new JsonResult().success();
    }
}
