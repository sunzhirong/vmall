package com.ysarch.uibase.base;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.ysarch.uibase.dialog.LoadingDialog;
import com.ysarch.uibase.dialog.SimpleDialogWithTwoBtn;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.ysarch.vmall.utils.SystemUtil;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * 作者：Ly
 * 时间：2018/3/9  11:01
 * 描述：这是一个类，用于基准的Fragment
 */

public abstract class BaseFragment<P extends IPresent> extends XFragment<P> {
    /**
     * 除了textedit之外的点击
     */
    public View.OnTouchListener mOnGlobalTouchHideKeyBoardListener;
    /**
     * 检测当前获取焦点的EditText
     */
    protected View.OnFocusChangeListener mOnFocusChangeListener;
    private LoadingDialog loadingDialog;
    private EditText mETCurrent;


    @Override
    public void showTs(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public LoadingDialog showLoadingDialog() {
        return this.showLoadingDialog(true);
    }

    @Override
    public LoadingDialog showLoadingDialog(boolean isCancelable) {
        return this.showLoadingDialog(null, isCancelable);
    }

    @Override
    public LoadingDialog showLoadingDialog(String loadingText, boolean isCancelable) {
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

    /**
     * 获取app权限的方法 跳到app信息页面
     *
     * @param message
     */
    protected void showAlert2AppInfo(String message) {
        new SimpleDialogWithTwoBtn.Builder(getContext())
                .setWarning(message)
                .setLeftLabel(getString(R.string.label_cancel))
                .setRightLabel(getString(R.string.label_next))
                .setAutoDismissWhileClick(true)
                .setOnSubmitListener(new SimpleDialogWithTwoBtn.OnSubmitListener() {
                    @Override
                    public void onLeftBtnClick() {

                    }

                    @Override
                    public void onRightBtnClick() {
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
                        startActivity(localIntent);
                    }
                }).build().show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void closeSelf() {
        getActivity().finish();
    }

    // ###############   editText focus 处理相关  ######################

    public View.OnTouchListener getOnGlobalTouchHideKeyBoardListener() {
        if (mOnGlobalTouchHideKeyBoardListener == null) {
            mOnGlobalTouchHideKeyBoardListener = (v, event) -> {
                int action = event.getAction();
                Log.i("onTouch", action + "");
                if ((mETCurrent != null && mETCurrent.hasFocus()) &&
                        !(v instanceof EditText) && (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL)) {
                    clearFocus();
                    return true;
                }
                return false;
            };
        }
        return mOnGlobalTouchHideKeyBoardListener;
    }

    protected View.OnFocusChangeListener getOnFocusChangeListener() {
        if (mOnFocusChangeListener == null) {
            mOnFocusChangeListener = (v, hasFocus) -> {
                if (hasFocus) {
                    setCurrentET((EditText) v);
                    onTextEditorGotFocus();
                } else {
                    onViewReleaseFocus(v);
                }
            };
        }
        return mOnFocusChangeListener;
    }

    /**
     * textedit获取焦点之后，可能需要添加遮盖层
     */
    protected void onTextEditorGotFocus() {

    }

    protected void onViewReleaseFocus(View view) {

    }

    protected void setCurrentET(EditText editText) {
        mETCurrent = editText;
    }

    public void clearFocus() {
        if (mETCurrent != null) {
            SystemUtil.hideKeyboard(mETCurrent);
        }

        mETCurrent = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        clearFocus();
    }

}
