package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 2020/10/18
 **/
public class WalletLogBean implements Serializable {

    /**
     * amount : 0
     * createTime : 2020-10-17T18:01:35.413Z
     * desc : string
     * id : 0
     * memberId : 0
     */

    private double amount;
    private String createTime;
    private String desc;
    private long id;
    private long memberId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
}
