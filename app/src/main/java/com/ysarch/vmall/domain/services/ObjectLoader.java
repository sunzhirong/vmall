package com.ysarch.vmall.domain.services;

import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;

/**
 * Created by fysong on 21/01/2019.
 */

public class ObjectLoader {
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }


    protected <T> T getRetrifitService(String url, Class<T> tClass) {
        return getRetrifitService(url, tClass, true);
    }

    protected <T> T getRetrifitService(String url, Class<T> tClass, boolean useRx) {
        return XApi.getInstance().getRetrofit(url, useRx).create(tClass);
    }
}
