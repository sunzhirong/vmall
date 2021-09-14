package com.ysarch.vmall.page.splash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.context.VMallBuildConfig;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.main.MainActivity;
import com.ysarch.vmall.utils.NavHelper;

import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by fysong on 03/09/2020
 **/
public class SplashActivity extends XActivity<SplashPresenter> {

    private static final int STATUS_TIMEOUT = 1;
    private static final int STATUS_PERMISSION = 2;
    private static final int STATUS_TOKEN = 4;
    private static final long DELAY = 3000;
    protected final String[] PERMISSIONS_NEED = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储卡写入权限
            android.Manifest.permission.READ_EXTERNAL_STORAGE,//存储卡读取权限
            android.Manifest.permission.READ_PHONE_STATE,//读取手机状态权限
    };
    private int mStatus;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == STATUS_TIMEOUT
                    || msg.what == STATUS_PERMISSION
            ||msg.what == STATUS_TOKEN) {
                mStatus = mStatus | msg.what;

                checkStatus(msg.what);
            }
        }
    };

    @Override
    protected void onInjectView() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        super.onInjectView();
    }

    private void checkStatus(int curChangeStatus) {
        if ((mStatus == (STATUS_TIMEOUT | STATUS_PERMISSION | STATUS_TOKEN))) {
            finishPage();
        }
    }

    private void finishPage() {
        NavHelper.startActivity(this, MainActivity.class);
        finish();
    }

    @Override
    public void setUpStatusBar() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        if (VMallBuildConfig.isDebugBuild()) {
            VMallBuildConfig.setEnv(CacheHelper.getInt(CacheKeys.KEY_ENV_VALUE, VMallBuildConfig.CUR_ENV));
        }
        sendMsg(STATUS_TIMEOUT, DELAY);
        checkPermissions();

        if(UserInfoManager.isLogin()){
            getPresenter().refreshToken();
        } else {
            sendMsg(STATUS_TOKEN);
        }
    }

    private void sendMsg(int status) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(status);
        }
    }

    private void sendMsg(int status, long delay) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(status, delay);
        }
    }

    private void checkPermissions() {
        getRxPermissions().request(PERMISSIONS_NEED).subscribe(aBoolean -> {
            if (aBoolean) {
                sendMsg(STATUS_PERMISSION);
            } else {
                onPermissionsDenyConfirm();
            }
        });
    }

    private void onPermissionsDenyConfirm() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.label_app_not_use)
                .setMessage(R.string.label_reset_premissions)
                .setCancelable(false)
                .setPositiveButton(R.string.label_confirm, (dialog, which) -> checkPermissions())
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    finish();
                    System.exit(0);
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashPresenter newPresenter() {
        return new SplashPresenter();
    }

    @Override
    public Dialog showLoadingDialog() {
        return null;
    }

    @Override
    public Dialog showLoadingDialog(String loadingText, boolean isCancelable) {
        return null;
    }

    @Override
    public Dialog showLoadingDialog(boolean isCancelable) {
        return null;
    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showTs(String msg) {

    }

    public void afterResreshToken() {
        sendMsg(STATUS_TOKEN);
    }
}
