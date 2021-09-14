package com.ysarch.vmall.page.wallet;

import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 2020/10/18
 **/
public class WalletLogActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_wallet_logs);
    }

    @Override
    protected Fragment createFragment() {
        return new WalletLogFragment();
    }
}
