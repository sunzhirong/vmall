package com.ysarch.vmall.page.setting.language;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 16/09/2020
 **/
public class LanguageSettingActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_language_choice);
    }

    @Override
    protected Fragment createFragment() {
        return new LanguageSettingFragment();
    }


    public static void reStart(Context context) {
        Intent intent = new Intent(context, LanguageSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
