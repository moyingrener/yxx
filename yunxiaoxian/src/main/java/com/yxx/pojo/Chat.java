package com.yxx.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Chat {

    private Integer chatId;
    private String fromUser;
    private String toUser;
    private String content;
    private Date theTime;
    private Integer goodsId;
    private boolean isPic;
    private boolean type;


    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTheTime() {
        return theTime;
    }

    public void setTheTime(Date theTime) {
        this.theTime = theTime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public boolean getIsPic() {
        return isPic;
    }

    public void setIsPic(boolean isPic) {
        this.isPic = isPic;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}