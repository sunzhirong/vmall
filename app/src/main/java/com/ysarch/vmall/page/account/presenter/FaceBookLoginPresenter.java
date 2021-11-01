package com.ysarch.vmall.page.account.presenter;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.account.FacebookLoginFragment;
import com.ysarch.vmall.page.account.LoginFragment;
import com.ysarch.vmall.utils.Log;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 13/09/2020
 **/
public class FaceBookLoginPresenter extends BasePresenter<FacebookLoginFragment> {



    public void facebookLogin( String outId, String token, String name) {
        getV().showLoadingDialog();
        AccountLoader.getInstance().facebookLogin(outId, token,name)
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResult>(getV()) {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        UserInfoManager.updateToken("", loginResult.getToken(), loginResult.getTokenHead(),
                                loginResult.getMember(),loginResult.getMember().isHasPayPassword());
                        Log.e("onSuccess", JSON.toJSONString(loginResult));
                        getV().onLoginSuccess();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}
