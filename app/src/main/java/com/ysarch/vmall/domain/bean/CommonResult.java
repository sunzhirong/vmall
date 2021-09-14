package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 28/09/2020
 **/
public class CommonResult implements Serializable {
    private int code;
    private String message;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
