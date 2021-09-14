package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fysong on 24/09/2020
 **/
public class SkuBean implements Serializable {
    /**
     * price : 117
     * orginal_price : 234.00
     * properties : 1627207:3232479;5573946:6476739
     * properties_name : 1627207:3232479:颜色分类:理川-16-白;5573946:6476739:规格:60*120cm
     * quantity : 883
     * sku_id : 4410100092232
     */

    private double price;
    @SerializedName("orginal_price")
    private double orginalPrice;
    private String properties;
    @SerializedName("properties_name")
    private String propertiesName;
    private String quantity;
    @SerializedName("sku_id")
    private String skuId;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOrginalPrice() {
        return orginalPrice;
    }

    public void setOrginalPrice(double orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
