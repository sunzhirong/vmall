package com.ysarch.vmall.page.orders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.constant.BundleKey;

/**
 * Created by fysong on 28/09/2020
 **/
public class OrderConfirmActivity extends BaseTitleActivity {

    private OrderConfirmFragment mFragment;

    public static Bundle getBundle(GenerateOrderConfirmResult generateOrderConfirmResult) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ARG_ORDER_GENERATE_RESULT, generateOrderConfirmResult);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_fill_order_info);
    }

    @Override
    protected Fragment createFragment() {
        mFragment = new OrderConfirmFragment();
        if (getIntent().getExtras() != null) {
            mFragment.setArguments(getIntent().getExtras());
        }
        return mFragment;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragment != null) {
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
