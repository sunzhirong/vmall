package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by fysong on 25/09/2020
 **/
public class CartGoodsBean implements Serializable {

    public static final int TYPE_ONE = -1;//只有一个
    public static final int TYPE_TOP = 0;//头部
    public static final int TYPE_MIDDLE = 1;//中部
    public static final int TYPE_BOTTOM = 2;//尾部

    /**
     * id : 135
     * productId : 623868822199
     * productSkuId : 4410100092125
     * memberId : 12
     * quantity : 1
     * price : 146
     * productName : %E3%80%90Q%E5%BC%B9%E7%A1%85%E8%83%B6%E9%A4%90%E6%A1%8C%E5%9E%AB%E3%80%91%E5%8C%97%E6%AC%A7%E5%A4%A7%E7%90%86%E7%9F%B3%E9%B9%BF%20%E6%A1%8C%E5%B8%83%E9%98%B2%E6%B0%B4%E9%98%B2%E6%B2%B9%E5%85%8D%E6%B4%97%E9%98%B2%E7%83%AB%E5%AE%B6%E7%94%A8%E8%8C%B6%E5%87%A0%E5%9E%AB
     * createDate : 2020-09-24T17:04:06.000+0000
     * deleteStatus : 0
     * productCategoryId : 122852001
     * productBrand : %E6%A2%93%E6%99%A8
     * source : 0
     */




//            "memberNickname": "string",
//            "modifyDate": "2021-10-12T01:56:04.770Z",
//            "productSkuCode": "string",
//            "productSn": "string",
//            "productSubTitle": "string",
//            "sellerId": 0,

    private String memberNickname;
    private String modifyDate;
    private String productSkuCode;
    private String productSn;
    private String productSubTitle;
    private String sellerId;

    private int type = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    private int id;
    private long productId;
    private String productSkuId;
    private int memberId;
    private int quantity;
    private double price;
    private String productName;
    private String createDate;
    private int deleteStatus;
    private int productCategoryId;
    private String productBrand;
    private int source;
    private String productAttr;
    private String productPic;


    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartGoodsBean that = (CartGoodsBean) o;
        return this.id == that.id;
    }

}