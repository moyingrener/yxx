package com.yxx.pojo;

import org.apache.commons.lang3.StringUtils;

public class image {
    private String[] url;
    private String[] test;

    public String getUrl() {
        return StringUtils.join(this.url,",");
    }

    public void setUrl(String[] url) {
        this.url = url;
    }

    public String[] getTest() {
        return test;
    }

    public void setTest(String[] test) {
        this.test = test;
    }
}