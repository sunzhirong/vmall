package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.constant.BundleKey;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by fysong on 15/09/2020
 **/
public class OrderHistoryActivity extends BaseTitleActivity {


    private OrderHistoryFragment mOrderHistoryFragment;

    public static Bundle getBundle(int navOrderStatus) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_TARGET_PAGE, navOrderStatus);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_history_order);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        StatusBarCompat.changeToLightStatusBar(this);
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected Fragment createFragment() {
        mOrderHistoryFragment = new OrderHistoryFragment();
        if(getIntent().getExtras() != null){
            mOrderHistoryFragment.setArguments(getIntent().getExtras());
        }

        return mOrderHistoryFragment;
    }
}
