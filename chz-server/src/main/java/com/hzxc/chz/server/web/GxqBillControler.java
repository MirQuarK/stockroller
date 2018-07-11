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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class GxqBillControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqBillControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqBillService gxqBillService;

    @CheckLogin
    @RequestMapping(value = "getBillByDate", produces = "application/json")
    public JsonResult getBillPage(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam int page,
                                  @RequestParam int count,
                                  HttpServletRequest request) {
        int userId = getUserId(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<GxqBill> lb = new LinkedList<>();
        int c = 0;
        try {
            c = gxqBillService.getCount(userId, sdf.parse(start), sdf.parse(end));
            lb = gxqBillService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> retmap = new HashMap<>();
        retmap.put("total_count", c);
        retmap.put("data", lb);

        return new JsonResult().success().data(retmap);
    }

    @CheckLogin
    @RequestMapping(value = "getBillById", produces = "application/json")
    public JsonResult getBillId(@RequestParam int id,
                                HttpServletRequest request) {
        int userId = getUserId(request);
        GxqBill lbill = gxqBillService.getByUserIdAndId(userId, id);
        return new JsonResult().success().data(lbill);
    }

    @CheckLogin(role = "ADMIN")
    @RequestMapping(value = "modifyBill", produces = "application/json")
    public JsonResult modifyBill(@RequestParam int userid,
                                @RequestParam int billid,
                                HttpServletRequest request) {
        return new JsonResult().success();
    }

    @CheckLogin(role = "ADMIN")
    @RequestMapping(value = "addBill", produces = "application/json")
    public JsonResult addBill(@RequestParam int userid,
                              @RequestParam int inout,
                              @RequestParam int money,
                              @RequestParam int type,
                              @RequestParam(required = false, defaultValue = "") String comment,
                              @RequestParam(required = false, defaultValue = "0") int orderid,
                              HttpServletRequest request) {

        GxqBill gb = new GxqBill();
        gb.setBillInout(inout);
        gb.setBillMoney(money);
        gb.setBillType(type);
        gb.setComment(comment);
        gb.setCreateUser(getUserId(request));
        gb.setOrderId(orderid);
        gb.setStatus(1);
        gb.setUserId(userid);
        long nowTime = System.currentTimeMillis();
        gb.setCreateTime(new Date(nowTime));

        boolean ret = gxqBillService.saveBill(gb);

        return new JsonResult().success().data(ret);
    }

    @CheckLogin
    @RequestMapping(value = "savebill")
    public JsonResult saveBill(HttpServletRequest request) {
        return new JsonResult().success();
    }
}
