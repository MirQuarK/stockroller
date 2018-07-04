package com.hzxc.chz.common.enums;

public enum ResultCodeEnum {
    /** 成功 */
    SUCCESS(200, "success"),

    NOT_LOGIN(400, "not login"),

    PARAMS_ERROR(403, "parameters error"),

    NOT_SUPPORTED(410, "not supported"),

    NO_PERMISSION(444, "no permission"),

    INVALID_AUTHCODE(445, "invalid token"),

    TOO_FREQUENT(446, "太频繁的调用"),

    REPEAT_INVODE(447, "重复的调用"),

    NOT_REGIST(455, "用户未注册"),
    USER_EXIST(456, "用户已存在"),
    MOBILE_USED(457, "手机号已使用"),

    MOBIEL_UNBIND(460, "未绑定手机号"),

    BIND_TARGET_EXIST(462, "绑定对象已存在"),
    BIND_TARGET_BINDED(463, "合并对象已被合并过"),
    NOT_BINDED_WECHAT(464, "该微信非绑定微信"),

    UNKNOWN_ERROR(499, "未知错误"),

    SERVER_ERROR(500,"服务器错误");

    ResultCodeEnum(int val, String msg){
        this.val = val;
        this.msg = msg;
    }

    public int val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private int val;
    private String msg;
}
