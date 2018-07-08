package com.hzxc.chz.server.web;

import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.entity.GxqCommission;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.service.GxqCommissionService;
import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@RestController
public class GxqCommissionControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqCommissionControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqCommissionService gxqCommissionService;

    @CheckLogin
    @RequestMapping(value = "getcommpage", produces = "application/json")
    public JsonResult getBillPage(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam int page,
                                  @RequestParam int count,
                                  HttpServletRequest request) {
        int userId = getUserId(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<GxqCommission> lbill = new LinkedList<>();
        try {
            lbill = gxqCommissionService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JsonResult().success().data(lbill);
    }

    @CheckLogin
    @RequestMapping(value = "getcommid", produces = "application/json")
    public JsonResult getBillId(@RequestParam int id,
                                HttpServletRequest request) {
        int userId = getUserId(request);
        GxqCommission lxom = gxqCommissionService.getByUserIdAndId(userId, id);
        return new JsonResult().success().data(lxom);
    }

    @CheckLogin
    @RequestMapping(value = "savecomm")
    public JsonResult saveBill(HttpServletRequest request) {
        return new JsonResult().success();
    }
}
