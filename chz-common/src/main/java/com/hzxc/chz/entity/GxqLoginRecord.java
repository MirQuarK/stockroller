package com.hzxc.chz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table
public class GxqLoginRecord extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Date loginTime;
    @Column(length = 15)
    private String ip;
    @Column
    private Integer sceneId;//小程序的场景值ID
    @Column(length = 36)
    private String shareUuid;//从自定义分享进入

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getShareUuid() {
        return shareUuid;
    }

    public void setShareUuid(String shareUuid) {
        this.shareUuid = shareUuid;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }
}
