package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fysong on 08/10/2020
 **/
public class MemberBean implements Serializable {




    /**
     * id : 12
     * memberLevelId : 4
     * username : 沙世
     * status : 1
     * icon : http:
     */

    private long id;
    private long memberLevelId;
    private String username;
    private int status;
    private String icon;
    private float wallet;
    private String phone;
    private boolean hasPayPassword;
    /**
     * birthday : 2021-02-04T03:00:39.985Z
     * city : string
     * couponCount : 0
     * gender : 0
     * growth : 0
     * historyIntegration : 0
     * id : 0
     * integration : 0
     * job : string
     * luckeyCount : 0
     * memberLevelId : 0
     * nickname : string
     * orderReadyCount : 0
     * orderReviewCount : 0
     * orderWaitPayCount : 0
     * orderWaitReceiveCount : 0
     * personalizedSignature : string
     * sourceType : 0
     * sumTipsCount : 0
     * wallet : 0
     */

    private String birthday;
    private String city;
    private int couponCount;
    private int gender;
    private int growth;
    private int historyIntegration;
    private int integration;
    private String job;
    private int luckeyCount;
    private String nickname;
    private int orderReadyCount;
    private int orderReviewCount;
    private int orderWaitPayCount;
    private int orderWaitReceiveCount;
    private String personalizedSignature;
    private int sourceType;
    private int sumTipsCount;
    private int cartItemCount;

    public int getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(int cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    public boolean isHasPayPassword() {
        return hasPayPassword;
    }

    public void setHasPayPassword(boolean hasPayPassword) {
        this.hasPayPassword = hasPayPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getHistoryIntegration() {
        return historyIntegration;
    }

    public void setHistoryIntegration(int historyIntegration) {
        this.historyIntegration = historyIntegration;
    }


    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getLuckeyCount() {
        return luckeyCount;
    }

    public void setLuckeyCount(int luckeyCount) {
        this.luckeyCount = luckeyCount;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOrderReadyCount() {
        return orderReadyCount;
    }

    public void setOrderReadyCount(int orderReadyCount) {
        this.orderReadyCount = orderReadyCount;
    }

    public int getOrderReviewCount() {
        return orderReviewCount;
    }

    public void setOrderReviewCount(int orderReviewCount) {
        this.orderReviewCount = orderReviewCount;
    }

    public int getOrderWaitPayCount() {
        return orderWaitPayCount;
    }

    public void setOrderWaitPayCount(int orderWaitPayCount) {
        this.orderWaitPayCount = orderWaitPayCount;
    }

    public int getOrderWaitReceiveCount() {
        return orderWaitReceiveCount;
    }

    public void setOrderWaitReceiveCount(int orderWaitReceiveCount) {
        this.orderWaitReceiveCount = orderWaitReceiveCount;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getSumTipsCount() {
        return sumTipsCount;
    }

    public void setSumTipsCount(int sumTipsCount) {
        this.sumTipsCount = sumTipsCount;
    }

}
