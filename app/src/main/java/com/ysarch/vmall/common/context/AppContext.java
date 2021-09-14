package com.ysarch.vmall.common.context;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.github.jokar.multilanguages.library.MultiLanguage;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.EnumBean;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.local.LanguageEntity;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.language.LocalManageUtil;
import com.ysarch.vmall.utils.ResUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by fysong on 15/08/2020.
 */

public class AppContext {
    private static Object object = new Object();
    private static AppContext sInstance;
    private String mDeviceId;
    private String mDeviceName;
    private Application sApplication;
    private String mChannelName = "official";
    private String mChannelId = "10000";

    private String mServicePhone;

    private LanguageEntity mLanguageEntity;
    private boolean bChaPingADOver = false;

    private List<CateLevelBean> mCateLevelBeans;
    private List<CateLevelBean> mCateHeaderBeans;


    /**
     * 充值平台银行账号
     */
    private List<BankItemBean> mBankItemBeans;

    /**
     * 提货仓库数据
     */
    private List<EnumBean> mWarehouseDatas;

    /**
     * 提货方式
     */
    private List<EnumBean> mModeDelivery;
    private List<EnumBean> mModeTransport;


    private AppContext(Application application) {
        sApplication = application;
        mDeviceId = CacheHelper.getString(CacheKeys.KEY_DEVICE_ID, "");
        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(sApplication.getApplicationContext());
        if (channelInfo != null) {
            Map<String, String> map = channelInfo.getExtraInfo();
            String channelId = map.get("channelid");
            if (TextUtils.isEmpty(channelId) || channelId.equals("0")) {
                mChannelId = "10000";
                mChannelName = "official";
            } else {
                mChannelId = channelId;
                mChannelName = channelInfo.getChannel();
            }
        }

    }

    public static AppContext getsInstance() {
        return sInstance;
    }

    public static void init(Application application) {
        if (sInstance == null) {
            synchronized (object) {
                if (sInstance == null) {
                    sInstance = new AppContext(application);
                }
            }
        }
    }

    /**
     * 发货方式
     *
     * @return
     */
    public List<EnumBean> getModeDelivery() {
        if (CollectionUtils.isEmpty(mModeDelivery)) {
            mModeDelivery = new ArrayList<>();
            mModeDelivery.add(new EnumBean(Constants.TYPE_DELIVERY_SEND_ON_ALL_READY,
                    ResUtils.getString(R.string.label_type_delivery_on_all_ready)));
            mModeDelivery.add(new EnumBean(Constants.TYPE_DELIVERY_SEND_ON_ARRIVE,
                    ResUtils.getString(R.string.label_type_delivery_on_arrive)));
        }
        return mModeDelivery;
    }

    /**
     * 运输方式
     *
     * @return
     */
    public List<EnumBean> getModeTransport() {
        if (CollectionUtils.isEmpty(mModeTransport)) {
            mModeTransport = new ArrayList<>();
            mModeTransport.add(new EnumBean(Constants.TYPE_TRANSPORT_LAND,
                    ResUtils.getString(R.string.label_mode_transport_land)));
            mModeTransport.add(new EnumBean(Constants.TYPE_TRANSPORT_SEA,
                    ResUtils.getString(R.string.label_mode_transport_sea)));
            mModeTransport.add(new EnumBean(Constants.TYPE_TRANSPORT_FRIGHT,
                    ResUtils.getString(R.string.label_mode_transport_fright)));
        }
        return mModeTransport;
    }

    /**
     * 提货仓库数据
     *
     * @return
     */
    public List<EnumBean> getWarehouseDatas() {
        return mWarehouseDatas;
    }

    public void setWarehouseDatas(List<EnumBean> warehouseDatas) {
        mWarehouseDatas = warehouseDatas;
    }

    public List<BankItemBean> getBankItemBeans() {
        return mBankItemBeans;
    }

    public void setBankItemBeans(List<BankItemBean> bankItemBeans) {
        mBankItemBeans = bankItemBeans;
    }

    public List<CateLevelBean> getCateLevelBeans() {
        return mCateLevelBeans;
    }

    public void setCateLevelBeans(List<CateLevelBean> cateLevelBeans) {
        mCateLevelBeans = cateLevelBeans;
    }


    public List<CateLevelBean> getCateHeaderBeans() {
        return mCateHeaderBeans;
    }

    public void setCateHeaderBeans(List<CateLevelBean> cateHeaderBeans) {
        mCateHeaderBeans = cateHeaderBeans;
    }

    public LanguageEntity getLanguageEntity() {
        if (mLanguageEntity == null) {
            int lanId = CacheHelper.getInt(CacheKeys.KEY_LAN_ID, -1);
            mLanguageEntity = new LanguageEntity(lanId);
        }
        return mLanguageEntity;
    }

    public void setLanguageEntity(LanguageEntity languageEntity) {
        mLanguageEntity = languageEntity;
    }

    public String getServicePhone() {
        return mServicePhone;
    }

    public void setServicePhone(String servicePhone) {
        mServicePhone = servicePhone;
    }

    public boolean setLanguageId(int lan) {
        if (mLanguageEntity == null || mLanguageEntity.getLanId() != lan) {
            mLanguageEntity = new LanguageEntity(lan);
            CacheHelper.putInt(CacheKeys.KEY_LAN_ID, lan);
            return true;
        }
        return false;
    }

    public boolean isbChaPingADOver() {
        return bChaPingADOver;
    }

    public void setbChaPingADOver(boolean bChaPingADOver) {
        this.bChaPingADOver = bChaPingADOver;
    }

    public Application getsApplication() {
        return sApplication;
    }

    public Context getAppliactionContext() {
        return sApplication.getApplicationContext();
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        CacheHelper.putString(CacheKeys.KEY_DEVICE_ID, deviceId);
        this.mDeviceId = deviceId;
    }

    public String getChannelId() {
        return mChannelId;
    }

    public String getChannelName() {
        return mChannelName;
    }

    /**
     * 设备名称标示
     */
    public String getDeviceName() {
        if (TextUtils.isEmpty(mDeviceName)) {
            mDeviceName = Build.BRAND + " " + Build.BOARD;
        }
        return mDeviceName;
    }


    private boolean mHasNewMsg;
    public void setHasNewMsg(boolean b) {
        mHasNewMsg = b;
    }

    public boolean getHasNewMsg(){
        return mHasNewMsg;
    }
}
