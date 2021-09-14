package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 28/09/2020
 **/
public class OrderBeanv1 implements Serializable {

    /**
     * autoConfirmDay : 0
     * billContent : string
     * billHeader : string
     * billReceiverEmail : string
     * billReceiverPhone : string
     * billType : 0
     * commentTime : 2020-09-28T02:32:21.600Z
     * confirmStatus : 0
     * couponAmount : 0
     * couponId : 0
     * createTime : 2020-09-28T02:32:21.600Z
     * deleteStatus : 0
     * deliveryCompany : string
     * deliverySn : string
     * deliveryTime : 2020-09-28T02:32:21.600Z
     * discountAmount : 0
     * freightAmount : 0
     * growth : 0
     * id : 0
     * integration : 0
     * integrationAmount : 0
     * memberId : 0
     * memberUsername : string
     * modifyTime : 2020-09-28T02:32:21.600Z
     * note : string
     * orderItemList : [{"couponAmount":0,"giftGrowth":0,"giftIntegration":0,"id":0,"integrationAmount":0,"orderId":0,"orderSn":"string","productAttr":"string","productBrand":"string","productCategoryId":0,"productId":0,"productName":"string","productPic":"string","productPrice":0,"productQuantity":0,"productSkuCode":"string","productSkuId":0,"productSn":"string","promotionAmount":0,"promotionName":"string","realAmount":0}]
     * orderSn : string
     * orderType : 0
     * payAmount : 0
     * payType : 0
     * paymentTime : 2020-09-28T02:32:21.600Z
     * promotionAmount : 0
     * promotionInfo : string
     * receiveTime : 2020-09-28T02:32:21.600Z
     * receiverCity : string
     * receiverDetailAddress : string
     * receiverName : string
     * receiverPhone : string
     * receiverPostCode : string
     * receiverProvince : string
     * receiverRegion : string
     * sourceType : 0
     * status : 0
     * totalAmount : 0
     * useIntegration : 0
     */

    private int autoConfirmDay;
    private String billContent;
    private String billHeader;
    private String billReceiverEmail;
    private String billReceiverPhone;
    private int billType;
    private String commentTime;
    private int confirmStatus;
    private float couponAmount;
    private int couponId;
    private String createTime;
    private int deleteStatus;
    private String deliveryCompany;
    private String deliverySn;
    private String deliveryTime;
    private float discountAmount;
    private float freightAmount;
    private int growth;
    private long id;
    private int integration;
    private float integrationAmount;
    private int memberId;
    private String memberUsername;
    private String modifyTime;
    private String note;
    private String orderSn;
    private int orderType;
    private float payAmount;
    private int payType;
    private String paymentTime;
    private float promotionAmount;
    private String promotionInfo;
    private String receiveTime;
    private String receiverCity;
    private String receiverDetailAddress;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverProvince;
    private String receiverRegion;
    private int sourceType;
    private int status;
    private float totalAmount;
    private int useIntegration;
    private List<OrderItemListBean> orderItemList;

    public int getAutoConfirmDay() {
        return autoConfirmDay;
    }

    public void setAutoConfirmDay(int autoConfirmDay) {
        this.autoConfirmDay = autoConfirmDay;
    }

    public String getBillContent() {
        return billContent;
    }

    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getBillReceiverEmail() {
        return billReceiverEmail;
    }

    public void setBillReceiverEmail(String billReceiverEmail) {
        this.billReceiverEmail = billReceiverEmail;
    }

    public String getBillReceiverPhone() {
        return billReceiverPhone;
    }

