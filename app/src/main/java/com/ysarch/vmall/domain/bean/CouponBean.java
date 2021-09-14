package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 25/09/2020
 **/
public class CouponBean implements Serializable {

    /**
     * amount : 0
     * code : string
     * count : 0
     * enableTime : 2020-09-25T04:49:32.478Z
     * endTime : 2020-09-25T04:49:32.478Z
     * id : 0
     * memberLevel : 0
     * minPoint : 0
     * name : string
     * note : string
     * perLimit : 0
     * platform : 0
     * publishCount : 0
     * receiveCount : 0
     * startTime : 2020-09-25T04:49:32.478Z
     * type : 0
     * useCount : 0
     * useType : 0
     */

    private double amount;
    private String code;
    private int count;
    private String enableTime;
    private String endTime;
    private long id;
    private int memberLevel;
    private int minPoint;
    private String name;
    private String note;
    private int perLimit;
    private int platform;
    private int publishCount;
    private int receiveCount;
    private String startTime;
    private int type;
    private int useCount;
    private int useType;

    private long couponId;


    private int couponType;

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    private boolean isLocalSelected;

    public boolean isLocalSelected() {
        return isLocalSelected;
    }

    public void setLocalSelected(boolean localSelected) {
        isLocalSelected = localSelected;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public int getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(int minPoint) {
        this.minPoint = minPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPerLimit() {
        return perLimit;
    }

    public void setPerLimit(int perLimit) {
        this.perLimit = perLimit;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(int publishCount) {
        this.publishCount = publishCount;
    }

    public int getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(int receiveCount) {
        this.receiveCount = receiveCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }
}
