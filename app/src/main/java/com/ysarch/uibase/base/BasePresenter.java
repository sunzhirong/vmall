package com.ysarch.uibase.base;

import android.os.Environment;

import com.ysarch.uibase.net.HttpFlowableTransformer;

import org.reactivestreams.Publisher;

import java.io.File;

import cn.droidlover.xdroidmvp.mvp.IView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.SimpleResponse;
import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 作者：Ly
 * 时间：2018/3/9  10:58
 * 描述：这是一个类，用于基准类
 */

public abstract class BasePresenter<V extends IView> extends XPresent<V> {
    /**
     * 默认的下载文件夹
     * Dir: /Download
     */
    private final File mDownloadDir = new File(Environment.getExternalStorageDirectory(), "MeToo");

    public File getDownLoadPath() {
        return mDownloadDir;
    }


    public <T> FlowableTransformer<SimpleResponse<T>, T> dontShowDialog(Class<T> clazz) {
        return upstream -> upstream.compose(new HttpFlowableTransformer<>(clazz))
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    public <T> FlowableTransformer<SimpleResponse<T>, T> dontShowDialog() {
        return upstream -> upstream.compose(new HttpFlowableTransformer<>(null))
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    public <T> FlowableTransformer<SimpleResponse<T>, T> showLoadingDialog() {
        return this.showLoadingDialog(null, false, null);
    }

    public <T> FlowableTransformer<SimpleResponse<T>, T> showLoadingDialog(Class<T> clazz) {
        return this.showLoadingDialog(null, false, clazz);
    }


    public <T> FlowableTransformer<SimpleResponse<T>, T> showLoadingDialog(boolean isCanable, Class<T> clazz) {
        return this.showLoadingDialog(null, isCanable, clazz);
    }


    public <T> FlowableTransformer<SimpleResponse<T>, T> showLoadingDialog(String loadingText, boolean isCanable, Class<T> clazz) {
        return new FlowableTransformer<SimpleResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<SimpleResponse<T>> upstream) {
                return upstream.compose(new HttpFlowableTransformer<>(clazz))
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .doOnSubscribe(subscription -> {
                            getV().showLoadingDialog(loadingText, isCanable);
                        }).subscribeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 格式化字符串
     *
     * @param packAgeName
     * @return
     */
    public String formatPackAgeName(String packAgeName) {
        return packAgeName.replaceAll("\\.", "_");
    }

//
//    public boolean judeIsLogin(Activity context) {
//        if (!UserInfoManager.isLogin()) {
//            toLogin(context);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//
//    public void toLogin(Activity context) {
//        NavHelper.startActivity(context, AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
//    }
}