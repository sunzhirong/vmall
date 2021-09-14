package com.ysarch.vmall.page.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.utils.NavHelper;

/**
 * Created by fysong on 2020/10/15
 **/
public class RechargeActivity extends BaseTitleActivity {
    private RechargeFragment mRechargeFragment;

    public static Bundle getBundle(int bankId,double amount) {
        Bundle bundle = new Bundle();
        bundle.putDouble(BundleKey.ARG_RECHARGE_AMOUNT, amount);
        bundle.putInt(BundleKey.ARG_RECHARGE_BANK_ID, bankId);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_recharge_bank);
    }

    @Override
    protected View initBarRight() {
        TextView textView = new TextView(this);
        textView.setTextColor(0xff343434);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(getString(R.string.label_record));
        return textView;
    }

    @Override
    protected Fragment createFragment() {
        mRechargeFragment = new RechargeFragment();
        mRechargeFragment.setArguments(getIntent().getExtras());
        return mRechargeFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mRechargeFragment != null) {
            mRechargeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onTitlebarRightClick(View view) {
        NavHelper.startActivity(this,RechargeHistoryActivity.class);
    }
}
