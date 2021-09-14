package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fysong on 30/09/2020
 **/
public class OrderItemListBean implements Serializable {
    /**
     * couponAmount : 0
     * giftGrowth : 0
     * giftIntegration : 0
     * id : 0
     * integrationAmount : 0
     * orderId : 0
     * orderSn : string
     * productAttr : string
     * productBrand : string
     * productCategoryId : 0
     * productId : 0
     * productName : string
     * productPic : string
     * productPrice : 0
     * productQuantity : 0
     * productSkuCode : string
     * productSkuId : 0
     * productSn : string
     * promotionAmount : 0
     * promotionName : string
     * realAmount : 0
     */

    private double couponAmount;
    private int giftGrowth;
    private int giftIntegration;
    private int id;
    private double integrationAmount;
    private long orderId;
    private String orderSn;
    private String productAttr;
    private String productBrand;
    private int productCategoryId;
    private long productId;
    private String productName;
    private String productPic;
    @SerializedName(value = "productPrice", alternate = {"price"})
    private double productPrice;
    private int productQuantity;
    private String productSkuCode;
    private String productSkuId;
    private String productSn;
    private double promotionAmount;
    private String promotionName;
    private double realAmount;
    private int source;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getGiftGrowth() {
        return giftGrowth;
    }

    public void setGiftGrowth(int giftGrowth) {
        this.giftGrowth = giftGrowth;
    }

    public int getGiftIntegration() {
        return giftIntegration;
    }

    public void setGiftIntegration(int giftIntegration) {
        this.giftIntegration = giftIntegration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(double integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(double promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(double realAmount) {
        this.realAmount = realAmount;
    }
}
