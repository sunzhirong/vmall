package com.ysarch.vmall.domain.local;

import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.ResUtils;

import java.io.Serializable;

/**
 * Created by fysong on 16/09/2020
 **/
public class LanguageEntity implements Serializable {
    private int mLanId;
    private int mIconRes;
    private int mCircleRes;
    private String mLabel;
    private String mShortCut;

    public LanguageEntity(int lanId) {
        mLanId = lanId;
        switch (lanId) {
            case Constants.ID_LAN_KM:
                mIconRes = R.drawable.ic_frag_km;
                mCircleRes = R.drawable.ic_language_kh;
                mLabel = ResUtils.getString(R.string.label_lan_cambodia);
                mShortCut = "km";
                break;
            case Constants.ID_LAN_ZH:
                mIconRes = R.drawable.ic_frag_cn;
                mCircleRes = R.drawable.ic_language_cn;
                mLabel = ResUtils.getString(R.string.label_lan_chinese);
                mShortCut = "zh";
                break;
            case Constants.ID_LAN_EN:
                mIconRes = R.drawable.ic_frag_en;
                mCircleRes = R.drawable.ic_language_en;
                mLabel = ResUtils.getString(R.string.label_lan_english);
                mShortCut = "en";
                break;
        }
    }

    public int getCircleRes() {
        return mCircleRes;
    }

    public void setCircleRes(int circleRes) {
        mCircleRes = circleRes;
    }

    public String getShortCut() {
        return mShortCut;
    }

    public void setShortCut(String shortCut) {
        mShortCut = shortCut;
    }

    public int getLanId() {
        return mLanId;
    }

    public void setLanId(int lanId) {
        mLanId = lanId;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public void setIconRes(int iconRes) {
        mIconRes = iconRes;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }
}
