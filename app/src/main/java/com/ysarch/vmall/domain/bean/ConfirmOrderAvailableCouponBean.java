package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 确认订单相关可用优惠券数据
 * Created by fysong on 03/10/2020
 **/
public class ConfirmOrderAvailableCouponBean implements Serializable {


    /**
     * categoryRelationList : [{"couponId":0,"id":0,"parentCategoryName":"string","productCategoryId":0,"productCategoryName":"string"}]
     * coupon : {"amount":0,"code":"string","count":0,"enableTime":"2020-10-03T04:31:32.942Z","endTime":"2020-10-03T04:31:32.942Z","id":0,"memberLevel":0,"minPoint":0,"name":"string","note":"string","perLimit":0,"platform":0,"publishCount":0,"receiveCount":0,"startTime":"2020-10-03T04:31:32.942Z","type":0,"useCount":0,"useType":0}
     * couponCode : string
     * couponId : 0
     * createTime : 2020-10-03T04:31:32.942Z
     * getType : 0
     * id : 0
     * memberId : 0
     * memberNickname : string
     * orderId : 0
     * orderSn : string
     * productRelationList : [{"couponId":0,"id":0,"productId":0,"productName":"string","productSn":"string"}]
     * useStatus : 0
     * useTime : 2020-10-03T04:31:32.942Z
     */

    private CouponBean coupon;
    private String couponCode;
    private long couponId;
    private String createTime;
    private int getType;
    private long id;
    private long memberId;
    private String memberNickname;
    private long orderId;
    private String orderSn;
    private int useStatus;
    private String useTime;
    private List<CategoryRelationListBean> categoryRelationList;
    private List<ProductRelationListBean> productRelationList;

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
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

    public int getGetType() {
        return getType;
    }

    public void setGetType(int getType) {
        this.getType = getType;
    }

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

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
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

    public int getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(int useStatus) {
        this.useStatus = useStatus;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public List<CategoryRelationListBean> getCategoryRelationList() {
        return categoryRelationList;
    }

    public void setCategoryRelationList(List<CategoryRelationListBean> categoryRelationList) {
        this.categoryRelationList = categoryRelationList;
    }

    public List<ProductRelationListBean> getProductRelationList() {
        return productRelationList;
    }

    public void setProductRelationList(List<ProductRelationListBean> productRelationList) {
        this.productRelationList = productRelationList;
    }


    public static class CategoryRelationListBean {
        /**
         * couponId : 0
         * id : 0
         * parentCategoryName : string
         * productCategoryId : 0
         * productCategoryName : string
         */

        private long couponId;
        private long id;
        private String parentCategoryName;
        private long productCategoryId;
        private String productCategoryName;

        public long getCouponId() {
            return couponId;
        }

        public void setCouponId(long couponId) {
            this.couponId = couponId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getParentCategoryName() {
            return parentCategoryName;
        }

        public void setParentCategoryName(String parentCategoryName) {
            this.parentCategoryName = parentCategoryName;
        }

        public long getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(long productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }
    }

    public static class ProductRelationListBean {
        /**
         * couponId : 0
         * id : 0
         * productId : 0
         * productName : string
         * productSn : string
         */

        private long couponId;
        private long id;
        private long productId;
        private String productName;
        private String productSn;

        public long getCouponId() {
            return couponId;
        }

        public void setCouponId(long couponId) {
            this.couponId = couponId;
        }

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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductSn() {
            return productSn;
        }

        public void setProductSn(String productSn) {
            this.productSn = productSn;
        }
    }
}
