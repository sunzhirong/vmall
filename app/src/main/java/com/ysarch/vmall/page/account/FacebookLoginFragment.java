package com.ysarch.vmall.page.account;

import android.os.Bundle;

import com.ysarch.vmall.R;
import com.ysarch.vmall.page.account.presenter.LoginPresenter;

public class FacebookLoginFragment extends AbsAccountFragment<LoginPresenter>{
    @Override
    protected void clearData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_facebook;
    }

    @Override
    public LoginPresenter newPresenter() {
        return new LoginPresenter();
    }
}
