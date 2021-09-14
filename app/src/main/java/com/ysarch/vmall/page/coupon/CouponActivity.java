package com.ysarch.vmall.page.coupon;

import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_coupon);
    }

    @Override
    protected Fragment createFragment() {
        return new CouponFragment();
    }
}
