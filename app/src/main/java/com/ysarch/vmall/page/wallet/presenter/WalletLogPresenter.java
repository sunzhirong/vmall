package com.ysarch.vmall.page.wallet.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.WalletLogBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.wallet.WalletLogFragment;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 2020/10/18
 **/
public class WalletLogPresenter extends BasePresenter<WalletLogFragment> {

    public boolean hasMore;

    public void requestWalletLogs(int page, boolean showLoading){
        WalletLoader.getInstance().requestWalletLogs(page, Constants.COUNT_PER_PAGE_GRID)
                .compose(showLoading?showLoadingDialog():dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<WalletLogBean>>(getV()) {
                    @Override
                    public void onSuccess(List<WalletLogBean> walletLogBeans) {
                        hasMore = (CollectionUtils.isNotEmpty(walletLogBeans)
                                && walletLogBeans.size() >= Constants.COUNT_PER_PAGE_GRID);
                        getV().onDataSucc(page, walletLogBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail(page);
                    }
                });
    }
}
