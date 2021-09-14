package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 行政区数据（省市区）
 * Created by fysong on 07/10/2020
 **/
public class RegionsResult implements Serializable {
    private List<ZoneItemBean> provinceList;
    private List<ZoneItemBean> cityList;
    private List<ZoneItemBean> districtList;

    public List<ZoneItemBean> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<ZoneItemBean> provinceList) {
        this.provinceList = provinceList;
    }

    public List<ZoneItemBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<ZoneItemBean> cityList) {
        this.cityList = cityList;
    }

    public List<ZoneItemBean> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<ZoneItemBean> districtList) {
        this.districtList = districtList;
    }
}
