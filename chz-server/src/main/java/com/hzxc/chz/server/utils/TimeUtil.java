package com.hzxc.chz.server.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static boolean isToday(Date time) {
        Calendar before = Calendar.getInstance();
        before.setTime(time);
        int beforeYear = before.get(Calendar.YEAR);
        int beforeDay = before.get(Calendar.DAY_OF_MONTH);
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);
        int nowDay = now.get(Calendar.DAY_OF_MONTH);

        if(beforeYear == nowYear && beforeDay == nowDay) {
            return true;
        }

        return false;
    }

    public static String getTodayStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public static long getTodaySeconds() {
        long current = System.currentTimeMillis();// 当前时间毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayzero = calendar.getTimeInMillis();
        return (current - todayzero) / 1000;
    }

    public static long getTommorrowSeconds() {
        long current = System.currentTimeMillis();// 当前时间毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayzero = calendar.getTimeInMillis();
        return (current - todayzero) / 1000;
    }

    public static String getTommorrowStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }
}
