package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;
import com.ysarch.vmall.utils.StringUtil;

import java.io.Serializable;

/**
 * 商品单元数据
 * Created by fysong on 18/09/2020
 **/
public class GoodsItemBean implements Serializable {


  /*  {
        "id": 7,
            "parentId": 1,
            "name": "外套",
            "level": 1,
            "productCount": 100,
            "productUnit": "件",
            "navStatus": 1,
            "showStatus": 1,
            "sort": 0,
            "icon": "",
            "keywords": "外套"
    }*/
    /**
     * title : qq币免邮腾讯返现防诈骗不刷单不官方包邮
     * pic_url : //g-search3.alicdn.com/img/bao/uploaded/i4/imgextra/i1/T1aiVpXoBHXXb1upjX.jpg
     * promotion_price : 0.99
     * price : 0.99
     * sales : 4755
     * num_iid : 528698606951
     * sample_id :
     * seller_nick : 博宽网游数卡专营店
     * post_fee :
     * area : 广东 深圳
     * detail_url : http://item.taobao.com/item.htm?id=528698606951
     */

    private String title;
    @SerializedName("pic_url")
    private String picUrl;
    @SerializedName("promotion_price")
    private String promotionPrice;
    private String price;
    private int sales;
    @SerializedName("num_iid")
    private long id;
    @SerializedName("sample_id")
    private String sampleId;
    @SerializedName("seller_nick")
    private String sellerNick;
    @SerializedName("post_fee")
    private String postFee;
    private String area;
    @SerializedName("detail_url")
    private String detailUrl;

    private String dollarMarketPrice;

    public String getDollarMarketPrice() {
        return dollarMarketPrice;
    }

    public void setDollarMarketPrice(String dollarMarketPrice) {
        this.dollarMarketPrice = dollarMarketPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl = StringUtil.correctUrl(picUrl);
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailUrl() {
        return detailUrl = StringUtil.correctUrl(detailUrl);
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
