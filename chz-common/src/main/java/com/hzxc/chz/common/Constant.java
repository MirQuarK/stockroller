package com.hzxc.chz.common;

public class Constant {
    public static String REDIS_KEY_PREFIX = "";

    public static final int ONE_HOUR_SECONDS = 60 * 60;
    public static final int ONE_DAY_SECONDS = 24 * 60 * 60;
    public static final int ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;

    public static final String SUCCESS = "SUCCESS";
    public static final String CALL_ERROR="调用异常";
    public static final String BUSINESS_ERROR="业务异常";

    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;

    public static final int STATUS_ON = 1;
    public static final int STATUS_OFF = 0;
    public static final int STATUS_DELETE = -1;

    public static final String KEY_USER_SHORT_MSG_PREFIX = "USER_SHORT_MSG_";
    public static final String KEY_NEW_USER_PREFIX = "NEW_USER_";       // 目前仅新人奖励用，不要做其他用处了。

    public static final String SESSION_USER_ID_KEY = "userId";
    public static final String SESSION_USER_NICKNAME_KEY = "nickname";
    public static final String SESSION_USER_CHANNEL_ID = "userChannelId";
    public static final String SESSION_APP_TYPE = "apptype";
    public static final String SESSION_USER_ROLE = "userrole";
    public static final String SESSION_USER_MOBILE = "userMobile";

    public static String getUserShortMsgKey(int userId) {
        return REDIS_KEY_PREFIX + KEY_USER_SHORT_MSG_PREFIX + userId;
    }

    public static String getSmsKey(String mobile) {
        return REDIS_KEY_PREFIX + "SEND_SMS_" + mobile;
    }
}
