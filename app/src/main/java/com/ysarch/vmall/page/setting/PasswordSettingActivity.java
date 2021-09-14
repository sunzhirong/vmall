package com.ysarch.vmall.page.setting;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

import androidx.fragment.app.Fragment;

public class PasswordSettingActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_pay_password);
    }

    @Override
    protected Fragment createFragment() {
        return new PasswordSettingFragment();
    }
}
