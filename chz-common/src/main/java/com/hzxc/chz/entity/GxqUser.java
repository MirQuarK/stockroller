package com.hzxc.chz.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = {@Index(name = "gxq_user_index_mobile",  columnList="mobile", unique = true)})
public class GxqUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 10)
    private String province;
    @Column(length = 10)
    private String city;
    @Column(length = 10)
    private String country;

    private String password;
    private String headUrl;

    private Date createTime;
    private Date lastLoginTime;//上一次登陆时间

    @Column( columnDefinition="VARCHAR(8) default 'USER'")
    private String role;

    private Date birthday;
    @Column(columnDefinition="int default 0 ")
    private int org;
    @Column(length = 24)
    private String mobile;
    @Column(columnDefinition="int default 0 ")
    private int wechatWalletBind;

    @Column(columnDefinition="int(4) default 1 ")
    private int status;

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getRole () {
        return role;
    }

    public void setRole (String role) {
        this.role = role;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getOrg() {
        return org;
    }

    public void setOrg(int org) {
        this.org = org;
    }
}
