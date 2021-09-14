package com.ysarch.vmall.page.recharge;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.PayChannelBean;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.wallet.RechargeFragment;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

public class RechargeAmountPresenter extends BasePresenter<RechargeAmountFragment> {

    /**
     * 获取银行列表数据
     */
    public void requestBanks() {
        WalletLoader.getInstance().listBanks()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<BankItemBean>>(getV()) {
                    @Override
                    public void onSuccess(List<BankItemBean> bankItemBeans) {
                        AppContext.getsInstance().setBankItemBeans(bankItemBeans);
                        getV().onBankData(bankItemBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }




    public void payChannelList(){
        WalletLoader.getInstance().payChannelList()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<PayChannelBean>>(getV()) {
                    @Override
                    public void onSuccess(List<PayChannelBean> payChannelBeans) {
//                        AppContext.getsInstance().setBankItemBeans(bankItemBeans);
                        getV().onPayChannelData(payChannelBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}
