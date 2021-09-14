package com.ysarch.vmall.page.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.page.main.MainCartFragment;

/**
 * Created by fysong on 31/10/2020
 **/
public class CartActivity extends BaseTitleActivity {

    @Override
    protected String getCustomTitle() {
        return null;
    }

    @Override
    protected Fragment createFragment() {
        return new MainCartFragment(true);
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
