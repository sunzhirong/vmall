package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by fysong on 22/09/2020
 **/
public class GoodsDetailResult implements Serializable {


    private GoodsDetailBean item;
    @SerializedName("translate_status")
    private String translateStatus;
    private String api_type;
    private String request_id;

    public GoodsDetailBean getItem() {
        return item;
    }

    public void setItem(GoodsDetailBean item) {
        this.item = item;
    }

    public String getTranslateStatus() {
        return translateStatus;
    }

    public void setTranslateStatus(String translateStatus) {
        this.translateStatus = translateStatus;
    }

    public String getApi_type() {
        return api_type;
    }

    public void setApi_type(String api_type) {
        this.api_type = api_type;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }


    public static class LanguageBean implements Serializable {
        /**
         * default_lang : cn
         * current_lang : cn
         */

        @SerializedName("default_lang")
        private String defaultLang;
        @SerializedName("current_lang")
        private String currentLang;

        public String getDefaultLang() {
            return defaultLang;
        }

        public void setDefaultLang(String defaultLang) {
            this.defaultLang = defaultLang;
        }

        public String getCurrentLang() {
            return currentLang;
        }

        public void setCurrentLang(String currentLang) {
            this.currentLang = currentLang;
        }
    }
}
