package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 28/09/2020
 **/
public class OrderBean implements Serializable {
    /**
     * alreadyPaydAmount : 0
     * autoConfirmDay : 0
     * billContent : string
     * billHeader : string
     * billReceiverEmail : string
     * billReceiverPhone : string
     * billType : 0
     * cancelReason : 0
     * cnFreight : 0
     * commentTime : 2020-12-23T08:59:10.525Z
     * confirmStatus : 0
     * couponAmount : 0
     * couponHistoryId : 0
     * couponId : 0
     * createTime : 2020-12-23T08:59:10.525Z
     * currentTag : string
     * deleteStatus : 0
     * deliverlyType : 0
     * deliveryCompany : string
     * deliverySn : string
     * deliveryTime : 2020-12-23T08:59:10.525Z
     * discountAmount : 0
     * existFreight : true
     * freightAmount : 0
     * freightCouponAmount : 0
     * freightCouponHistoryId : 0
     * goodsNum : 0
     * growth : 0
     * id : 0
     * integration : 0
     * integrationAmount : 0
     * internationalDeliverySn : string
     * internationalPackageBillingWeight : 0
     * internationalPackageHeight : 0
     * internationalPackageLength : 0
     * internationalPackageNum : 0
     * internationalPackageVolume : 0
     * internationalPackageVolumeWeight : 0
     * internationalPackageWeight : 0
     * internationalPackageWidth : 0
     * lcTraceDTO : {"logisticproviderid":"string","responseitems":[{"billcode":"string","details":[{"city":"string","deliveryName":"string","deliveryTel":"string","desc":"string","latitude":"string","longitude":"string","nextSite":"string","scanscode":"string","scanstatus":"string","scantime":"string","scantype":"string","signer":"string","siteName":"string","siteType":"string"}],"mailno":"string","reason":"string","success":true,"txlogisticid":"string"}]}
     * localFreight : 0
     * localPackageNum : 0
     * logisticsStatus : 0
     * memberId : 0
     * memberUsername : string
     * modifyTime : 2020-12-23T08:59:10.525Z
     * needConfirmReceive : true
     * needPayFreigth : true
     * note : string
     * orderAmount : 0
     * orderItemList : [{"couponAmount":0,"giftGrowth":0,"giftIntegration":0,"goodsUrl":"string","id":0,"integrationAmount":0,"orderId":0,"orderSn":"string","outDeliverySn":"string","outOrderSn":"string","productAttr":"string","productBrand":"string","productCategoryId":0,"productId":0,"productName":"string","productPic":"string","productPrice":0,"productQuantity":0,"productSkuCode":"string","productSkuId":0,"productSn":"string","promotionAmount":0,"promotionName":"string","realAmount":0,"shopName":"string","shopUrl":"string","source":0}]
     * orderSn : string
     * orderType : 0
     * overseasFreight : 0
     * payAmount : 0
     * payFreightStatus : true
     * payFreightTime : 2020-12-23T08:59:10.526Z
     * payType : 0
     * paymentTime : 2020-12-23T08:59:10.526Z
     * predictFreightAmount : 0
     * promotionAmount : 0
     * promotionInfo : string
     * receiveTime : 2020-12-23T08:59:10.526Z
     * receiverCity : string
     * receiverDetailAddress : string
     * receiverName : string
     * receiverPhone : string
     * receiverPostCode : string
     * receiverProvince : string
     * receiverRegion : string
     * remark : string
     * restAmount : 0
     * restTime : 0
     * serviceAmount : 0
     * shippingMethod : 0
     * shippingType : 0
     * sourceType : 0
     * status : 0
     * totalAmount : 0
     * useIntegration : 0
     * warehouse : 0
     */

