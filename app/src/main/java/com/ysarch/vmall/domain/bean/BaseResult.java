package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 28/09/2020
 **/
public class BaseResult<T extends Object> implements Serializable {
    private int code;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
