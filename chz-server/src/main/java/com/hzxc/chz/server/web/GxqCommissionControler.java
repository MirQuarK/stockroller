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
import java.util.*;

@RestController
public class GxqCommissionControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqCommissionControler.class);

    @Autowired
    DistributionLock distributionLock;

    @Autowired
    GxqCommissionService gxqCommissionService;

    @CheckLogin
    @RequestMapping(value = "getCommByDate", produces = "application/json")
    public JsonResult getCommList(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam int page,
                                  @RequestParam int count,
                                  HttpServletRequest request) {
        int userId = getUserId(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<GxqCommission> lc = new LinkedList<>();
        int c = 0;
        try {
            c = gxqCommissionService.getCount(userId, sdf.parse(start), sdf.parse(end));
            lc = gxqCommissionService.getByTimePage(userId, sdf.parse(start), sdf.parse(end), page * count, count);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> retmap = new HashMap<>();
        retmap.put("total_count", c);
        retmap.put("data", lc);


        return new JsonResult().success().data(retmap);
    }

    @CheckLogin
    @RequestMapping(value = "getCommById", produces = "application/json")
    public JsonResult getCommById(@RequestParam int id,
                                HttpServletRequest request) {
        int userId = getUserId(request);
        GxqCommission lc = gxqCommissionService.getByUserIdAndId(userId, id);
        return new JsonResult().success().data(lc);
    }

    @CheckLogin
    @RequestMapping(value = "addComm", produces = "application/json")
    public JsonResult addComm( @RequestParam int totalcomm,
                               @RequestParam int leftcomm,
                               @RequestParam(required = false, defaultValue = "") String comment,
                               HttpServletRequest request) {

        GxqCommission gc = new GxqCommission();
        gc.setCreateUser(getUserId(request));
        gc.setTotalCommission(totalcomm);
        gc.setLeftCommission(leftcomm);
        gc.setComment(comment);
        gc.setStatus(1);
        gc.setUserId(getUserId(request));
        long nowTime = System.currentTimeMillis();
        gc.setCreateTime(new Date(nowTime));

        boolean ret = gxqCommissionService.saveComm(gc);

        return new JsonResult().success().data(ret);
    }

    @CheckLogin
    @RequestMapping(value = "savecomm")
    public JsonResult saveBill(HttpServletRequest request) {
        return new JsonResult().success();
    }
}
