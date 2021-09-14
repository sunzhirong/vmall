package com.ysarch.vmall.page.recharge;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.page.setting.SettingFragment;
import com.ysarch.vmall.page.wallet.RechargeFragment;

import androidx.fragment.app.Fragment;

public class RechargeAmountActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_recharge);
    }

    @Override
    protected Fragment createFragment() {
        return new RechargeAmountFragment();
    }
}
