package com.ysarch.vmall.domain.bean;

public class OrderTraceBean {

    /**
     * createTime : 2021-01-07T03:28:30.234Z
     * desc : string
     * eventType : 0
     * id : 0
     * orderId : 0
     * remark : string
     */

    private String createTime;
    private String desc;
    private int eventType;
    private int id;
    private int orderId;
    private String remark;
    private String tag;
    private boolean firstInfo;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isFirstInfo() {
        return firstInfo;
    }

    public void setFirstInfo(boolean firstInfo) {
        this.firstInfo = firstInfo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}