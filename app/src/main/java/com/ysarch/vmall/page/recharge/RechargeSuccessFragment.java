package com.ysarch.vmall.page.recharge;

import android.os.Bundle;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.page.wallet.WalletActivity;
import com.ysarch.vmall.utils.NavHelper;

import butterknife.OnClick;

public class RechargeSuccessFragment extends BaseFragment {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recharge_succ;
    }

    @Override
    public Object newPresenter() {
        return null;
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
//        NavHelper.startActivity(getActivity(), WalletActivity.class);
        context.finish();
    }
}
