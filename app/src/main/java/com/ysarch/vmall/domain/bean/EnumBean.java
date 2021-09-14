package com.ysarch.vmall.domain.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

/**
 * 仓库、发货模式、运输方式
 * Created by fysong on 2020/10/16
 **/
public class EnumBean implements Serializable, IPickerViewData {

    /**
     * desc : 仓库1号
     * id : 1
     */

    private String desc;
    private int id;
    private String address;

    public EnumBean() {
    }

    public EnumBean(int id, String desc) {
        this.desc = desc;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getPickerViewText() {
        return desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
