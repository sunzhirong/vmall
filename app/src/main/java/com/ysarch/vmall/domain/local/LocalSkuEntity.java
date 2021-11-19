package com.ysarch.vmall.domain.local;

import java.io.Serializable;

/**
 * Created by fysong on 24/09/2020
 **/
public class LocalSkuEntity implements Serializable {
    private String skuCode;
    private String skuLabel;
    private String skuBelong;
    private String skuBelongId;
    private boolean selected;
    private String image;
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSkuBelongId() {
        return skuBelongId;
    }

    public void setSkuBelongId(String skuBelongId) {
        this.skuBelongId = skuBelongId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSkuBelong() {
        return skuBelong;
    }

    public void setSkuBelong(String skuBelong) {
        this.skuBelong = skuBelong;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuLabel() {
        return skuLabel;
    }

    public void setSkuLabel(String skuLabel) {
        this.skuLabel = skuLabel;
    }

    public LocalSkuEntity clone() {
        LocalSkuEntity entity = new LocalSkuEntity();
        entity.skuBelong = this.skuBelong;
        entity.skuLabel = this.skuLabel;
        entity.skuCode = this.skuCode;
        entity.skuBelongId = this.skuBelongId;
        entity.selected = this.selected;
//        entity.countAdded = this.countAdded;
//        entity.quantity = this.quantity;
        return entity;
    }
}
