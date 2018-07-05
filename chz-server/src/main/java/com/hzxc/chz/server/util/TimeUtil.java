package com.hzxc.chz.server.util;

/**
 * create by chz on 2018/3/5
 */
public class TimeUtil {
    public static String castHumanRead(int seconds) {
        int sec = seconds % 60;
        int min = seconds / 60 % 60;
        int hour = seconds / 60 / 60 % 60;
        return (hour < 10 ? "0" + hour : hour) + ":"
                + (min < 10 ? "0" + min : min) + ":"
                + (sec < 10 ? "0" + sec : sec);
    }
}
