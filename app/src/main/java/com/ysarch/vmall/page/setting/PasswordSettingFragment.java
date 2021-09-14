package com.ysarch.vmall.page.setting;

import android.os.Bundle;
import android.view.View;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.page.wallet.PayPwdForgetActivity;
import com.ysarch.vmall.page.wallet.PayPwdModifyActivity;
import com.ysarch.vmall.utils.NavHelper;

import butterknife.OnClick;

public class PasswordSettingFragment extends BaseFragment {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_password_setting;
    }

    @Override
    public Object newPresenter() {
        return null;
    }

    @OnClick({R.id.siv_modify_password, R.id.siv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_modify_password:
                NavHelper.startActivity(getActivity(), PayPwdModifyActivity.class);
                break;
            case R.id.siv_forget_password:
                NavHelper.startActivity(getActivity(), PayPwdForgetActivity.class);
                break;
        }
    }
}
