package com.ysarch.vmall.domain.local;

import com.ysarch.vmall.domain.bean.SkuBeanV2;

import java.io.Serializable;

/**
 * Created by fysong on 12/10/2020
 **/
public class MultiSkuEntity implements Serializable {
    private LocalSkuEntity mLocalSkuEntity;
    private SkuBeanV2 mSkuBeanV2;
    private int amount = 0;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public LocalSkuEntity getLocalSkuEntity() {
        return mLocalSkuEntity;
    }

    public void setLocalSkuEntity(LocalSkuEntity localSkuEntity) {
        mLocalSkuEntity = localSkuEntity;
    }

    public SkuBeanV2 getSkuBeanV2() {
        return mSkuBeanV2;
    }

    public void setSkuBeanV2(SkuBeanV2 skuBeanV2) {
        mSkuBeanV2 = skuBeanV2;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
