package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 2020/10/16
 **/
public class RechargeRecordBean implements Serializable {

    /**
     * amount : 0
     * bankId : 0
     * createTime : 2020-10-15T17:02:32.569Z
     * id : 0
     * memberId : 0
     * rechargePic : string
     * rechargeTime : 2020-10-15T17:02:32.569Z
     * remark : string
     * status : 0
     * updateTime : 2020-10-15T17:02:32.569Z
     */

    private float amount;
    private long bankId;
    private String createTime;
    private long id;
    private long memberId;
    private String rechargePic;
    private String rechargeTime;
    private String remark;
    private int status;
    private String updateTime;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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
