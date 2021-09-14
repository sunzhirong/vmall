package com.ysarch.vmall.page.account.presenter;

import android.accounts.Account;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.page.account.InitPWFragment;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;

/**
 * Created by fysong on 13/09/2020
 **/
public class RegisterPresenter extends BasePresenter<InitPWFragment> {
    public void getSmsCode(String phone) {
        AccountLoader.getInstance().requestAuthCode(phone)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onAuthCodeSucc();
                    }
                });
    }

    public void doRegister(String phone, String nickname, String authCode, String password) {
        AccountLoader.getInstance().register(phone, authCode, nickname, password)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onRegisterSuccess();
                    }
                });
    }

    public void resetPassword(String phone, String authCode, String password) {
        AccountLoader.getInstance().updatePassword(phone, authCode, password)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        UserInfoManager.logout();
                        getV().resetPasswordSucc();
                    }
                });
    }
}
