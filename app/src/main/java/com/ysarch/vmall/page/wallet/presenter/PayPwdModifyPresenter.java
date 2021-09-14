package com.ysarch.vmall.page.wallet.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.wallet.PayPwdForgetFragment;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 2020/10/15
 **/
public class PayPwdModifyPresenter extends BasePresenter<PayPwdForgetFragment> {

    public void modifyPayPassword(String phone, String authCode,
                                 String payPassword){
        WalletLoader.getInstance().modifyPayPassword(phone, authCode, payPassword)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        UserInfoManager.getUser().setHasPayPassword(true);
                        UserInfoManager.update();
                        getV().onPWDModifySucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }

    /**
     * 获取验证码
     * @param phone
     */
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
}
