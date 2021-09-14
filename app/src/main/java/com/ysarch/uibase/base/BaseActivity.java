package com.ysarch.uibase.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ysarch.uibase.dialog.LoadingDialog;
import com.ysarch.vmall.utils.LanguageUtils;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：Ly
 * 时间：2018/3/9  10:58
 * 描述：这是一个类，用于基准类
 */

public abstract class BaseActivity<P extends IPresent> extends XActivity<P> {

    LoadingDialog loadingDialog;


    @Override
    public void onBackPressed() {
        if (onBack()) {
            super.onBackPressed();
        }
    }

    public boolean onBack() {
        return true;
    }

    @Override
    public void setUpStatusBar() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color));
    }

    /**
     * 显示Toast
     *
     * @param message
     */
    @Override
    public void showTs(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && v != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    /**
     * 是否该关闭软键盘
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


    @Override
    public Dialog showLoadingDialog() {
        return this.showLoadingDialog(true);
    }

    @Override
    public Dialog showLoadingDialog(boolean isCancelable) {
        return this.showLoadingDialog(null, isCancelable);
    }

    @Override
    public Dialog showLoadingDialog(String loadingText, boolean isCancelable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        return loadingDialog.show(loadingText, isCancelable);
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = LanguageUtils.setAppLocalLanguage(newBase);
        super.attachBaseContext(context);
    }


}
