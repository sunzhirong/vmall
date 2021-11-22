package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 30/09/2020
 **/
public class GenerateOrderResult implements Serializable {

    private int code;
    private String message;
    private GenerateOrderResultData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GenerateOrderResultData getData() {
        return data;
    }

    public void setData(GenerateOrderResultData data) {
        this.data = data;
    }

    public static class GenerateOrderResultData {
        private OrderBean order;
        private List<OrderItemListBean> orderItemList;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<OrderItemListBean> getOrderItemList() {
            return orderItemList;
        }

        public void setOrderItemList(List<OrderItemListBean> orderItemList) {
            this.orderItemList = orderItemList;
        }
    }

    /**
     * orderItemList : [{"orderId":73,"orderSn":"202009300101000001","productId":621372769072,"productPic":"http://img.alicdn.com/imgextra/i4/2208145437550/O1CN01RlQ6WY25dwu3LGrGr_!!2208145437550.jpg","productName":"夏季透气网反光志愿者马甲公益定制公益广告马甲工作服多口袋刺绣","productBrand":"倾锋","productPrice":15,"productQuantity":1,"productSkuId":4561906502949,"productCategoryId":30,"promotionName":"无优惠","promotionAmount":0,"couponAmount":0,"integrationAmount":0,"realAmount":15,"giftIntegration":0,"giftGrowth":0,"productAttr":"已选: M/无口袋---红色"}]
     * order : {"id":73,"memberId":12,"orderSn":"202009300101000001","createTime":"2020-09-29T18:25:39.100+0000","memberUsername":"沙世","totalAmount":15,"payAmount":15,"freightAmount":0,"promotionAmount":0,"integrationAmount":0,"couponAmount":0,"discountAmount":0,"payType":1,"sourceType":1,"status":0,"orderType":0,"autoConfirmDay":15,"integration":0,"growth":0,"promotionInfo":"无优惠","receiverName":"沙世嘉麟杰","receiverPhone":"13535075370","receiverPostCode":"","receiverProvince":"江苏省","receiverCity":"南京市","receiverRegion":"江宁区","receiverDetailAddress":"隔空取物呀基金经理","confirmStatus":0,"deleteStatus":0}
     */


    public static class OrderBean implements Serializable {
        /**
         * id : 73
         * memberId : 12
         * orderSn : 202009300101000001
         * createTime : 2020-09-29T18:25:39.100+0000
         * memberUsername : 沙世
         * totalAmount : 15
         * payAmount : 15
         * freightAmount : 0
         * promotionAmount : 0
         * integrationAmount : 0
         * couponAmount : 0
         * discountAmount : 0
         * payType : 1
         * sourceType : 1
         * status : 0
         * orderType : 0
         * autoConfirmDay : 15
         * integration : 0
         * growth : 0
         * promotionInfo : 无优惠
         * receiverName : 沙世嘉麟杰
         * receiverPhone : 13535075370
         * receiverPostCode :
         * receiverProvince : 江苏省
         * receiverCity : 南京市
         * receiverRegion : 江宁区
         * receiverDetailAddress : 隔空取物呀基金经理
         * confirmStatus : 0
         * deleteStatus : 0
         */

        private long id;
        private long memberId;
        private String orderSn;
        private String createTime;
        private String memberUsername;
        private double totalAmount;
        private double payAmount;
        private double freightAmount;
        private double promotionAmount;
        private double integrationAmount;
        private double couponAmount;
        private double discountAmount;
        private int payType;
        private int sourceType;
        private int status;
        private int orderType;
        private int autoConfirmDay;
        private int integration;
        private int growth;
        private String promotionInfo;
        private String receiverName;
        private String receiverPhone;
        private String receiverPostCode;
        private String receiverProvince;
        private String receiverCity;
        private String receiverRegion;
        private String receiverDetailAddress;
        private int confirmStatus;
        private int deleteStatus;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getMemberId() {
            return memberId;
        }

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMemberUsername() {
            return memberUsername;
        }

        public void setMemberUsername(String memberUsername) {
            this.memberUsername = memberUsername;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
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

        public double getIntegrationAmount() {
            return integrationAmount;
        }

        public void setIntegrationAmount(double integrationAmount) {
            this.integrationAmount = integrationAmount;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getAutoConfirmDay() {
            return autoConfirmDay;
        }

        public void setAutoConfirmDay(int autoConfirmDay) {
            this.autoConfirmDay = autoConfirmDay;
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

        public String getPromotionInfo() {
            return promotionInfo;
        }

        public void setPromotionInfo(String promotionInfo) {
            this.promotionInfo = promotionInfo;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getReceiverPostCode() {
            return receiverPostCode;
        }

        public void setReceiverPostCode(String receiverPostCode) {
            this.receiverPostCode = receiverPostCode;
        }

        public String getReceiverProvince() {
            return receiverProvince;
        }

        public void setReceiverProvince(String receiverProvince) {
            this.receiverProvince = receiverProvince;
        }

        public String getReceiverCity() {
            return receiverCity;
        }

        public void setReceiverCity(String receiverCity) {
            this.receiverCity = receiverCity;
        }

        public String getReceiverRegion() {
            return receiverRegion;
        }

        public void setReceiverRegion(String receiverRegion) {
            this.receiverRegion = receiverRegion;
        }

        public String getReceiverDetailAddress() {
            return receiverDetailAddress;
        }

        public void setReceiverDetailAddress(String receiverDetailAddress) {
            this.receiverDetailAddress = receiverDetailAddress;
        }

        public int getConfirmStatus() {
            return confirmStatus;
        }

        public void setConfirmStatus(int confirmStatus) {
            this.confirmStatus = confirmStatus;
        }

        public int getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(int deleteStatus) {
            this.deleteStatus = deleteStatus;
        }
    }

}
