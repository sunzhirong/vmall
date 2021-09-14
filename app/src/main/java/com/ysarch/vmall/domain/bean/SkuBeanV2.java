package com.ysarch.vmall.domain.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by fysong on 08/10/2020
 **/
public class SkuBeanV2 implements Serializable {
    /**
     * quantity : 159
     * price : 188
     * propPath :
     * skuId : 0
     */

    private String quantity;
    private String price;
    private String propPath;
    private String skuId;


    public String getQuantity() {
        return quantity;
    }

    public int getQuantityInt() {
        if(TextUtils.isEmpty(quantity))
            return 0;
        return Integer.parseInt(quantity);
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPropPath() {
        return propPath;
    }

    public void setPropPath(String propPath) {
        this.propPath = propPath;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public double getFloatPrice() {
        if(TextUtils.isEmpty(price)){
          return 0;
        }
        return Double.valueOf(price);
    }

    public SkuBeanV2 clone(){
        SkuBeanV2 skuBeanV2 = new SkuBeanV2();
        skuBeanV2.price = this.price;
        skuBeanV2.propPath = this.propPath;
        skuBeanV2.quantity = this.quantity;
        skuBeanV2.skuId = this.skuId;
        return skuBeanV2;
    }

}
