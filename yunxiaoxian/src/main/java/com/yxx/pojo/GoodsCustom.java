package com.yxx.pojo;

import java.util.Date;

public class GoodsCustom extends Goods {
    private String userName;
    private String userImage;

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
