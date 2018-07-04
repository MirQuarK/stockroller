package com.hzxc.chz.dto;

/**
 * 登录请求的响应
 */
public class LoginResponse extends CommonResponse {
    private Long userId;
    private String token;
    private String wechatNick;
    private String unionId;
    private String openId;
    private int newPrize;
    private boolean newUser = false;
    private String channel;

    public String getOpenId () {
        return openId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }

    public boolean getNewUser () {
        return newUser;
    }

    public void setNewUser (boolean newUser) {
        this.newUser = newUser;
    }

    public String getChannel () {
        return channel;
    }

    public void setChannel (String channel) {
        this.channel = channel;
    }

    public int getNewPrize () {
        return newPrize;
    }

    public void setNewPrize (int newPrize) {
        this.newPrize = newPrize;
    }

    public String getUnionId () {
        return unionId;
    }

    public void setUnionId (String unionId) {
        this.unionId = unionId;
    }

    public String getWechatNick () {
        return wechatNick;
    }


    public void setWechatNick (String wechatNick) {
        this.wechatNick = wechatNick;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
