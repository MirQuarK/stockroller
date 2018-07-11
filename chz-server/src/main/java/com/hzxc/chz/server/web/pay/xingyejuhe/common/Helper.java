package com.hzxc.chz.server.web.pay.xingyejuhe.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYmmddHHmmss");
        return sdf.format(new Date());
    }
}
