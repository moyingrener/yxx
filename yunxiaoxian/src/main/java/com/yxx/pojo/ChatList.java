package com.yxx.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ChatList {

    private String AOpenID;
    private String BOpenID;
    private Date modifyTime;

    //物品信息
    private Integer goodsID;
    private Integer status;
    private String[] goodsImage;
    private BigDecimal goodsPrice;

    //B（卖家）的用户名和头像
    private String userName;
    private String userImage;

    //卖家ID
    private String seller;


    public String getAOpenID() {
        return AOpenID;
    }

    public void setAOpenID(String AOpenID) {
        this.AOpenID = AOpenID;
    }

    public String getBOpenID() {
        return BOpenID;
    }

    public void setBOpenID(String BOpenID) {
        this.BOpenID = BOpenID;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String[] getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String[] goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}