package com.ysarch.vmall.page.recharge;

import android.os.Bundle;
import android.view.View;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.wallet.WalletActivity;
import com.ysarch.vmall.utils.NavHelper;

import androidx.fragment.app.Fragment;

public class RechargeSuccessActivity  extends BaseTitleActivity {

    public static Bundle getBundle(String pageName) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_TALKINGDATA_PAGE_NAME, pageName);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_submit_succ);
    }

    @Override
    protected Fragment createFragment() {
        return new RechargeSuccessFragment();
    }

    @Override
    protected void onTitlebarLeftClick(View view) {
//        NavHelper.startActivity(this, WalletActivity.class);
        finish();
    }
}
