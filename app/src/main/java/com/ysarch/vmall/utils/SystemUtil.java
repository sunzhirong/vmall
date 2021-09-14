package com.ysarch.vmall.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ysarch.vmall.R;

import androidx.appcompat.app.ActionBar;


/**
 * Created by fys on 2016/11/1.
 */

public class SystemUtil {


    /**
     * 隐藏键盘
     */
    public static boolean hideKeyboard(View view) {
        Log.e("clicktest", "hideKeyboard");
        return hideKeyboard(view, true);
    }

    /**
     * 隐藏键盘
     *
     * @param view
     * @param clearFocus
     */
    public static boolean hideKeyboard(View view, boolean clearFocus) {
        if (clearFocus && view.hasFocus()) {
            view.clearFocus();
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    public static void showKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }


    @SuppressLint("RestrictedApi")
    public static void showSupportActionBar(Context context, boolean barExitWhileFullScreen) {
        if (barExitWhileFullScreen && ActivityUtil.getAppCompActivity(context) != null) {
            ActionBar ab = ActivityUtil.getAppCompActivity(context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.show();
            }
        }
        if (barExitWhileFullScreen) {
            ActivityUtil.getWindow(context).clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @SuppressLint("RestrictedApi")
    public static void hideSupportActionBar(Context context, boolean barExitWhileFullScreen) {
        if (barExitWhileFullScreen && ActivityUtil.getAppCompActivity(context) != null) {
            ActionBar ab = ActivityUtil.getAppCompActivity(context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.hide();
            }
        }
        if (barExitWhileFullScreen) {
            ActivityUtil.getWindow(context).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    //系统剪贴板-获取:
    public static String getCopy(Context context) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 返回数据
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0 && clipData.getItemAt(0).getText() != null) {
            return clipData.getItemAt(0).getText().toString();
        }
        return null;
    }


    /**
     * 清空剪贴板内容
     */
    public static void clearClipboard(Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public static boolean copyText(Context context, String copyStr) {
//        try {
//            //获取剪贴板管理器
//            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//            // 创建普通字符型ClipData
//            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
//            // 将ClipData内容放到系统剪贴板里。
//            cm.setPrimaryClip(mClipData);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//
//    }

    public static void copy(Context context, String msg) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(msg);
        if (cm.getText().equals(msg)) {
            Toast.makeText(context, R.string.tip_copy_succ, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.tip_copy_failed, Toast.LENGTH_SHORT).show();
        }
    }

}

