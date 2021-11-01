package com.ysarch.vmall.page.setting;

import android.content.Intent;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OwnerSettingActivity extends BaseTitleActivity {

    private OwnerSettingFragment mOwnerSettingFragment;

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_owner_setting);
    }

    @Override
    protected Fragment createFragment() {
        mOwnerSettingFragment = new OwnerSettingFragment();
        return mOwnerSettingFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mOwnerSettingFragment != null) {
            mOwnerSettingFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
