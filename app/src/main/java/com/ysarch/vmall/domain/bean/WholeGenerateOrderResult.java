package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 28/09/2020
 **/
public class WholeGenerateOrderResult implements Serializable {

    /**
     * code : 200
     * message : 操作成功
     * data : {}
     */

    private int code;
    private String message;
    private GenerateOrderConfirmResult data;

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

    public GenerateOrderConfirmResult getData() {
        return data;
    }

    public void setData(GenerateOrderConfirmResult data) {
        this.data = data;
    }


}
