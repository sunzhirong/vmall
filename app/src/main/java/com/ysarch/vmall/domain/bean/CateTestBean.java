package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 12/09/2020
 **/
public class CateTestBean implements Serializable {
    public String message;
    public int code;
    public List<CateLevelBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CateLevelBean> getData() {
        return data;
    }

    public void setData(List<CateLevelBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
