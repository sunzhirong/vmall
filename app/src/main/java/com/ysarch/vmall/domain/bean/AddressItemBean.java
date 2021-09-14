package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressItemBean implements Serializable {

    /**
     * city : string
     * defaultStatus : 0
     * detailAddress : string
     * id : 0
     * memberId : 0
     * name : string
     * phoneNumber : string
     * postCode : string
     * province : string
     * region : string
     */

    private String city;
    private int defaultStatus;
    private String detailAddress;
    private long id;
    private long memberId;
    private String name;
    private String phoneNumber;
    private String postCode;
    private String province;
    private String region;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(int defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
