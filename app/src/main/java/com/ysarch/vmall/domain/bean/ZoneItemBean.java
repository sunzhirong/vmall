package com.ysarch.vmall.domain.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

/**
 * Created by fysong on 03/10/2020
 **/
public class ZoneItemBean implements Serializable, IPickerViewData {

    /**
     * text : 桑园区
     * value : 1301184153
     * parentVal : 1341162543
     */

    private String text;
    private String value;
    private String parentVal;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentVal() {
        return parentVal;
    }

    public void setParentVal(String parentVal) {
        this.parentVal = parentVal;
    }

    @Override
    public String getPickerViewText() {
        return text;
    }
}
