package com.hzxc.chz.server.web;

import com.hzxc.chz.common.Constant;
import com.hzxc.chz.common.enums.Enumes;

import javax.servlet.http.HttpServletRequest;

/**
 * create by chz on 2018/1/26
 */
public class AbstractControler {

    int getUserId(HttpServletRequest request) {
        try {
            return (int) request.getSession().getAttribute(Constant.SESSION_USER_ID_KEY);
        } catch (Exception e) {}
        return  0;
    }
    String getUserMobile(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(Constant.SESSION_USER_MOBILE);
    }

    String getNickname(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(Constant.SESSION_USER_NICKNAME_KEY);
    }

    String getChannelId(HttpServletRequest request) {
        String ci = (String) request.getSession().getAttribute(Constant.SESSION_USER_CHANNEL_ID);
        if(ci==null) return "0";
        return ci.isEmpty() ? "0" : ci;
    }

    Enumes.LoginTypeEnum getLoginType(HttpServletRequest request) {
        Enumes.LoginTypeEnum ci = null;
        try {
            ci = (Enumes.LoginTypeEnum) request.getSession().getAttribute(Constant.SESSION_APP_TYPE);
        } catch (Exception e) {}
        if(ci==null) return Enumes.LoginTypeEnum.APP;
        return ci;
    }
}
