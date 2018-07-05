package com.hzxc.chz.entity;

import javax.persistence.*;
import java.util.Date;

// TODO 去外键
// 用户账单
@Entity
@Table
public class GxqBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderId;
    @Column(length = 4, columnDefinition="int(4) default 1 comment '账单类型' ")
    private int billType;
    @Column(columnDefinition="int default 0 ")
    private int billMoney;
    @Column(length = 4, columnDefinition="int(4) default 1 comment '账单收支类型' ")
    private int billInout;

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
    @Column(length = 4, columnDefinition="int(4) default 1 comment '状态' ")
    private int status;

    // 管理订单
    @Transient
    private GxqOrder gxqOrder;


    public int getUserId () {
        return userId;
    }

    public void setUserId (int userId) {
        this.userId = userId;
    }

    public int getOrderId () {
        return orderId;
    }

    public void setOrderId (int orderId) {
        this.orderId = orderId;
    }

    public GxqOrder getGxqOrder() {
        return gxqOrder;
    }

    public void setGxqOrder(GxqOrder gxqOrder) {
        this.gxqOrder = gxqOrder;
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
        id = id;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public int getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(int billMoney) {
        this.billMoney = billMoney;
    }

    public int getBillInout() {
        return billInout;
    }

    public void setBillInout(int billInout) {
        this.billInout = billInout;
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
