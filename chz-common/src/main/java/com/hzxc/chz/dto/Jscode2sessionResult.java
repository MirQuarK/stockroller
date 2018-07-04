package com.hzxc.chz.dto;

/**
 * create by chz on 2017/12/19
 */
public class Jscode2sessionResult {
    private static final String SUCCESS_CODE = "0";

    private String errcode;
    private String errmsg;

    private String openid;

    private String session_key;

    private String unionid;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public boolean isSuccess() {
        return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
    }
}
