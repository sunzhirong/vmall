package com.ysarch.vmall.page.wallet;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.vmall.common.adapter.WalletLogAdapter;
import com.ysarch.vmall.domain.bean.WalletLogBean;
import com.ysarch.vmall.page.wallet.presenter.WalletLogPresenter;

import java.util.List;

/**
 * Created by fysong on 2020/10/18
 **/
public class WalletLogFragment extends CommonPureListFragment<WalletLogPresenter> {

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new WalletLogAdapter(getContext());
    }

    @Override
    protected void requestData(int page) {
        getPresenter().requestWalletLogs(page, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initRecyclerView(null);
        requestDataOnEmpty();
    }

    @Override
    protected void requestDataOnEmpty() {
        getPresenter().requestWalletLogs(1, true);
    }

    @Override
    public WalletLogPresenter newPresenter() {
        return new WalletLogPresenter();
    }

    public void onDataFail(int page) {
        resetUIStatus(page, false);
    }

    public void onDataSucc(int page, List<WalletLogBean> walletLogBeans) {
        if (page == 1) {
            ((WalletLogAdapter) mAdapter).refreshData(walletLogBeans);
        } else {
            ((WalletLogAdapter) mAdapter).appendData(walletLogBeans);
        }
        resetUIStatus(page, true);
    }

    @Override
    protected boolean hasMore() {
        return getPresenter().hasMore;
    }

    @Override
    protected String getPageName() {
        return "账单页";
    }
}
