package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

public class WmsWarehouseInfoBean implements Serializable {
    /**
     * id : 1
     * name : 张三
     * address : 广州市北京路22号
     * enAddress : GUANGZHOU BEIJING ROAD 22#
     * khAddress : 广州市北京路22号
     * phone : 123123124
     */

    private int id;
    private String name;
    private String address;
    private String enAddress;
    private String khAddress;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnAddress() {
        return enAddress;
    }

    public void setEnAddress(String enAddress) {
        this.enAddress = enAddress;
    }

    public String getKhAddress() {
        return khAddress;
    }

    public void setKhAddress(String khAddress) {
        this.khAddress = khAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
