package com.hzxc.chz.server.web.pay.xingyejuhe.common;

import com.google.gson.Gson;

public class Sign {
    public static String getSign(RequestData data){
        String sign = "";
        Gson gson = new Gson();
        sign = new EncryptUtilMd5().getMD5ofStr(gson.toJson(data) + "&" + data.getTimestamp() + Global.TOKEN).toLowerCase();
        return sign;
    }
}
