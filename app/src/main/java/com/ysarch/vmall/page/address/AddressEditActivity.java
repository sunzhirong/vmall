package com.ysarch.vmall.page.address;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressEditActivity extends BaseTitleActivity {


    private AddressEditFragment mAddressEditFragment;

    public static Bundle getBundle(AddressItemBean addressItemBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ARG_ADDRESS_BEAN, addressItemBean);
        bundle.putBoolean(BundleKey.ARG_ADDRESS_DEFAULT_OPT_SHOW, true);
        return bundle;
    }


    public static Bundle getNoShowDefaultOptBundle(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(BundleKey.ARG_ADDRESS_DEFAULT_OPT_SHOW, false);
        bundle.putBoolean(BundleKey.ARG_AUTO_SELECT, true);
        return bundle;
    }

    public static Bundle getConfirmOrderOptBundle(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(BundleKey.ARG_ADDRESS_DEFAULT_OPT_SHOW, false);
        bundle.putBoolean(BundleKey.ARG_AUTO_SELECT, true);
        bundle.putBoolean(BundleKey.ARG_IS_CONFIRM_ORDER, true);
        return bundle;
    }


    public static Bundle getAutoSelectBundle(boolean autoSelect){
        Bundle bundle = new Bundle();
        bundle.putBoolean(BundleKey.ARG_ADDRESS_DEFAULT_OPT_SHOW, true);
        bundle.putBoolean(BundleKey.ARG_AUTO_SELECT, autoSelect);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_add_new_address);
    }

    @Override
    protected View initBarRight() {
        TextView textView = new TextView(this);
        textView.setTextColor(0xff666666);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(getString(R.string.label_save));
        return textView;
    }

    @Override
    protected Fragment createFragment() {
        mAddressEditFragment = new AddressEditFragment();
        if (getIntent().getExtras() != null) {
            mAddressEditFragment.setArguments(getIntent().getExtras());
        }
        return mAddressEditFragment;
    }

    @Override
    protected void onTitlebarRightClick(View view) {
        mAddressEditFragment.onSaveClick();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
