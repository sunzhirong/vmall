package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 2020/10/20
 **/
public class PriceRangeItemBean implements Serializable {

    /**
     * startAmount : 100
     * price : 0.25
     */

    private int startAmount;
    private double price;

    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
