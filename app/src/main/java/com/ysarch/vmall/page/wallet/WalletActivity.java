package com.ysarch.vmall.page.wallet;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.utils.NavHelper;

/**
 * Created by fysong on 17/09/2020
 **/
public class WalletActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_my_wallet);
    }

    @Override
    protected Fragment createFragment() {
        return new WalletFragment();
    }

    @Override
    protected View initBarRight() {
        TextView textView = new TextView(this);
        textView.setTextColor(0xff666666);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setText(getString(R.string.label_bill));
        return textView;
    }

    @Override
    protected void onTitlebarRightClick(View view) {
        NavHelper.startActivity(this, WalletLogActivity.class);
    }
}
