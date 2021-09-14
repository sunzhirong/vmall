package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by fysong on 25/09/2020
 **/
public class GoodsDetailBean implements Serializable {

    @SerializedName("num_iid")
    private String id;
    private String title;
    @SerializedName("desc_short")
    private String descShort;
    private double price;
    @SerializedName("total_price")
    private double totalPrice;
    @SerializedName("suggestive_price")
    private double suggestivePrice;
    @SerializedName("orginal_price")
    private double orginalPrice;
    private String nick;
    private String num;
    @SerializedName("min_num")
    private int minNum;
    @SerializedName("detail_url")
    private String detailUrl;
    @SerializedName("pic_url")
    private String picUrl;
    private String brand;
    private String brandId;
    private String rootCatId;
    private String cid;
    private String desc;
    @SerializedName("item_weight")
    private int itemWeight;
    private String location;
    @SerializedName("post_fee")
    private String postFee;
    @SerializedName("express_fee")
    private int expressFee;
    @SerializedName("ems_fee")
    private String emsFee;
    @SerializedName("shipping_to")
    private String shippingTo;
    @SerializedName("has_discount")
    private String hasDiscount;
    @SerializedName("is_promotion")
    private String isPromotion;
    @SerializedName("props_name")
    private String propsName;
    @SerializedName("prop_imgs")
    private PropImgsBean propImgs;
    @SerializedName("property_alias")
    private String propertyAlias;
    @SerializedName("total_sold")
    private String totalSold;
    private SkusBean skus;
    @SerializedName("seller_id")
    private String sellerId;
    private String sales;
    @SerializedName("shop_id")
    private String shopId;
    @SerializedName("seller_info")
    private SellerInfoBean sellerInfo;
    private String tmall;
    private String error;
    private String warning;
    private String favcount;
    private String fanscount;
    @SerializedName("stuff_status")
    private String stuffStatus;
    private ShopinfoBean shopinfo;
    @SerializedName("data_from")
    private String dataFrom;
    private String method;
    @SerializedName("rate_grade")
    private String rateGrade;
    @SerializedName("desc_img")
    private List<String> descImg;
    @SerializedName("item_imgs")
    private List<ItemImgsBean> itemImgs;
    private List<?> video;
    private List<PropsBean> props;
    @SerializedName("props_list")
    private List<Map<String, String>> propsList;
    @SerializedName("url_log")
    private List<String> urlLog;
    @SerializedName("props_img")
    private List<Map<String, String>> propsImg;
    @SerializedName("shop_item")
    private List<Object> shopItem;
    @SerializedName("relate_items")
    private List<Object> relateItems;

    public List<Map<String, String>> getPropsList() {
        return propsList;
    }

    public void setPropsList(List<Map<String, String>> propsList) {
        this.propsList = propsList;
    }

    public List<Map<String, String>> getPropsImg() {
        return propsImg;
    }

