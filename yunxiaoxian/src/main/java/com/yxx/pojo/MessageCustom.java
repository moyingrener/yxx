package com.yxx.pojo;


public class MessageCustom extends Message {
    private String speaker;
    private String speakerImage;
    private String buyer;
    private String[] goodsImage;
    private String goodsName;
    private String listener;

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getSpeakerImage() {
        return speakerImage;
    }

    public void setSpeakerImage(String speakerImage) {
        this.speakerImage = speakerImage;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String[] getGoodsImage() {
        return goodsImage;
    }
    public void setGoodsImage(String[] goodsImage) {
        this.goodsImage = goodsImage;
    }
    public void setImage(String url){
        this.goodsImage = url.split(",");
    }

}
