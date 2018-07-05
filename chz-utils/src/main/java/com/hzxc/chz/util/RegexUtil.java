package com.hzxc.chz.util;

/**
 * create by chz on 2018/1/29
 */
public class RegexUtil {
    public static boolean isMobileNumber(String s) {
        return s.matches("1\\d{10}");
    }

    public static boolean isEmail(String s) {
        return s.matches("[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+");
    }
}
