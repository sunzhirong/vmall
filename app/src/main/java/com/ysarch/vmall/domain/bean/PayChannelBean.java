package com.ysarch.vmall.domain.bean;

public class PayChannelBean {

    /**
     * createTime : 2021-02-03T07:25:10.086Z
     * enRemark : string
     * icon : string
     * id : 0
     * khRemark : string
     * payName : string
     * payType : 0
     * relateBank : 0
     * remark : string
     * serviceFeeRate : 0
     * showStatus : true
     * sort : 0
     * updateTime : 2021-02-03T07:25:10.086Z
     */

    private String createTime;
    private String enRemark;
    private String icon;
    private int id;
    private String khRemark;
    private String payName;
    private int payType;
    private int relateBank;
    private String remark;
    private double serviceFeeRate;
    private boolean showStatus;
    private int sort;
    private String updateTime;

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEnRemark() {
        return enRemark;
    }

    public void setEnRemark(String enRemark) {
        this.enRemark = enRemark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKhRemark() {
        return khRemark;
    }

    public void setKhRemark(String khRemark) {
        this.khRemark = khRemark;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getRelateBank() {
        return relateBank;
    }

    public void setRelateBank(int relateBank) {
        this.relateBank = relateBank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(double serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
