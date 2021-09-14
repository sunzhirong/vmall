package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 2020/10/18
 **/
public class RechargeItemBean implements Serializable {

    /**
     * amount : 0
     * bankId : 0
     * createTime : 2020-10-17T17:25:29.753Z
     * id : 0
     * memberId : 0
     * rechargeNo : string
     * rechargePic : string
     * rechargeTime : 2020-10-17T17:25:29.753Z
     * rejectReason : string
     * remark : string
     * status : 0
     * updateTime : 2020-10-17T17:25:29.753Z
     */

    private double amount;
    private long bankId;
    private String createTime;
    private long id;
    private long memberId;
    private String rechargeNo;
    private String rechargePic;
    private String rechargeTime;
    private String rejectReason;
    private String remark;
    private int status;
    private String updateTime;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(String rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public String getRechargePic() {
        return rechargePic;
    }

    public void setRechargePic(String rechargePic) {
        this.rechargePic = rechargePic;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