    public void setPropsImg(List<Map<String, String>> propsImg) {
        this.propsImg = propsImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescShort() {
        return descShort;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getSuggestivePrice() {
        return suggestivePrice;
    }

    public void setSuggestivePrice(double suggestivePrice) {
        this.suggestivePrice = suggestivePrice;
    }

    public double getOrginalPrice() {
        return orginalPrice;
    }

    public void setOrginalPrice(double orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(String rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(int itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public int getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(int expressFee) {
        this.expressFee = expressFee;
    }

    public String getEmsFee() {
        return emsFee;
    }

    public void setEmsFee(String emsFee) {
        this.emsFee = emsFee;
    }

    public String getShippingTo() {
        return shippingTo;
    }

    public void setShippingTo(String shippingTo) {
        this.shippingTo = shippingTo;
    }

    public String getHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(String hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public String getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(String isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getPropsName() {
        return propsName;
    }

    public void setPropsName(String propsName) {
        this.propsName = propsName;
    }

    public PropImgsBean getPropImgs() {
        return propImgs;
    }

    public void setPropImgs(PropImgsBean propImgs) {
        this.propImgs = propImgs;
    }

    public String getPropertyAlias() {
        return propertyAlias;
    }

    public void setPropertyAlias(String propertyAlias) {
        this.propertyAlias = propertyAlias;
    }

    public String getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(String totalSold) {
        this.totalSold = totalSold;
    }

    public SkusBean getSkus() {
        return skus;
    }

    public void setSkus(SkusBean skus) {
        this.skus = skus;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public SellerInfoBean getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(SellerInfoBean sellerInfo) {
        this.sellerInfo = sellerInfo;
    }

    public String getTmall() {
        return tmall;
    }

    public void setTmall(String tmall) {
        this.tmall = tmall;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getFavcount() {
        return favcount;
    }

    public void setFavcount(String favcount) {
        this.favcount = favcount;
    }

    public String getFanscount() {
        return fanscount;
    }

    public void setFanscount(String fanscount) {
        this.fanscount = fanscount;
    }

    public String getStuffStatus() {
        return stuffStatus;
    }

    public void setStuffStatus(String stuffStatus) {
        this.stuffStatus = stuffStatus;
    }

    public ShopinfoBean getShopinfo() {
        return shopinfo;
    }

    public void setShopinfo(ShopinfoBean shopinfo) {
        this.shopinfo = shopinfo;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRateGrade() {
        return rateGrade;
    }

    public void setRateGrade(String rateGrade) {
        this.rateGrade = rateGrade;
    }

    public List<String> getDescImg() {
        return descImg;
    }

    public void setDescImg(List<String> descImg) {
        this.descImg = descImg;
    }

    public List<ItemImgsBean> getItemImgs() {
        return itemImgs;
    }

    public void setItemImgs(List<ItemImgsBean> itemImgs) {
        this.itemImgs = itemImgs;
    }

    public List<?> getVideo() {
        return video;
    }

    public void setVideo(List<?> video) {
        this.video = video;
    }

    public List<PropsBean> getProps() {
        return props;
    }

    public void setProps(List<PropsBean> props) {
        this.props = props;
    }

    public List<String> getUrlLog() {
        return urlLog;
    }

    public void setUrlLog(List<String> urlLog) {
        this.urlLog = urlLog;
    }

    public List<Object> getShopItem() {
        return shopItem;
    }

    public void setShopItem(List<Object> shopItem) {
        this.shopItem = shopItem;
    }

    public List<Object> getRelateItems() {
        return relateItems;
    }

    public void setRelateItems(List<Object> relateItems) {
        this.relateItems = relateItems;
    }

    public static class PropImgsBean implements Serializable {
        @SerializedName("prop_img")
        private List<PropImgBean> propImg;

        public List<PropImgBean> getPropImg() {
            return propImg;
        }

        public void setPropImg(List<PropImgBean> propImg) {
            this.propImg = propImg;
        }

        public static class PropImgBean implements Serializable {
            /**
             * properties : 1627207:3232479
             * url : //img.alicdn.com/imgextra/i1/665298756/O1CN01rehsuE2EYIZhsYmRI_!!665298756.jpg
             */

            private String properties;
            private String url;

            public String getProperties() {
                return properties;
            }

            public void setProperties(String properties) {
                this.properties = properties;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class SkusBean implements Serializable {
        private List<SkuBean> sku;

        public List<SkuBean> getSku() {
            return sku;
        }

        public void setSku(List<SkuBean> sku) {
            this.sku = sku;
        }

    }

    public static class SellerInfoBean implements Serializable {
        /**
         * title : 梓晨旗舰店
         * shop_name : 梓晨旗舰店
         * sid : 64565965
         * zhuy : //shop64565965.taobao.com
         * level : 18
         * shop_type : B
         * user_num_id : 665298756
         * nick : 梓晨旗舰店
         * delivery_score : 4.9
         * item_score : 4.9
         * score_p : 4.8
         */

        private String title;
        @SerializedName("shop_name")
        private String shopName;
        private String sid;
        private String zhuy;
        private String level;
        @SerializedName("shop_type")
        private String shopType;
        @SerializedName("user_num_id")
        private String userNumId;
        private String nick;
        @SerializedName("delivery_score")
        private String deliveryScore;
        @SerializedName("item_score")
        private String itemScore;
        @SerializedName("score_p")
        private String scorePercent;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getZhuy() {
            return zhuy;
        }

        public void setZhuy(String zhuy) {
            this.zhuy = zhuy;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getShopType() {
            return shopType;
        }

        public void setShopType(String shopType) {
            this.shopType = shopType;
        }

        public String getUserNumId() {
            return userNumId;
        }

        public void setUserNumId(String userNumId) {
            this.userNumId = userNumId;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getDeliveryScore() {
            return deliveryScore;
        }

        public void setDeliveryScore(String deliveryScore) {
            this.deliveryScore = deliveryScore;
        }

        public String getItemScore() {
            return itemScore;
        }

        public void setItemScore(String itemScore) {
            this.itemScore = itemScore;
        }

        public String getScorePercent() {
            return scorePercent;
        }

        public void setScorePercent(String scorePercent) {
            this.scorePercent = scorePercent;
        }
    }

    public static class ShopinfoBean implements Serializable {
        /**
         * shop_name : 梓晨旗舰店
         * shop_id : 64565965
         */

        @SerializedName("shop_name")
        private String shopName;
        @SerializedName("shop_id")
        private String shopId;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }
    }

    public static class ItemImgsBean implements Serializable {
        /**
         * url : //img.alicdn.com/imgextra/i3/665298756/O1CN01NLXjUb2EYIZShxKGM_!!665298756-0-lubanu-s.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class PropsBean implements Serializable {
        /**
         * name : 品牌
         * value : 梓晨
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}