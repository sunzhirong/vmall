package com.ysarch.vmall.page.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.page.recharge.RechargeAmountActivity;
import com.ysarch.vmall.page.setting.PasswordSettingActivity;
import com.ysarch.vmall.page.wallet.presenter.WalletPresenter;
import com.ysarch.vmall.utils.NavHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 17/09/2020
 **/
public class WalletFragment extends BaseFragment<WalletPresenter> {
    @BindView(R.id.tv_income_cash)
    TextView mTVBalance;
    @Override
    public void initData(Bundle savedInstanceState) {
        mTVBalance.setText(UserInfoManager.getUser().getWallet() + "");
        getPresenter().requestUserInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    public WalletPresenter newPresenter() {
        return new WalletPresenter();
    }


    @OnClick({R.id.tv_recharge_cash, R.id.tv_pay_password})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge_cash:
                NavHelper.startActivity(getActivity(), RechargeAmountActivity.class);
                break;
            case R.id.tv_pay_password:
                NavHelper.startActivity(getActivity(), PasswordSettingActivity.class);
                break;
//            case R.id.ctv_transaction_details_cash:
//                NavHelper.startActivity(getActivity(), WalletLogActivity.class);
//                break;
        }
    }

    public void onUserInfoSucc() {
        mTVBalance.setText(UserInfoManager.getUser().getWallet() + "");
    }
}
