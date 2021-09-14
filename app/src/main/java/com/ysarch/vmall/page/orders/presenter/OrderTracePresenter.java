package com.ysarch.vmall.page.orders.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.OrderTraceBean;
import com.ysarch.vmall.domain.services.OrderLoader;
import com.ysarch.vmall.page.orders.OrderTraceActivity;
import com.ysarch.vmall.page.orders.OrderTraceFragment;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

public class OrderTracePresenter extends BasePresenter<OrderTraceFragment> {

    public void getOrderTrace(long orderId) {
        OrderLoader.getInstance().getOrderTrace(orderId)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<OrderTraceBean>>(getV()) {
                    @Override
                    public void onSuccess(List<OrderTraceBean> data) {
                        getV().onDataSucc(data);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail();
                    }
                });
    }
}
