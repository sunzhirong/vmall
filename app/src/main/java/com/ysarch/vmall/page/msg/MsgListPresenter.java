package com.ysarch.vmall.page.msg;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.MsgBean;
import com.ysarch.vmall.domain.services.AccountLoader;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 27/10/2020
 **/
public class MsgListPresenter extends BasePresenter<MsgListFragment> {
    public void requestMsgList(int msgType, boolean showLoading){
        AccountLoader.getInstance().requestMsgDatas(msgType)
                .compose(showLoading?showLoadingDialog():dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<MsgBean>>(getV()) {
                    @Override
                    public void onSuccess(List<MsgBean> msgBeans) {
                        getV().onDataSucc(msgBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().resetUIStatus(1, false);
                    }
                });
    }
}
