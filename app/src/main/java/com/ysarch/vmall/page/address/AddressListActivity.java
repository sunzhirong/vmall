package com.ysarch.vmall.page.address;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.utils.NavHelper;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressListActivity extends BaseTitleActivity {
    private AddressListFragment mFragment;

    public static Bundle getBundle(AddressItemBean addressItemBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ARG_ADDRESS_BEAN, addressItemBean);
        bundle.putInt(BundleKey.ARG_ADDRESS_MODE, Constants.MODE_ADDRESS_SELECT);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_address_manager);
    }

    @Override
    protected View initBarRight() {
        TextView textView = new TextView(this);
        textView.setTextColor(0xff666666);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(getString(R.string.label_add_address));
        return textView;
    }

    @Override
    protected Fragment createFragment() {
        mFragment = new AddressListFragment();
        if (getIntent().getExtras() != null) {
            mFragment.setArguments(
                    getIntent().getExtras()
            );
        }
        return mFragment;
    }


    @Override
    protected void onTitlebarRightClick(View view) {
        if (mFragment.isAddressEmpty()) {
            NavHelper.startActivity(this, AddressEditActivity.class,
                    AddressEditActivity.getNoShowDefaultOptBundle(), RequestCode.CODE_EDIT_ADDRESS);
        } else {
            NavHelper.startActivity(this, AddressEditActivity.class, RequestCode.CODE_EDIT_ADDRESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragment != null) {
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
