package com.hzxc.chz.server.web.pay.xingyejuhe.wx;

public class RefundQuery {
    private String trade_no;
    private String wx_trade_no;
    private String refund_id;

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getWx_trade_no() {
        return wx_trade_no;
    }

    public void setWx_trade_no(String wx_trade_no) {
        this.wx_trade_no = wx_trade_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }
}
