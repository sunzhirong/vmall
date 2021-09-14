package com.ysarch.vmall.domain.local;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 24/09/2020
 **/
public class LocalPropSkuEntity implements Serializable {
    private String mPropLabel;
    private String mPropId;
    private List<LocalSkuEntity> mLocalSkuEntities;

    public String getPropId() {
        return mPropId;
    }

    public void setPropId(String propId) {
        mPropId = propId;
    }

    public String getPropLabel() {
        return mPropLabel;
    }

    public void setPropLabel(String propLabel) {
        mPropLabel = propLabel;
    }

    public List<LocalSkuEntity> getLocalSkuEntities() {
        return mLocalSkuEntities;
    }

    public void setLocalSkuEntities(List<LocalSkuEntity> localSkuEntities) {
        mLocalSkuEntities = localSkuEntities;
    }
}
