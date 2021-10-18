package com.ysarch.vmall.page.setting;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.page.account.AccountActivity;
import com.ysarch.vmall.utils.NavHelper;

/**
 * Created by fysong on 16/09/2020
 **/
public class SettingActivity extends BaseTitleActivity {
    private int count = 0 ;
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_setting);
    }

    protected void initTitleBarWidgets(View barRootView) {
        super.initTitleBarWidgets(barRootView);
         barRootView.findViewById(R.id.tv_base_title_bar_title).setOnClickListener(v -> {
             count++;
             if(count == 10) {
                 NavHelper.startActivity(this, AccountActivity.class, AccountActivity.getBundle(true,AccountActivity.TYPE_LOGIN));
             }
         });
    }

    @Override
    protected Fragment createFragment() {
        return new SettingFragment();
    }
}
