package com.hzxc.chz.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = {@Index(name = "gxq_user_index_mobile",  columnList="mobile", unique = true)})
public class GxqUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32)
    private String nickName;
    private int sex;
    @Column(length = 10)
    private String province;
    @Column(length = 10)
    private String city;
    @Column(length = 10)
    private String country;
    private String headUrl;

    private int sceneId;//首次注册是来自哪个场景ID

    /**微信相关参数**/
    @Column(length = 64)
    private String unionId;//微信用户唯一标示
    @Column(length = 64)
    private String openId;//微信用户在当前app中的唯一标示
    private Date createTime;
    private Date lastLoginTime;//上一次登陆时间

    private Long inviter;//谁邀请的
    private Long master;//师父userId
    private String masterPrize;//给师傅的奖励
    private Date birthday;
    private int org;
    private String channel;
    private String wechatInfo;  // 绑定微信的信息
    @Column(length = 24)
    private String mobile;

    @Column(length = 24)
    private String wechatId;
    @Column(length = 24)
    private String alipayId;
    @Column(length = 24)
    private String alipayRealName;
    @Column(length = 24)
    private String wechatRealName;
    private int wechatWalletBind;

    @Column(length = 256)
    private String wechatAccessToken;

    @Column(length = 256)
    private String wechatRefreshToken;

    public String getWechatRealName () {
        return wechatRealName;
    }

    public void setWechatRealName (String wechatRealName) {
        this.wechatRealName = wechatRealName;
    }


    public String getWechatAccessToken () {
        return wechatAccessToken;
    }

    public void setWechatAccessToken (String wechatAccessToken) {
        this.wechatAccessToken = wechatAccessToken;
    }

    public String getWechatRefreshToken () {
        return wechatRefreshToken;
    }

    public void setWechatRefreshToken (String wechatRefreshToken) {
        this.wechatRefreshToken = wechatRefreshToken;
    }

    public String getWechatId () {
        return wechatId;
    }

    public void setWechatId (String wechatId) {
        this.wechatId = wechatId;
    }

    public String getAlipayId () {
        return alipayId;
    }

    public void setAlipayId (String alipayId) {
        this.alipayId = alipayId;
    }

    public String getAlipayRealName () {
        return alipayRealName;
    }

    public void setAlipayRealName (String alipayRealName) {
        this.alipayRealName = alipayRealName;
    }

    public int getWechatWalletBind () {
        return wechatWalletBind;
    }

    public void setWechatWalletBind (int wechatWalletBind) {
        this.wechatWalletBind = wechatWalletBind;
    }

    public Date getBirthday () {
        return birthday;
    }

    public void setBirthday (Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile () {
        return mobile;
    }

    public void setMobile (String mobile) {
        this.mobile = mobile;
    }

    public String getWechatInfo () {
        return wechatInfo;
    }

    public void setWechatInfo (String wechatInfo) {
        this.wechatInfo = wechatInfo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getInviter() {
        return inviter;
    }

    public void setInviter(Long inviter) {
        this.inviter = inviter;
    }

    public Long getMaster() {
        return master;
    }

    public void setMaster(Long master) {
        this.master = master;
    }

    public String getMasterPrize() {
        return masterPrize;
    }

    public void setMasterPrize(String masterPrize) {
        this.masterPrize = masterPrize;
    }

    public int getOrg() {
        return org;
    }

    public void setOrg(int org) {
        this.org = org;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
