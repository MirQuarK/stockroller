package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.annotation.CheckLogin;
import com.hzxc.chz.server.wsc.StockMarketDataRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class wstestcontroler extends AbstractControler{
    private static Logger logger = LoggerFactory.getLogger(wstestcontroler.class);

//    @CheckLogin
    @RequestMapping(value = "getws", produces = "application/json")
    public JsonResult getProductById(
            @RequestParam String stockid,
            HttpServletRequest request) {
        int userId = getUserId(request);
        StockMarketDataRequest.getMarcketData(stockid);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }
}
