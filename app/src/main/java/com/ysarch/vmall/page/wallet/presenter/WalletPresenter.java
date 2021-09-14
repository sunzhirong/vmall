package com.ysarch.vmall.page.wallet.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.MemberBean;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.page.wallet.WalletFragment;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 17/09/2020
 **/
public class WalletPresenter extends BasePresenter<WalletFragment> {
    public void requestUserInfo() {
        AccountLoader.getInstance().requestUserInfo()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<MemberBean>(getV()) {
                    @Override
                    public void onSuccess(MemberBean userInfoResult) {
                        UserInfoManager.updateUserInfo(userInfoResult);
                        getV().onUserInfoSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}