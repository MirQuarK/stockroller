package com.hzxc.chz.server.web.pay.xingyejuhe.wx;

import com.google.gson.Gson;
import com.hzxc.chz.server.web.pay.xingyejuhe.common.Global;
import com.hzxc.chz.server.web.pay.xingyejuhe.common.Helper;
import com.hzxc.chz.server.web.pay.xingyejuhe.common.RequestData;


public class WXApi {
    private static RequestData data;

    static {
        initData();
    }

    private static void initData(){
        data = new RequestData();
        data.setVersion(Global.VERSION);
        data.setMerchant_id(Global.MERCHANT_ID);
        data.setTerm_id(Global.TERM_ID);
        data.setTimestamp(Helper.getTimestamp());
        data.setSign_type(Global.SIGN_TYPE);
    }

    /**
     * JSAPI线上支付
     * @return
     */
    public static RequestData jspay(){
        data.setBiz_type("wx.jspay");
        JSPay jsPay = new JSPay();
        jsPay.setTotal_amount(0.01);
        jsPay.setTrade_no(System.currentTimeMillis() + "");
        jsPay.setMch_create_ip("192.168.0.1");
        jsPay.setLimit_credit_pay("0");
        jsPay.setTime_expire("20201227091010");
        jsPay.setAttach("");
        jsPay.setBody("");
        jsPay.setGoods_tag("");
        jsPay.setOpenid("");
        jsPay.setNotify_url("");
        jsPay.setCallback_url("");
        jsPay.setType("0");
        data.setBiz_content(new Gson().toJson(jsPay));
        return data;
    }

    /**
     * App支付预下单
     * @return
     */
    public static RequestData appPay(){
        data.setBiz_type("wx.apppay");
        AppPay appPay = new AppPay();
        appPay.setTrade_no(System.currentTimeMillis()+"");
        appPay.setTotal_amount(0.01);
        appPay.setAttach("");
        appPay.setBody("");
        appPay.setMch_create_ip("192.168.0.1");
        appPay.setNotify_url("www.baidu.com");
        appPay.setTime_expire("20201227091010");
        data.setBiz_content(new Gson().toJson(appPay));
        return data;
    }

    /**
     * 扫码－B扫C支付
     * @return
     */
    public static RequestData tradePay(){
        data.setBiz_type("wx.tradepay");
        TradePay tradePay = new TradePay();
        tradePay.setTrade_no(System.currentTimeMillis() + "");
        //传入收款码信息
        tradePay.setAuth_code("");
        tradePay.setTotal_amount(0.01);
        tradePay.setAttach("");
        tradePay.setBody("");
        tradePay.setTime_expire(60);
        data.setBiz_content(new Gson().toJson(tradePay));
        return data;
    }

    /**
     * 扫码－C扫B预下单
     * @return
     */
    public static RequestData prePay(){
        data.setBiz_type("wx.prepay");
        PrePay tradePay = new PrePay();
        tradePay.setTrade_no(System.currentTimeMillis() + "");
        tradePay.setTotal_amount(0.01);
        tradePay.setAttach("");
        tradePay.setBody("");
        tradePay.setTime_expire("20201227091010");
        tradePay.setNotify_url("www.baidu.com");
        data.setBiz_content(new Gson().toJson(tradePay));
        return data;
    }

    /**
     * 预下单－查询支付结果
     * @return
     */
    public static RequestData prePayQuery(){
        data.setBiz_type("wx.prepayquery");
        PrePayQuery prePayQuery = new PrePayQuery();
        prePayQuery.setTrade_no("1511763380717");
        data.setBiz_content(new Gson().toJson(prePayQuery));
        return data;
    }

    /**
     * 查询订单
     * @return
     */
    public static RequestData query(){
        data.setBiz_type("wx.query");
        Query query = new Query();
        query.setTrade_no("1111");
        query.setWx_trade_no("");
        data.setBiz_content(new Gson().toJson(query));
        return data;
    }

    /**
     * 退款
     * @return
     */
    public static RequestData refund(){
        data.setBiz_type("wx.refund");
        Refund refund = new Refund();
        refund.setTrade_no("1111");
        refund.setWx_trade_no("");
        refund.setRefund_amount("0.01");
        refund.setOut_refund_no("");
        data.setBiz_content(new Gson().toJson(refund));
        return data;
    }

    /**
     * 退款查询
     * @return
     */
    public static RequestData refundQuery(){
        data.setBiz_type("wx.refundquery");
        RefundQuery refundQuery = new RefundQuery();
        refundQuery.setTrade_no("");
        refundQuery.setWx_trade_no("");
        refundQuery.setRefund_id("1");
        data.setBiz_content(new Gson().toJson(refundQuery));
        return data;
    }
}
