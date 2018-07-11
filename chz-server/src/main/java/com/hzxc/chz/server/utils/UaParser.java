package com.hzxc.chz.server.utils;

import java.util.Arrays;

/**
 * @author chz
 * @version create at：2017年3月28日 下午8:38:56
 */
public class UaParser {
    private static final String WINDOWS = "Windows";
    private static final String MACOS = "Mac OS";
    private static final String LINUX = "Linux";
    private static final String ANDROID = "Android";
    private static final String IPHONE = "iPhone";

    private static final String QQ = "QQ/";
    private static final String QQB = " QQB";
    private static final String MQQB = "MQQBrowser";
    private static final String WECHAT = "MicroMessenger";
    private static final String UC = "UCBrowser";
    private static final String SOUGOU_MOBILE = "SogouMobileBrowser";
    private static final String FIRE_FOX = "Firefox";
    private static final String CHROME = "Chrome";
    private static final String LIEBAO = "LieBao";

    public static UserAgent parse(String ua) {
        UserAgent userAgent = new UserAgent();
        // os
        if (isAndroid(ua)) {
            userAgent.setOs(ANDROID);
            userAgent.setAndroid(true);
            userAgent.setMobile(true);
        } else if (isIphone(ua)) {
            userAgent.setOs(IPHONE);
            userAgent.setIphone(true);
            userAgent.setMobile(true);
        } else if (isWindows(ua)) {
            userAgent.setOs(WINDOWS);
        } else if (isLiunx(ua)) {
            userAgent.setOs(LINUX);
        } else {
            userAgent.setOs("Other");
        }

        if (isWechat(ua)) {
            userAgent.setBrowser(WECHAT);
            userAgent.setWechat(true);
            userAgent.setMobile(true);
        } else if (isQQ(ua)) {
            userAgent.setBrowser("QQ");
            userAgent.setQq(true);
            userAgent.setMobile(true);
        } else if (isQQb(ua)) {
            userAgent.setBrowser("QQBrowser");
            userAgent.setQqb(true);
        } else if (isMQQb(ua)) {
            userAgent.setBrowser(MQQB);
            userAgent.setQqb(true);
            userAgent.setMobile(true);
        } else if (ua.contains(UC)) {
            userAgent.setBrowser(UC);
            userAgent.setMobile(true);
        } else if (ua.contains(SOUGOU_MOBILE)) {
            userAgent.setBrowser(SOUGOU_MOBILE);
        } else if (ua.contains(LIEBAO)) {
            userAgent.setBrowser(LIEBAO);
        } else if (ua.contains(FIRE_FOX)) {
            userAgent.setBrowser(FIRE_FOX);
        } else if (ua.contains(CHROME)) {
            userAgent.setBrowser(CHROME);
        } else {
            userAgent.setBrowser("Other");
        }

        if (!userAgent.isMobile()) {
            if (ua.contains("Mobile") || ua.contains("Phone") || ua.contains("Android")) {
                userAgent.setMobile(true);
            }
        }
        return userAgent;
    }

    public static boolean isWindows(String ua) {
        return ua.contains(WINDOWS);
    }

    public static boolean isMacOS(String ua) {
        return ua.contains(MACOS);
    }

    public static boolean isLiunx(String ua) {
        return ua.contains(LINUX);
    }

    public static boolean isAndroid(String ua) {
        return ua.contains(ANDROID);
    }

    public static boolean isIphone(String ua) {
        return ua.contains(IPHONE);
    }

    public static boolean isQQ(String ua) {
        return ua.contains(QQ);
    }

    public static boolean isQQb(String ua) {
        return ua.contains(QQB);
    }

    public static boolean isMQQb(String ua) {
        return ua.contains(MQQB);
    }

    public static boolean isWechat(String ua) {
        return ua.contains(WECHAT);
    }

    public static String getWechatVer(String ua) {
        return Arrays.stream(ua.split(" "))
                .filter(str -> str.startsWith("MicroMessenger/"))
                .limit(1)
                .findFirst()
                .map(str -> str.split("/|\\(")[1]).orElse("");
    }

    // public static void main(String[] args) {
    //     String ua = "Mozilla/5.0 (Linux; Android 4.4.4; Coolpad 8297-T01 Build/KTU84P; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043613 Safari/537.36 MicroMessenger/6.6.1.1220(0x26060133) NetType/WIFI Language/zh_CN MicroMessenger/6.6.1.1220(0x26060133) NetType/WIFI Language/zh_CN";
    //     System.out.println(getWechatVer(ua));
    //     ua = "MicroMessenger/6.5.10.1080 NetType/WIFI Language/zh_CN";
    //     System.out.println(getWechatVer(ua));
    //     ua = "";
    //     System.out.println(ua);
    // }
}
