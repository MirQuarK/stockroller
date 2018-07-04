package com.hzxc.chz.entity;

import javax.persistence.*;
import java.util.Date;

// TODO 去外键
// 用户订单
@Entity
@Table(indexes = {@Index(name = "gxq_order_index_create_user",  columnList="create_user, status", unique = false),
        @Index(name = "gxq_order_index_create_time", columnList="create_time", unique = false)})
public class GxqOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int createUser;

    private Long gxqProductId;
    private int stockId;
    @Column( columnDefinition="int(11) comment '申购金额'")
    private int subscribeMoney;
    private int stockCount;
    @Column( columnDefinition="int(11) comment '赎回金额'")
    private int redeemMoney;
    @Column( columnDefinition="int(11) comment '盈利金额'")
    private int gainMoney;

    private int updateUser;
//    @Column( columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP comment '创建时间'")
    @Column( columnDefinition="datetime comment '创建时间'")   // 老版本mysql不支持两个时间戳设置,用这个
    private Date createTime;
    @Column( columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ")
    private Date updateTime;

    private String Comment;
    @Column(length = 4, columnDefinition="int(4) comment '状态' ")
    private int status;

    @Transient
    private GxqProduct gxqProduct;

    public GxqProduct getGxqProduct () {
        return gxqProduct;
    }

    public void setGxqProduct (GxqProduct gxqProduct) {
        this.gxqProduct = gxqProduct;
    }

    public Long getGxqProductId () {
        return gxqProductId;
    }

    public void setGxqProductId (Long gxqProductId) {
        this.gxqProductId = gxqProductId;
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

    public int getStockCount () {
        return stockCount;
    }

    public void setStockCount (int stockCount) {
        this.stockCount = stockCount;
    }

    public int getRedeemMoney () {
        return redeemMoney;
    }

    public void setRedeemMoney (int redeemMoney) {
        this.redeemMoney = redeemMoney;
    }

    public int getGainMoney () {
        return gainMoney;
    }

    public void setGainMoney (int gainMoney) {
        this.gainMoney = gainMoney;
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
