package com.hzxc.chz.entity;

import javax.persistence.*;
import java.util.Date;

// TODO 去外键
// 公司产品
@Entity
@Table
public class GxqProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String productName;
    private int stockId;
    private String Comment;
    @Column( columnDefinition="int(11) comment '申购金额'")
    private int subscribeMoney;
    @Column( columnDefinition="int(11) comment '权利金'")
    private int rightMoney;
    @Column( columnDefinition="int(11) comment '申购周期'")
    private int subscribeTime;
    private String state;

    private int createUser;
    private int updateUser;
//    @Column( columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP comment '创建时间'")
    @Column( columnDefinition="datetime comment '创建时间'")   // 老版本mysql不支持两个时间戳设置,用这个
    private Date createTime;
    @Column( columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ")
    private Date updateTime;

    @Column(length = 4, columnDefinition="int(4) comment '状态' ")
    private int status;

    public String getProductName () {
        return productName;
    }

    public void setProductName (String productName) {
        this.productName = productName;
    }

    public int getStockId () {
        return stockId;
    }

    public void setStockId (int stockId) {
        this.stockId = stockId;
    }

    public int getSubscribeMoney () {
        return subscribeMoney;
    }

    public void setSubscribeMoney (int subscribeMoney) {
        this.subscribeMoney = subscribeMoney;
    }

    public int getRightMoney () {
        return rightMoney;
    }

    public void setRightMoney (int rightMoney) {
        this.rightMoney = rightMoney;
    }

    public int getSubscribeTime () {
        return subscribeTime;
    }

    public void setSubscribeTime (int subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getState () {
        return state;
    }

    public void setState (String state) {
        this.state = state;
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
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
