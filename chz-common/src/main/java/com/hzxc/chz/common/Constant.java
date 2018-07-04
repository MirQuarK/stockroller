package com.hzxc.chz.common;

public class Constant {
    // 不同服务的redis前缀，从yml配置。
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

    public static final String KEY_REC_TOPIC_SET = "REC_TOPIC_SET";//推荐
    public static final String KEY_HOT_TOPIC_SET = "HOT_TOPIC_SET";//热门
    public static final String KEY_ALL_TOPIC_ID_SET = "ALL_TOPIC_ID_SET";//所有topicId
    public static final String KEY_PHONE_TOPIC_SET = "PHONE_TOPIC_SET";//小视频
    public static final String KEY_SHARE_TOPIC_SET = "SHARE_TOPIC_SET";
    private static final String KEY_TAG_TOPIC_SET_PREFIX = "TAG_TOPIC_SET_";
    public static final String KEY_LATEST_LOAD_NEW_TOPIC_TIME = "LATEST_LOAD_NEW_TOPIC_TIME";
    public static final String KEY_DELAY_UPDATE_TOPIC_LIKE_COUNT_MAP = "DELAY_UPDATE_TOPIC_LIKE_COUNT_MAP";
    public static final String KEY_DELAY_UPDATE_TOPIC_PLAY_COUNT_MAP = "DELAY_UPDATE_TOPIC_PLAY_COUNT_MAP";
    public static final String KEY_USER_DAILY_DATA_PREFIX = "USER_DAILY_DATA_";
    public static final String KEY_FLY_COMMENTS_KEY_PREFIX = "FLY_COMMENTS_V2_";
    public static final String KEY_COMMENTS_DETAIL_KEY_PREFIX = "COMMENTS_DETAIL_";
    public static final String KEY_USER_SHORT_MSG_PREFIX = "USER_SHORT_MSG_";
    public static final String KEY_USER_SIGN_INFO_PREFIX = "USER_SIGN_INFO_";
    public static final String KEY_NEW_USER_PREFIX = "NEW_USER_";       // 目前仅新人奖励用，不要做其他用处了。
    public static final String KEY_ALL_REC_VIDEO_CATEGORY_POS = "ALL_REC_VIDEO_CATEGORY_POS";
    public static final String KEY_LOGIN_INTERVAL_ = "LOGIN_INTERVAL_";

    private static final String KEY_USER_WATCHED_TOPIC_SETS = "USER_WATCHED_";

    public static final String KEY_GOOD_TOPIC_ZSET = "GOOD_TOPIC_ZSET";

    public static final String SESSION_USER_ID_KEY = "userId";
    public static final String SESSION_USER_NICKNAME_KEY = "nickname";
    public static final String SESSION_USER_CHANNEL_ID = "userChannelId";
    public static final String SESSION_APP_TYPE = "apptype";
    public static final String SESSION_USER_ROLE = "userrole";

    public static final String GOLD_EXCHANGE = "金币兑换";
    public static final String RMB_WITHDRAW = "提现";
    public static final String KEY_LATEST_LOAD_REC_CATEGORY_TOPIC_TIME = "LATEST_LOAD_REC_CATEGORY_TOPIC_TIME";

    public static String getTopicKey(String topicId) {
        return "TOPIC_" + topicId;
    }

    public static String getTagTopicSetKey(String tag) {
        return KEY_TAG_TOPIC_SET_PREFIX + tag;
    }

    public static String getUserTopicEvent(Long userId, String topicId) {
        return "E_" + userId + "_" + topicId;
    }

    public static String getUserDailyDataKey(Long userId) {
        return KEY_USER_DAILY_DATA_PREFIX + userId;
    }

    public static String getCommentsKey(String topicId) {
        return KEY_FLY_COMMENTS_KEY_PREFIX + topicId;
    }

    public static String getCommentsDetailKey(String topicId) {
        return KEY_COMMENTS_DETAIL_KEY_PREFIX + topicId;
    }

    public static String getUserWatchedSetKey(Long userId) {
        return KEY_USER_WATCHED_TOPIC_SETS + userId;
    }

    public static String getUserGoodTopicZsetPosKey(String userId) {
        return "GOOD_TOPIC_ZSET_POS_" + userId;
    }

    public static String getUserShortMsgKey(Long userId) {
        return KEY_USER_SHORT_MSG_PREFIX + userId;
    }

    public static String getUserSignInfoKey(Long userId) {
        return KEY_USER_SIGN_INFO_PREFIX + userId;
    }

    public static String getRecVideoZsetKey(int position, String channel) {
        // 增加渠道
        return "REC_VIDEO_ZSET_" + position + "_" + channel;
    }

    // 每个渠道单独分离视频集。
    public static String getGoodTopicZsetCategoryKey(String category) {
        return  KEY_GOOD_TOPIC_ZSET + "_" + category+ "_";
    }

    // 每个渠道单独分离手机视频集。
    public static String getPhoneTopicZsetCategoryKey(String category) {
        return  KEY_PHONE_TOPIC_SET + "_" + category + "_";
    }

    // 每个渠道单独分离手机视频集。
    public static String getPhoneTopicZsetCategoryKeyAn(String category) {
        return  KEY_PHONE_TOPIC_SET + "_AN_" + category + "_";
    }

    public static String getUserShareGroupIdKey(Long userId, String groupId) {
        return "SHARE_GROUP_" + userId + "_" + groupId;
    }

    public static String getUserCommentList(Long userId) {
        return "USER_COMMENT_LIST_" + userId;
    }

    public static String getSmsKey(String mobile) {
        return "SEND_SMS_" + mobile;
    }

    public static String getFaqKey() {
        return "WX_VIDEO_FAQ_CACHE_KEY";
    }
}
