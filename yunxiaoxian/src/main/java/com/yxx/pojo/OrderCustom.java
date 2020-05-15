package com.yxx.pojo;


import java.math.BigDecimal;

public class OrderCustom extends Orders {
    private String goodsName;
    private BigDecimal goodsPrice;
    private String[] goodsImage;
    private Integer status;
    private String userName;
    private String userImage;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String[] getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String[] goodsImage) {
        this.goodsImage = goodsImage;
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
}
