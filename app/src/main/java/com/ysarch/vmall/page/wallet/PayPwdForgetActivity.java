package com.ysarch.vmall.page.wallet;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

import androidx.fragment.app.Fragment;

public class PayPwdForgetActivity extends BaseTitleActivity {


    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_pay_password_modify);
    }

    @Override
    protected Fragment createFragment() {
        return new PayPwdForgetFragment();
    }



}
