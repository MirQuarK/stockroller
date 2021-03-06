package com.hzxc.chz.entity;

import javax.persistence.*;
import java.util.Date;

// TODO 去外键
@Entity
@Table
public class GxqCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition="int default 0 ")
    private int userId;
    @Column(columnDefinition="int default 0 ")
    private int createUser;
    @Column(columnDefinition="int default 0 ")
    private int updateUser;
//    @Column( columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP comment '创建时间'")
    @Column( columnDefinition="datetime comment '创建时间'")   // 老版本mysql不支持两个时间戳设置,用这个
    private Date createTime;
    @Column( columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ")
    private Date updateTime;
    private String Comment;
    @Column(length = 4, columnDefinition="int(4) default 0 comment '状态' ")
    private int status;

    public int getUserId () {
        return userId;
    }

    public void setUserId (int userId) {
        this.userId = userId;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