    private double alreadyPayAmount;
    private int autoConfirmDay;
    private String billContent;
    private String billHeader;
    private String billReceiverEmail;
    private String billReceiverPhone;
    private int billType;
    private int cancelReason;
    private double cnFreight;
    private String commentTime;
    private int confirmStatus;
    private double couponAmount;
    private int couponHistoryId;
    private int couponId;
    private String createTime;
    private String currentTag;
    private int deleteStatus;
    private int deliverlyType;
    private String deliveryCompany;
    private String deliverySn;
    private String deliveryTime;
    private double discountAmount;
    private boolean existFreight;
    private double freightAmount;
    private double freightCouponAmount;
    private double freightCouponHistoryId;
    private int goodsNum;
    private int growth;
    private int id;
    private int integration;
    private double integrationAmount;
    private String internationalDeliverySn;
    private double internationalPackageBillingWeight;
    private double internationalPackageHeight;
    private double internationalPackageLength;
    private int internationalPackageNum;
    private double internationalPackageVolume;
    private double internationalPackageVolumeWeight;
    private double internationalPackageWeight;
    private double internationalPackageWidth;
    private LcTraceDTOBean lcTraceDTO;
    private double localFreight;
    private int localPackageNum;
    private int logisticsStatus;
    private int memberId;
    private String memberUsername;
    private String modifyTime;
    private boolean needConfirmReceive;
    private boolean needPayFreigth;
    private String note;
    private double orderAmount;
    private String orderSn;
    private int orderType;
    private double overseasFreight;
    private double payAmount;
    private boolean payFreightStatus;
    private String payFreightTime;
    private int payType;
    private String paymentTime;
    private double predictFreightAmount;
    private double promotionAmount;
    private String promotionInfo;
    private String receiveTime;
    private String receiverCity;
    private String receiverDetailAddress;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverProvince;
    private String receiverRegion;
    private String remark;
    private double restAmount;
    private int restTime;
    private double serviceAmount;
    private int shippingMethod;
    private int shippingType;
    private int sourceType;
    private int status;
    private double totalAmount;
    private int useIntegration;
    private int warehouse;
    private List<OrderItemListBean> orderItemList;
    private List<SameSellerCartPromotionBean> sameSellerOrderItemList;
    private WmsWarehouseInfoBean wmsWarehouseInfo;

    public List<SameSellerCartPromotionBean> getSameSellerOrderItemList() {
        return sameSellerOrderItemList;
    }

    public void setSameSellerOrderItemList(List<SameSellerCartPromotionBean> sameSellerOrderItemList) {
        this.sameSellerOrderItemList = sameSellerOrderItemList;
    }

    public static class SameSellerCartPromotionBean implements Serializable {

        /**
         * amount : 0
         * delivery : 0
         * dollorDelivery : 0
         * sellerId : 0
         * source : 0
         */

        private double amount;
        private int productQuantity;
        private String delivery;
        private double dollorDelivery;
        private int source;
        private List<OrderItemListBean> omsOrderItems;
        private int type;

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<OrderItemListBean> getOmsOrderItems() {
            return omsOrderItems;
        }

        public void setOmsOrderItems(List<OrderItemListBean> omsOrderItems) {
            this.omsOrderItems = omsOrderItems;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getDelivery() {
            return delivery;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public double getDollorDelivery() {
            return dollorDelivery;
        }

        public void setDollorDelivery(double dollorDelivery) {
            this.dollorDelivery = dollorDelivery;
        }


        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }
    }


    private String pickUpCode;

    public String getPickUpCode() {
        return pickUpCode;
    }

    public void setPickUpCode(String pickUpCode) {
        this.pickUpCode = pickUpCode;
    }

    public WmsWarehouseInfoBean getWmsWarehouseInfo() {
        return wmsWarehouseInfo;
    }

    public void setWmsWarehouseInfo(WmsWarehouseInfoBean wmsWarehouseInfo) {
        this.wmsWarehouseInfo = wmsWarehouseInfo;
    }

    public double getAlreadyPayAmount() {
        return alreadyPayAmount;
    }

    public void setAlreadyPayAmount(double alreadyPayAmount) {
        this.alreadyPayAmount = alreadyPayAmount;
    }

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

    public int getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(int cancelReason) {
        this.cancelReason = cancelReason;
    }

    public double getCnFreight() {
        return cnFreight;
    }

