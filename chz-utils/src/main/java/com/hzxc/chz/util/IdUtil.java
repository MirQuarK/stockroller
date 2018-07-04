package com.hzxc.chz.util;

import java.util.UUID;

public class IdUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String getUpperCaseUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
    public static String getLowerCaseUUID(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
