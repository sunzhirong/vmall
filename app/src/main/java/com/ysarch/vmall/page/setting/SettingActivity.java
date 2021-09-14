package com.ysarch.vmall.page.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 16/09/2020
 **/
public class SettingActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_setting);
    }

    @Override
    protected Fragment createFragment() {
        return new SettingFragment();
    }
}
