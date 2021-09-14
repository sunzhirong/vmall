package com.ysarch.vmall.domain.bean;

public class GoodsPictureBean {
    private String picUrl;
    private boolean select;

    public GoodsPictureBean(String picUrl, boolean select) {
        this.picUrl = picUrl;
        this.select = select;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
