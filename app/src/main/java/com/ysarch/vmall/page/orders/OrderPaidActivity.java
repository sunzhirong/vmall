package com.ysarch.vmall.page.orders;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

import androidx.fragment.app.Fragment;

/**
 * Created by fysong on 2020/10/15
 **/
public class OrderPaidActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_pay_succ);
    }

    @Override
    protected Fragment createFragment() {
        return new OrderPaidFragment();
    }
}
