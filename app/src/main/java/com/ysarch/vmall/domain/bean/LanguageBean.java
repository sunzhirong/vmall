package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fysong on 18/09/2020
 **/
public class LanguageBean implements Serializable {
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
