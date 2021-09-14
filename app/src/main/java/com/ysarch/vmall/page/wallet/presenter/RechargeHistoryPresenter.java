package com.ysarch.vmall.page.wallet.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.wallet.RechargeHistoryFragment;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 2020/10/18
 **/
public class RechargeHistoryPresenter extends BasePresenter<RechargeHistoryFragment> {

    public void requestRechargeHistory(boolean showLoading){
        WalletLoader.getInstance().requestRechargeHistory()
                .compose(showLoading?showLoadingDialog():dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<RechargeItemBean>>(getV()) {
                    @Override
                    public void onSuccess(List<RechargeItemBean> rechargeItemBeans) {
                        getV().onDataSucc(rechargeItemBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail();
                    }
                });
    }
}
