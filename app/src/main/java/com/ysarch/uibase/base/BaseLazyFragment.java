package com.ysarch.uibase.base;

import android.app.Dialog;
import android.widget.Toast;


import com.ysarch.uibase.dialog.LoadingDialog;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * Created by fysong on 2020-07-12
 **/
public abstract class BaseLazyFragment<T extends IPresent> extends XLazyFragment<T> {


    private LoadingDialog loadingDialog;
    @Override
    public Dialog showLoadingDialog() {
         return this.showLoadingDialog(false);
    }

    @Override
    public Dialog showLoadingDialog(String loadingText, boolean isCancelable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        return loadingDialog.show(loadingText, isCancelable);
    }

    @Override
    public Dialog showLoadingDialog(boolean isCancelable) {
        return this.showLoadingDialog(null, isCancelable);
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showTs(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeSelf() {
        getActivity().finish();
    }
}
