package com.ysarch.vmall.page.account.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.UserInfoResult;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.page.account.LoginFragment;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 13/09/2020
 **/
public class LoginPresenter extends BasePresenter<LoginFragment> {
    public void doLoginPassWord(String phone, String password) {
        getV().showLoadingDialog();
        AccountLoader.getInstance().login(phone, password)
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResult>(getV()) {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        UserInfoManager.updateToken(phone, loginResult.getToken(), loginResult.getTokenHead(),
                                loginResult.getMember(),loginResult.getMember().isHasPayPassword());
                        getV().onLoginSuccess();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}
