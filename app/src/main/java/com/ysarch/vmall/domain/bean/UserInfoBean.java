package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fysong on 15/08/2020
 **/
public class UserInfoBean implements Serializable {
    @SerializedName("username")
    private String nickname;
    private long id;
    private String account;
    private String token;
    private String tokenHeader;

    private long memberLevelId;
    private int status;
    private String createTime;
    private String icon;

    private boolean hasPayPassword;


    private int orderReadyCount;
    private int orderReviewCount;
    private int orderWaitPayCount;
    private int orderWaitReceiveCount;
    private String birthday;
    private int gender;
    private int cartItemCount;

    public int getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(int cartItemCount) {
        this.cartItemCount = cartItemCount;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isHasPayPassword() {
        return hasPayPassword;
    }

    public void setHasPayPassword(boolean hasPayPassword) {
        this.hasPayPassword = hasPayPassword;
    }

    private float wallet;

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
