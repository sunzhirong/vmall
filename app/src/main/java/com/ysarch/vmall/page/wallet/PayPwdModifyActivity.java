package com.ysarch.vmall.page.wallet;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 2020/10/15
 **/
public class PayPwdModifyActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FragmentTransaction fragTransact = getSupportFragmentManager().beginTransaction();
        fragTransact.replace(R.id.fl_fragment_container, new PayPwdModifyNewFragment());
        fragTransact.commitAllowingStateLoss();
    }
}


//        extends BaseTitleActivity {
//    @Override
//    protected String getCustomTitle() {
//        return null;
//    }
//
//    @Override
//    protected Fragment createFragment() {
//        return new PayPwdModifyNewFragment();
//    }
//
//
//}
