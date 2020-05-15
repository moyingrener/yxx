package com.yxx.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ChatRecord {

    private Integer chatRecordId;

    @JsonProperty("fromUser")
    private String fromUser;

    @JsonProperty("toUser")
    private String toUser;

    @JsonProperty("content")
    private String content;

    @JsonProperty("theTime")
    private Date theTime;

    @JsonProperty("goodsId")
    private Integer goodsId;

    @JsonProperty("isPic")
    private boolean isPic;

    @JsonProperty("isSelf")
    private boolean isSelf;

    @JsonProperty("type")
    private boolean type;

    public Integer getChatRecordId() {
        return chatRecordId;
    }

    public void setChatRecordId(Integer chatRecordId) {
        this.chatRecordId = chatRecordId;
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

    public boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}