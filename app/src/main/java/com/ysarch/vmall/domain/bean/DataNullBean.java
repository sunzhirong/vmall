package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * data 为空的bean
 * @author Morphine
 * @date 2018-04-20.
 */

public class DataNullBean implements Serializable {

    @SerializedName("position_type")
    private String simpleString;

    public String getSimpleString() {
        return simpleString;
    }

    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }
}
