package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品单元数据v2
 * Created by fysong on 18/09/2020
 **/
public class GoodsItemBeanV2 implements Serializable {

    /**
     * marketPrice : 299.00
     * quantity :
     * baoyou : true
     * deliver :
     * shopName : 南极人南电专卖店
     * title : 南极人运动 湖北销量过百 专卖店南极人运动秋冬健身套装男排汗速干高弹训练紧身衣
     * shopUrl : http://shop.m.taobao.com/shop/shop_index.htm?user_id=3881186050
     * sales : 4389
     * url : https://detail.tmall.com/item.htm?id=570205030295
     * idAd : true
     * commentCount : 0
     * sellerId : 3881186050
     * price : 59.00
     * location : 广东 东莞
     * id : 570205030295
     * category :
     * sameCount : 0
     * imageUrls : ["http://g.search.alicdn.com/img/i4/131370547/O1CN01XUspwk1FuZGuH3W4Y_!!0-saturn_solar.jpg"]
     */

    private double marketPrice;
//    private int quantity;
    private boolean baoyou;
    private String deliver;
    private String shopName;
    private String title;
    private String shopUrl;
    private String sales;
    private String url;
    private boolean idAd;
    private int commentCount;
    private long sellerId;
    private float price;
    private String location;
    private long id;
    private String category;
    private int sameCount;
    private List<String> imageUrls;
    private String dollarPrice;

    private String dollarMarketPrice;

    private boolean onItemView;

    public boolean isOnItemView() {
        return onItemView;
    }

    public void setOnItemView(boolean onItemView) {
        this.onItemView = onItemView;
    }

    public String getDollarMarketPrice() {
        return dollarMarketPrice;
    }

    public void setDollarMarketPrice(String dollarMarketPrice) {
        this.dollarMarketPrice = dollarMarketPrice;
    }


    public String getDollarPrice() {
        return dollarPrice;
    }

    public void setDollarPrice(String dollarPrice) {
        this.dollarPrice = dollarPrice;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

   /* public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }*/

    public boolean isBaoyou() {
        return baoyou;
    }

    public void setBaoyou(boolean baoyou) {
        this.baoyou = baoyou;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIdAd() {
        return idAd;
    }

    public void setIdAd(boolean idAd) {
        this.idAd = idAd;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSameCount() {
        return sameCount;
    }

    public void setSameCount(int sameCount) {
        this.sameCount = sameCount;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