    public void setCnFreight(double cnFreight) {
        this.cnFreight = cnFreight;
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

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public int getCouponHistoryId() {
        return couponHistoryId;
    }

    public void setCouponHistoryId(int couponHistoryId) {
        this.couponHistoryId = couponHistoryId;
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

    public String getCurrentTag() {
        return currentTag;
    }

    public void setCurrentTag(String currentTag) {
        this.currentTag = currentTag;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getDeliverlyType() {
        return deliverlyType;
    }

    public void setDeliverlyType(int deliverlyType) {
        this.deliverlyType = deliverlyType;
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

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public boolean isExistFreight() {
        return existFreight;
    }

    public void setExistFreight(boolean existFreight) {
        this.existFreight = existFreight;
    }

    public double getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(double freightAmount) {
        this.freightAmount = freightAmount;
    }

    public double getFreightCouponAmount() {
        return freightCouponAmount;
    }

    public void setFreightCouponAmount(double freightCouponAmount) {
        this.freightCouponAmount = freightCouponAmount;
    }

    public double getFreightCouponHistoryId() {
        return freightCouponHistoryId;
    }

    public void setFreightCouponHistoryId(double freightCouponHistoryId) {
        this.freightCouponHistoryId = freightCouponHistoryId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public double getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(double integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public String getInternationalDeliverySn() {
        return internationalDeliverySn;
    }

    public void setInternationalDeliverySn(String internationalDeliverySn) {
        this.internationalDeliverySn = internationalDeliverySn;
    }

    public double getInternationalPackageBillingWeight() {
        return internationalPackageBillingWeight;
    }

    public void setInternationalPackageBillingWeight(double internationalPackageBillingWeight) {
        this.internationalPackageBillingWeight = internationalPackageBillingWeight;
    }

    public double getInternationalPackageHeight() {
        return internationalPackageHeight;
    }

    public void setInternationalPackageHeight(double internationalPackageHeight) {
        this.internationalPackageHeight = internationalPackageHeight;
    }

    public double getInternationalPackageLength() {
        return internationalPackageLength;
    }

    public void setInternationalPackageLength(double internationalPackageLength) {
        this.internationalPackageLength = internationalPackageLength;
    }

    public int getInternationalPackageNum() {
        return internationalPackageNum;
    }

    public void setInternationalPackageNum(int internationalPackageNum) {
        this.internationalPackageNum = internationalPackageNum;
    }

    public double getInternationalPackageVolume() {
        return internationalPackageVolume;
    }

    public void setInternationalPackageVolume(double internationalPackageVolume) {
        this.internationalPackageVolume = internationalPackageVolume;
    }

    public double getInternationalPackageVolumeWeight() {
        return internationalPackageVolumeWeight;
    }

    public void setInternationalPackageVolumeWeight(double internationalPackageVolumeWeight) {
        this.internationalPackageVolumeWeight = internationalPackageVolumeWeight;
    }

    public double getInternationalPackageWeight() {
        return internationalPackageWeight;
    }

    public void setInternationalPackageWeight(double internationalPackageWeight) {
        this.internationalPackageWeight = internationalPackageWeight;
    }

    public double getInternationalPackageWidth() {
        return internationalPackageWidth;
    }

    public void setInternationalPackageWidth(double internationalPackageWidth) {
        this.internationalPackageWidth = internationalPackageWidth;
    }

    public LcTraceDTOBean getLcTraceDTO() {
        return lcTraceDTO;
    }

    public void setLcTraceDTO(LcTraceDTOBean lcTraceDTO) {
        this.lcTraceDTO = lcTraceDTO;
    }

    public double getLocalFreight() {
        return localFreight;
    }

    public void setLocalFreight(double localFreight) {
        this.localFreight = localFreight;
    }

    public int getLocalPackageNum() {
        return localPackageNum;
    }

    public void setLocalPackageNum(int localPackageNum) {
        this.localPackageNum = localPackageNum;
    }

    public int getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(int logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
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

    public boolean isNeedConfirmReceive() {
        return needConfirmReceive;
    }

    public void setNeedConfirmReceive(boolean needConfirmReceive) {
        this.needConfirmReceive = needConfirmReceive;
    }

    public boolean isNeedPayFreigth() {
        return needPayFreigth;
    }

    public void setNeedPayFreigth(boolean needPayFreigth) {
        this.needPayFreigth = needPayFreigth;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
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

    public double getOverseasFreight() {
        return overseasFreight;
    }

    public void setOverseasFreight(double overseasFreight) {
        this.overseasFreight = overseasFreight;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public boolean isPayFreightStatus() {
        return payFreightStatus;
    }

    public void setPayFreightStatus(boolean payFreightStatus) {
        this.payFreightStatus = payFreightStatus;
    }

    public String getPayFreightTime() {
        return payFreightTime;
    }

    public void setPayFreightTime(String payFreightTime) {
        this.payFreightTime = payFreightTime;
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

    public double getPredictFreightAmount() {
        return predictFreightAmount;
    }

    public void setPredictFreightAmount(double predictFreightAmount) {
        this.predictFreightAmount = predictFreightAmount;
    }

    public double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(double promotionAmount) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(double restAmount) {
        this.restAmount = restAmount;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public int getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(int shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(int useIntegration) {
        this.useIntegration = useIntegration;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public List<OrderItemListBean> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemListBean> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public static class LcTraceDTOBean {
        /**
         * logisticproviderid : string
         * responseitems : [{"billcode":"string","details":[{"city":"string","deliveryName":"string","deliveryTel":"string","desc":"string","latitude":"string","longitude":"string","nextSite":"string","scanscode":"string","scanstatus":"string","scantime":"string","scantype":"string","signer":"string","siteName":"string","siteType":"string"}],"mailno":"string","reason":"string","success":true,"txlogisticid":"string"}]
         */

        private String logisticproviderid;
        private List<ResponseitemsBean> responseitems;

        public String getLogisticproviderid() {
            return logisticproviderid;
        }

        public void setLogisticproviderid(String logisticproviderid) {
            this.logisticproviderid = logisticproviderid;
        }

        public List<ResponseitemsBean> getResponseitems() {
            return responseitems;
        }

        public void setResponseitems(List<ResponseitemsBean> responseitems) {
            this.responseitems = responseitems;
        }

        public static class ResponseitemsBean {
            /**
             * billcode : string
             * details : [{"city":"string","deliveryName":"string","deliveryTel":"string","desc":"string","latitude":"string","longitude":"string","nextSite":"string","scanscode":"string","scanstatus":"string","scantime":"string","scantype":"string","signer":"string","siteName":"string","siteType":"string"}]
             * mailno : string
             * reason : string
             * success : true
             * txlogisticid : string
             */

            private String billcode;
            private String mailno;
            private String reason;
            private boolean success;
            private String txlogisticid;
            private List<DetailsBean> details;

            public String getBillcode() {
                return billcode;
            }

            public void setBillcode(String billcode) {
                this.billcode = billcode;
            }

            public String getMailno() {
                return mailno;
            }

            public void setMailno(String mailno) {
                this.mailno = mailno;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public String getTxlogisticid() {
                return txlogisticid;
            }

            public void setTxlogisticid(String txlogisticid) {
                this.txlogisticid = txlogisticid;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public static class DetailsBean {
                /**
                 * city : string
                 * deliveryName : string
                 * deliveryTel : string
                 * desc : string
                 * latitude : string
                 * longitude : string
                 * nextSite : string
                 * scanscode : string
                 * scanstatus : string
                 * scantime : string
                 * scantype : string
                 * signer : string
                 * siteName : string
                 * siteType : string
                 */

                private String city;
                private String deliveryName;
                private String deliveryTel;
                private String desc;
                private String latitude;
                private String longitude;
                private String nextSite;
                private String scanscode;
                private String scanstatus;
                private String scantime;
                private String scantype;
                private String signer;
                private String siteName;
                private String siteType;

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getDeliveryName() {
                    return deliveryName;
                }

                public void setDeliveryName(String deliveryName) {
                    this.deliveryName = deliveryName;
                }

                public String getDeliveryTel() {
                    return deliveryTel;
                }

                public void setDeliveryTel(String deliveryTel) {
                    this.deliveryTel = deliveryTel;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getNextSite() {
                    return nextSite;
                }

                public void setNextSite(String nextSite) {
                    this.nextSite = nextSite;
                }

                public String getScanscode() {
                    return scanscode;
                }

                public void setScanscode(String scanscode) {
                    this.scanscode = scanscode;
                }

                public String getScanstatus() {
                    return scanstatus;
                }

                public void setScanstatus(String scanstatus) {
                    this.scanstatus = scanstatus;
                }

                public String getScantime() {
                    return scantime;
                }

                public void setScantime(String scantime) {
                    this.scantime = scantime;
                }

                public String getScantype() {
                    return scantype;
                }

                public void setScantype(String scantype) {
                    this.scantype = scantype;
                }

                public String getSigner() {
                    return signer;
                }

                public void setSigner(String signer) {
                    this.signer = signer;
                }

                public String getSiteName() {
                    return siteName;
                }

                public void setSiteName(String siteName) {
                    this.siteName = siteName;
                }

                public String getSiteType() {
                    return siteType;
                }

                public void setSiteType(String siteType) {
                    this.siteType = siteType;
                }
            }
        }
    }





    /* *//**
     * autoConfirmDay : 0
     * billContent : string
     * billHeader : string
     * billReceiverEmail : string
     * billReceiverPhone : string
     * billType : 0
     * cnFreight : 0
     * commentTime : 2020-10-17T12:16:24.109Z
     * confirmStatus : 0
     * couponAmount : 0
     * couponId : 0
     * createTime : 2020-10-17T12:16:24.109Z
     * deleteStatus : 0
     * deliverlyType : 0
     * deliveryCompany : string
     * deliverySn : string
     * deliveryTime : 2020-10-17T12:16:24.109Z
     * discountAmount : 0
     * freightAmount : 0
     * growth : 0
     * id : 0
     * integration : 0
     * integrationAmount : 0
     * logisticsStatus : 0
     * memberId : 0
     * memberUsername : string
     * modifyTime : 2020-10-17T12:16:24.109Z
     * note : string
     * orderItemList : [{"couponAmount":0,"giftGrowth":0,"giftIntegration":0,"id":0,"integrationAmount":0,"orderId":0,"orderSn":"string","productAttr":"string","productBrand":"string","productCategoryId":0,"productId":0,"productName":"string","productPic":"string","productPrice":0,"productQuantity":0,"productSkuCode":"string","productSkuId":0,"productSn":"string","promotionAmount":0,"promotionName":"string","realAmount":0}]
     * orderSn : string
     * orderType : 0
     * overseasFreight : 0
     * payAmount : 0
     * payType : 0
     * paymentTime : 2020-10-17T12:16:24.109Z
     * promotionAmount : 0
     * promotionInfo : string
     * receiveTime : 2020-10-17T12:16:24.109Z
     * receiverCity : string
     * receiverDetailAddress : string
     * receiverName : string
     * receiverPhone : string
     * receiverPostCode : string
     * receiverProvince : string
     * receiverRegion : string
     * remark : string
     * shippingType : 0
     * sourceType : 0
     * status : 0
     * totalAmount : 0
     * useIntegration : 0
     * warehouse : 0
     *//*

    private int autoConfirmDay;
    private String billContent;
    private String billHeader;
    private String billReceiverEmail;
    private String billReceiverPhone;
    private int billType;
    private double cnFreight;
    private String commentTime;
    private int confirmStatus;
    private double couponAmount;
    private long couponId;
    private String createTime;
    private int deleteStatus;
    private int deliverlyType;
    private String deliveryCompany;
    private String deliverySn;
    private String deliveryTime;
    private double discountAmount;
    private double freightAmount;
    private int growth;
    private long id;
    private int integration;
    private double integrationAmount;
    private int logisticsStatus;
    private long memberId;
    private String memberUsername;
    private String modifyTime;
    private String note;
    private String orderSn;
    private int orderType;
    private double overseasFreight;
    private double payAmount;
    private int payType;
    private String paymentTime;
    private double promotionAmount;
    private String promotionInfo;
    private String receiveTime;
    private String receiverCity;
    private String receiverDetailAddress;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverProvince;
    private String receiverRegion;
    private String remark;
    private int shippingType;
    private int sourceType;
    private int status;
    private double totalAmount;
    private double serviceAmount;
    private int useIntegration;
    private int warehouse;
    private List<OrderItemListBean> orderItemList;




    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

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

    public double getCnFreight() {
        return cnFreight;
    }

    public void setCnFreight(double cnFreight) {
        this.cnFreight = cnFreight;
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

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
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

    public int getDeliverlyType() {
        return deliverlyType;
    }

    public void setDeliverlyType(int deliverlyType) {
        this.deliverlyType = deliverlyType;
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

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(double freightAmount) {
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

    public double getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(double integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public int getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(int logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
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

    public double getOverseasFreight() {
        return overseasFreight;
    }

    public void setOverseasFreight(double overseasFreight) {
        this.overseasFreight = overseasFreight;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
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

    public double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(double promotionAmount) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getUseIntegration() {
        return useIntegration;
    }

    public void setUseIntegration(int useIntegration) {
        this.useIntegration = useIntegration;
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
    }

    public List<OrderItemListBean> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemListBean> orderItemList) {
        this.orderItemList = orderItemList;
    }*/
}
