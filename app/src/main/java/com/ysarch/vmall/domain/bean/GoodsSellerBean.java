package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 09/10/2020
 **/
public class GoodsSellerBean implements Serializable {
    /**
     * sellerNick : vfu运动旗舰店
     * goodRatePercentage : 100.00%
     * shopName : VFU运动旗舰店
     * shopIcon : //img.alicdn.com/imgextra//b5/d5/TB1VIomQFXXXXa8XpXXSutbFXXX.jpg
     * shopUrl : tmall://page.tm/shop?item_id=624887497564&shopId=471311106
     * userId : 3201957018
     * fans : 79.3万
     * itemCount : 212
     * taoShopUrl : https://shop471311106.taobao.com/
     * creditLevel : 15
     * sellerId : 3201957018
     * creditLevelIcon : //gw.alicdn.com/tfs/TB1QoJUIwmTBuNjy1XbXXaMrVXa-84-36.png?getAvatar=avatar
     * shopId : 471311106
     * starts : 2017-03-14 16:54:34
     * evaluates : [{"score":"4.9 ","level":"1","levelText":"高","title":"宝贝描述","type":"desc"},{"score":"4.9 ","level":"1","levelText":"高","title":"卖家服务","type":"serv"},{"score":"4.9 ","level":"1","levelText":"高","title":"物流服务","type":"post"}]
     */

    private String sellerNick;
    private String goodRatePercentage;
    private String shopName;
    private String shopIcon;
    private String shopUrl;
    private long userId;
    private String fans;
    private String itemCount;
    private String taoShopUrl;
    private String creditLevel;
    private long sellerId;
    private String creditLevelIcon;
    private long shopId;
    private String starts;
    private List<EvaluatesBean> evaluates;

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getGoodRatePercentage() {
        return goodRatePercentage;
    }

    public void setGoodRatePercentage(String goodRatePercentage) {
        this.goodRatePercentage = goodRatePercentage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getTaoShopUrl() {
        return taoShopUrl;
    }

    public void setTaoShopUrl(String taoShopUrl) {
        this.taoShopUrl = taoShopUrl;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getCreditLevelIcon() {
        return creditLevelIcon;
    }

    public void setCreditLevelIcon(String creditLevelIcon) {
        this.creditLevelIcon = creditLevelIcon;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public List<EvaluatesBean> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<EvaluatesBean> evaluates) {
        this.evaluates = evaluates;
    }

    public static class EvaluatesBean implements Serializable {
        /**
         * score : 4.9
         * level : 1
         * levelText : 高
         * title : 宝贝描述
         * type : desc
         */

        private String score;
        private String level;
        private String levelText;
        private String title;
        private String type;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevelText() {
            return levelText;
        }

        public void setLevelText(String levelText) {
            this.levelText = levelText;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
