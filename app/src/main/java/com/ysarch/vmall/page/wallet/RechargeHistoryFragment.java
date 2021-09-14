package com.ysarch.vmall.page.wallet;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.vmall.common.adapter.RechargeHistoryAdapter;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.ysarch.vmall.page.wallet.presenter.RechargeHistoryPresenter;

import java.util.List;

/**
 * Created by fysong on 2020/10/18
 **/
public class RechargeHistoryFragment extends CommonPureListFragment<RechargeHistoryPresenter> {

    public void onDataSucc(List<RechargeItemBean> rechargeItemBeans) {
        ((RechargeHistoryAdapter) mAdapter).refreshData(rechargeItemBeans);
        resetUIStatus(1, true);
    }

    public void onDataFail() {
        resetUIStatus(1, false);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new RechargeHistoryAdapter(getContext());
    }

    @Override
    protected void requestData(int page) {
        getPresenter().requestRechargeHistory(false);
    }

    @Override
    protected void requestDataOnEmpty() {
        getPresenter().requestRechargeHistory(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initRecyclerView(null);
        requestDataOnEmpty();
    }

    @Override
    protected boolean isPagingData() {
        return false;
    }

    @Override
    public RechargeHistoryPresenter newPresenter() {
        return new RechargeHistoryPresenter();
    }
}
