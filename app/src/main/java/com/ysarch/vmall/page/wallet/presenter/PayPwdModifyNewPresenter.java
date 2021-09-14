package com.ysarch.vmall.page.wallet.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.wallet.PayPwdModifyNewFragment;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 2020/10/15
 **/
public class PayPwdModifyNewPresenter extends BasePresenter<PayPwdModifyNewFragment> {

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
                        getV().onPWDModifyFailed();
                    }
                });
    }

    public void verifyPayPassword(String payPassword){
        WalletLoader.getInstance().verifyPayPassword(payPassword)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        UserInfoManager.getUser().setHasPayPassword(true);
                        UserInfoManager.update();
                        getV().onPWDVerifySucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onPWDVerifyFailed();
                    }
                });
    }

    public void updatePayPwd(String newPayPassword,String oldPayPassword){
        WalletLoader.getInstance().updatePayPwd(newPayPassword,oldPayPassword)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        UserInfoManager.getUser().setHasPayPassword(true);
                        UserInfoManager.update();
                        getV().onPWDUpdateSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onPWDUpdateFailed();
                    }
                });
    }


}
