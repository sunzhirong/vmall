package com.ysarch.vmall.page.splash;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.domain.services.AccountLoader;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 08/09/2020
 **/
public class SplashPresenter extends BasePresenter<SplashActivity> {

    public void refreshToken() {
        AccountLoader.getInstance().refreshToken()
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        UserInfoManager.updateToken(loginResult.getToken(),loginResult.getTokenHead());
                        getV().afterResreshToken();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        UserInfoManager.logout();
                        getV().afterResreshToken();
                    }
                });
    }
}
