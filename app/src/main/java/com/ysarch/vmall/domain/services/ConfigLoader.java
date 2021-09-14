package com.ysarch.vmall.domain.services;

import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.OssBean;

import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by fysong on 26/10/2020
 **/
public class ConfigLoader extends ObjectLoader {

    private ConfigService mService;

    private ConfigLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), ConfigService.class);
    }

    public static ConfigLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Flowable<SimpleResponse<OssBean>> requestOss() {
        return mService.requestOss();
    }

    interface ConfigService {
        @GET("mall/common/uploadToken")
        Flowable<SimpleResponse<OssBean>> requestOss();
    }

    private static class SingletonHolder {
        static ConfigLoader INSTANCE = new ConfigLoader();
    }
}