    public void setBillReceiverPhone(String billReceiverPhone) {
        this.billReceiverPhone = billReceiverPhone;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public float getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(float couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public float getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(float freightAmount) {
        this.freightAmount = freightAmount;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public float getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(float integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public float getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(float payAmount) {
        this.payAmount = payAmount;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public float getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(float promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
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

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
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

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(int useIntegration) {
        this.useIntegration = useIntegration;
    }

    public List<OrderItemListBean> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemListBean> orderItemList) {
        this.orderItemList = orderItemList;
    }

//    public static class OrderItemListBean  implements Serializable{
//        /**
//         * couponAmount : 0
//         * giftGrowth : 0
//         * giftIntegration : 0
//         * id : 0
//         * integrationAmount : 0
//         * orderId : 0
//         * orderSn : string
//         * productAttr : string
//         * productBrand : string
//         * productCategoryId : 0
//         * productId : 0
//         * productName : string
//         * productPic : string
//         * productPrice : 0
//         * productQuantity : 0
//         * productSkuCode : string
//         * productSkuId : 0
//         * productSn : string
//         * promotionAmount : 0
//         * promotionName : string
//         * realAmount : 0
//         */
//
//        private float couponAmount;
//        private int giftGrowth;
//        private int giftIntegration;
//        private long id;
//        private float integrationAmount;
//        private long orderId;
//        private String orderSn;
//        private String productAttr;
//        private String productBrand;
//        private long productCategoryId;
//        private long productId;
//        private String productName;
//        private String productPic;
//        private float productPrice;
//        private int productQuantity;
//        private String productSkuCode;
//        private long productSkuId;
//        private String productSn;
//        private float promotionAmount;
//        private String promotionName;
//        private float realAmount;
//
//        public float getCouponAmount() {
//            return couponAmount;
//        }
//
//        public void setCouponAmount(float couponAmount) {
//            this.couponAmount = couponAmount;
//        }
//
//        public int getGiftGrowth() {
//            return giftGrowth;
//        }
//
//        public void setGiftGrowth(int giftGrowth) {
//            this.giftGrowth = giftGrowth;
//        }
//
//        public int getGiftIntegration() {
//            return giftIntegration;
//        }
//
//        public void setGiftIntegration(int giftIntegration) {
//            this.giftIntegration = giftIntegration;
//        }
//
//        public long getId() {
//            return id;
//        }
//
//        public void setId(long id) {
//            this.id = id;
//        }
//
//        public float getIntegrationAmount() {
//            return integrationAmount;
//        }
//
//        public void setIntegrationAmount(float integrationAmount) {
//            this.integrationAmount = integrationAmount;
//        }
//
//        public long getOrderId() {
//            return orderId;
//        }
//
//        public void setOrderId(long orderId) {
//            this.orderId = orderId;
//        }
//
//        public String getOrderSn() {
//            return orderSn;
//        }
//
//        public void setOrderSn(String orderSn) {
//            this.orderSn = orderSn;
//        }
//
//        public String getProductAttr() {
//            return productAttr;
//        }
//
//        public void setProductAttr(String productAttr) {
//            this.productAttr = productAttr;
//        }
//
//        public String getProductBrand() {
//            return productBrand;
//        }
//
//        public void setProductBrand(String productBrand) {
//            this.productBrand = productBrand;
//        }
//
//        public long getProductCategoryId() {
//            return productCategoryId;
//        }
//
//        public void setProductCategoryId(long productCategoryId) {
//            this.productCategoryId = productCategoryId;
//        }
//
//        public long getProductId() {
//            return productId;
//        }
//
//        public void setProductId(long productId) {
//            this.productId = productId;
//        }
//
//        public String getProductName() {
//            return productName;
//        }
//
//        public void setProductName(String productName) {
//            this.productName = productName;
//        }
//
//        public String getProductPic() {
//            return productPic;
//        }
//
//        public void setProductPic(String productPic) {
//            this.productPic = productPic;
//        }
//
//        public float getProductPrice() {
//            return productPrice;
//        }
//
//        public void setProductPrice(float productPrice) {
//            this.productPrice = productPrice;
//        }
//
//        public int getProductQuantity() {
//            return productQuantity;
//        }
//
//        public void setProductQuantity(int productQuantity) {
//            this.productQuantity = productQuantity;
//        }
//
//        public String getProductSkuCode() {
//            return productSkuCode;
//        }
//
//        public void setProductSkuCode(String productSkuCode) {
//            this.productSkuCode = productSkuCode;
//        }
//
//        public long getProductSkuId() {
//            return productSkuId;
//        }
//
//        public void setProductSkuId(long productSkuId) {
//            this.productSkuId = productSkuId;
//        }
//
//        public String getProductSn() {
//            return productSn;
//        }
//
//        public void setProductSn(String productSn) {
//            this.productSn = productSn;
//        }
//
//        public float getPromotionAmount() {
//            return promotionAmount;
//        }
//
//        public void setPromotionAmount(float promotionAmount) {
//            this.promotionAmount = promotionAmount;
//        }
//
//        public String getPromotionName() {
//            return promotionName;
//        }
//
//        public void setPromotionName(String promotionName) {
//            this.promotionName = promotionName;
//        }
//
//        public float getRealAmount() {
//            return realAmount;
//        }
//
//        public void setRealAmount(float realAmount) {
//            this.realAmount = realAmount;
//        }
//    }
}
