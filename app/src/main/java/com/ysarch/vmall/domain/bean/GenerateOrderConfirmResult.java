package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 27/09/2020
 **/
public class GenerateOrderConfirmResult implements Serializable {

    /**
     * cartPromotionItemList : [{"id":181,"productId":623868822199,"productSkuId":4410100092420,"memberId":12,"quantity":2,"price":187,"productPic":"http%3A%2F%2Fimg.alicdn.com%2Fimgextra%2Fi1%2F665298756%2FO1CN01vpKV042EYIZXrCDso_%21%21665298756.jpg","productName":"%E3%80%90Q%E5%BC%B9%E7%A1%85%E8%83%B6%E9%A4%90%E6%A1%8C%E5%9E%AB%E3%80%91%E5%8C%97%E6%AC%A7%E5%A4%A7%E7%90%86%E7%9F%B3%E9%B9%BF%20%E6%A1%8C%E5%B8%83%E9%98%B2%E6%B0%B4%E9%98%B2%E6%B2%B9%E5%85%8D%E6%B4%97%E9%98%B2%E7%83%AB%E5%AE%B6%E7%94%A8%E8%8C%B6%E5%87%A0%E5%9E%AB","createDate":"2020-09-27T03:48:09.000+0000","deleteStatus":0,"productCategoryId":122852001,"productBrand":"%E6%A2%93%E6%99%A8","productAttr":"%E5%B7%B2%E9%80%89%3A%20%E4%B9%90%E9%B1%BC-21-%E7%99%BD%2F85*140cm","source":0,"promotionMessage":"无优惠","reduceAmount":0,"realStock":996,"integration":0,"growth":0}]
     * couponHistoryDetailList : []
     * calcAmount : {"totalAmount":374,"freightAmount":0,"promotionAmount":0,"payAmount":374}
     */

    private CalcAmountBean calcAmount;
    private List<CartPromotionItemListBean> cartPromotionItemList;
    private List<ConfirmOrderAvailableCouponBean> couponHistoryDetailList;

    @SerializedName("umsMemberReceiveAddress")
    private AddressItemBean mAddressItemBean;
    private WmsWarehouseInfoBean wmsWarehouseInfo;

    public WmsWarehouseInfoBean getWmsWarehouseInfo() {
        return wmsWarehouseInfo;
    }

    public void setWmsWarehouseInfo(WmsWarehouseInfoBean wmsWarehouseInfo) {
        this.wmsWarehouseInfo = wmsWarehouseInfo;
    }

    private int shippingMethod;

    public int getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(int shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public AddressItemBean getAddressItemBean() {
        return mAddressItemBean;
    }

    public void setAddressItemBean(AddressItemBean addressItemBean) {
        mAddressItemBean = addressItemBean;
    }

    public CalcAmountBean getCalcAmount() {
        return calcAmount;
    }

    public void setCalcAmount(CalcAmountBean calcAmount) {
        this.calcAmount = calcAmount;
    }

    public List<CartPromotionItemListBean> getCartPromotionItemList() {
        return cartPromotionItemList;
    }

    public void setCartPromotionItemList(List<CartPromotionItemListBean> cartPromotionItemList) {
        this.cartPromotionItemList = cartPromotionItemList;
    }

    public List<ConfirmOrderAvailableCouponBean> getCouponHistoryDetailList() {
        return couponHistoryDetailList;
    }

    public void setCouponHistoryDetailList(List<ConfirmOrderAvailableCouponBean> couponHistoryDetailList) {
        this.couponHistoryDetailList = couponHistoryDetailList;
    }

    public static class CalcAmountBean implements Serializable {
        /**
         * totalAmount : 374
         * freightAmount : 0
         * promotionAmount : 0
         * payAmount : 374
         */

        private double totalAmount;
        private double freightAmount;
        private double promotionAmount;
        private double payAmount;
        private double serviceFeeAmount;

        public double getServiceFeeAmount() {
            return serviceFeeAmount;
        }

        public void setServiceFeeAmount(double serviceFeeAmount) {
            this.serviceFeeAmount = serviceFeeAmount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getFreightAmount() {
            return freightAmount;
        }

        public void setFreightAmount(double freightAmount) {
            this.freightAmount = freightAmount;
        }

        public double getPromotionAmount() {
            return promotionAmount;
        }

        public void setPromotionAmount(double promotionAmount) {
            this.promotionAmount = promotionAmount;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }
    }



    public static class CartPromotionItemListBean implements Serializable {
        /**
         * id : 181
         * productId : 623868822199
         * productSkuId : 4410100092420
         * memberId : 12
         * quantity : 2
         * price : 187
         * productPic : http%3A%2F%2Fimg.alicdn.com%2Fimgextra%2Fi1%2F665298756%2FO1CN01vpKV042EYIZXrCDso_%21%21665298756.jpg
         * productName : %E3%80%90Q%E5%BC%B9%E7%A1%85%E8%83%B6%E9%A4%90%E6%A1%8C%E5%9E%AB%E3%80%91%E5%8C%97%E6%AC%A7%E5%A4%A7%E7%90%86%E7%9F%B3%E9%B9%BF%20%E6%A1%8C%E5%B8%83%E9%98%B2%E6%B0%B4%E9%98%B2%E6%B2%B9%E5%85%8D%E6%B4%97%E9%98%B2%E7%83%AB%E5%AE%B6%E7%94%A8%E8%8C%B6%E5%87%A0%E5%9E%AB
         * createDate : 2020-09-27T03:48:09.000+0000
         * deleteStatus : 0
         * productCategoryId : 122852001
         * productBrand : %E6%A2%93%E6%99%A8
         * productAttr : %E5%B7%B2%E9%80%89%3A%20%E4%B9%90%E9%B1%BC-21-%E7%99%BD%2F85*140cm
         * source : 0
         * promotionMessage : 无优惠
         * reduceAmount : 0
         * realStock : 996
         * integration : 0
         * growth : 0
         */

        private long id;
        private long productId;
        private long productSkuId;
        private int memberId;
        private int quantity;
        private double price;
        private String productPic;
        private String productName;
        private String createDate;
        private int deleteStatus;
        private int productCategoryId;
        private String productBrand;
        private String productAttr;
        private int source;
        private String promotionMessage;
        private long reduceAmount;
        private int realStock;
        private int integration;
        private int growth;


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public long getProductSkuId() {
            return productSkuId;
        }

        public void setProductSkuId(long productSkuId) {
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

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
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

        public String getProductAttr() {
            return productAttr;
        }

        public void setProductAttr(String productAttr) {
            this.productAttr = productAttr;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getPromotionMessage() {
            return promotionMessage;
        }

        public void setPromotionMessage(String promotionMessage) {
            this.promotionMessage = promotionMessage;
        }

        public long getReduceAmount() {
            return reduceAmount;
        }

        public void setReduceAmount(long reduceAmount) {
            this.reduceAmount = reduceAmount;
        }

        public int getRealStock() {
            return realStock;
        }

        public void setRealStock(int realStock) {
            this.realStock = realStock;
        }

        public int getIntegration() {
            return integration;
        }

        public void setIntegration(int integration) {
            this.integration = integration;
        }

        public int getGrowth() {
            return growth;
        }

        public void setGrowth(int growth) {
            this.growth = growth;
        }
    }


}
