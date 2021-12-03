package com.ysarch.vmall;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.github.jokar.multilanguages.library.LanguageLocalListener;
import com.github.jokar.multilanguages.library.MultiLanguage;
import com.tencent.bugly.crashreport.CrashReport;
import com.tendcloud.tenddata.TCAgent;
import com.ysarch.vmall.common.context.ActivityLifeCallback;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.net.GlobalParamInterceptor;
import com.ysarch.vmall.common.net.RequestStatusHandler;
import com.ysarch.vmall.component.ease.EaseHelper;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.local.LanguageEntity;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.helper.VersionHelper;
import com.ysarch.vmall.language.LocalManageUtil;
import com.ysarch.vmall.language.SPUtil;
import com.ysarch.vmall.utils.LanguageUtils;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;

import java.nio.channels.NonReadableChannelException;
import java.util.Locale;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by fysong on 2020-07-06
 **/
public class VMallApplication extends MultiDexApplication {


    public static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        initMvp();
        CacheHelper.init(this);
        AppContext.init(this);
        SizeUtils.init(this);
        ResUtils.init(this);
        VersionHelper.init();
        TCAgent.init(this);
        EaseHelper.getInstance().init(this);

        registerActivityLifecycleCallbacks(new ActivityLifeCallback());

        CrashReport.initCrashReport(getApplicationContext(), "f65f66f857", false);

        MultiLanguage.init(new LanguageLocalListener() {
            @Override
            public Locale getSetLanguageLocale(Context context) {
                //返回自己本地保存选择的语言设置
                Log.e("language333",LocalManageUtil.getSystemLocale(context).getCountry());
//                return LocalManageUtil.getSetLanguageLocale(context);
                int lanId = CacheHelper.getInt(CacheKeys.KEY_LAN_ID, -1);
//                int lanId = -1;
                if(lanId == -1){
                    //未设置过语言
                    String country = LocalManageUtil.getSystemLocale(context).getCountry();
                    switch (country){
                        case "EN":
                        case "US":
                            CacheHelper.putInt(CacheKeys.KEY_LAN_ID,Constants.ID_LAN_EN);
                            SPUtil.getInstance(context).saveLanguage(Constants.ID_LAN_EN);
                            return Locale.ENGLISH;
                        case "KH":
                            CacheHelper.putInt(CacheKeys.KEY_LAN_ID,Constants.ID_LAN_KM);
                            SPUtil.getInstance(context).saveLanguage(Constants.ID_LAN_KM);
                            return new Locale("kh");
                        case "CN":
                            CacheHelper.putInt(CacheKeys.KEY_LAN_ID,Constants.ID_LAN_ZH);
                            SPUtil.getInstance(context).saveLanguage(Constants.ID_LAN_ZH);
                            return Locale.CHINA;
                        default:
                            CacheHelper.putInt(CacheKeys.KEY_LAN_ID,Constants.ID_LAN_EN);
                            SPUtil.getInstance(context).saveLanguage(Constants.ID_LAN_EN);
                            return Locale.ENGLISH;
                    }
                }else {
                    return LocalManageUtil.getSetLanguageLocale(context);
                }



            }
        });
        MultiLanguage.setApplicationLanguage(this);
    }



    @Override
    protected void attachBaseContext(Context base) {
        //第一次进入app时保存系统选择语言(为了选择随系统语言时使用，如果不保存，切换语言后就拿不到了）
        LocalManageUtil.saveSystemCurrentLanguage(base);
        Log.e("language1",MultiLanguage.getSystemLocal(base).getCountry());
        super.attachBaseContext(MultiLanguage.setLocal(base));
//        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initMvp() {
        XApi.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[]{new GlobalParamInterceptor()};
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {
            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return RequestStatusHandler.getInstance();
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //用户在系统设置页面切换语言时保存系统选择语言(为了选择随系统语言时使用，如果不保存，切换语言后就拿不到了）
        LocalManageUtil.saveSystemCurrentLanguage(getApplicationContext(), newConfig);
        MultiLanguage.onConfigurationChanged(getApplicationContext());
    }
}
