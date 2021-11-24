package com.ysarch.vmall.page.recharge;

import android.os.Bundle;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.wallet.WalletActivity;
import com.ysarch.vmall.utils.NavHelper;

import butterknife.OnClick;

public class RechargeSuccessFragment extends BaseFragment {

    private String pagename;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            pagename = getArguments().getString(BundleKey.ARG_TALKINGDATA_PAGE_NAME);
        }
    }

    @Override
    protected String getPageName() {
        return pagename;
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
