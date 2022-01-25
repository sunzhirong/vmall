package com.ysarch.vmall.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author wufei
 * @version v1.0
 */
public class SizeUtils {

    private static int sScreenWidth = -1;
    private static int sScreenHeight = -1;

    private static Application sApp;
    private static Resources sResources;

    public static void init(Application application) {
        sApp = application;
        sResources = application.getResources();
        getStatusHeight(application);
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        final float scale = sResources.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue) {
        final float scale = sResources.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = sResources.getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = sResources.getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽度px
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth() {
        if (sScreenWidth == -1) {
            WindowManager windowManager = (WindowManager) sApp.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
            windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
            sScreenWidth = outMetrics.widthPixels;
        }
        return sScreenWidth;
    }

    /**
     * <pre>
     * 获取屏幕的高度px
     * 从状态栏顶部到导航栏顶部（囊括状态栏但不包括导航栏），（部分手机没有导航栏）
     *</pre>
     * @return 屏幕高px
     */
    public static int getScreenHeight() {
        if (sScreenHeight == -1) {
            WindowManager windowManager = (WindowManager) sApp.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            sScreenHeight = outMetrics.heightPixels;
        }
        return sScreenHeight;
    }

    /**
     * 考虑屏幕方向，获取屏幕的宽
     *
     * @return
     */
    public static int getWidth() {
        if (sResources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return getScreenWidth();
        } else {
            return getScreenHeight();
        }
    }

    /**
     * 考虑平米的方向，获取屏幕的搞
     *
     * @return
     */
    public static int getHeight() {
        if (sResources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return getScreenHeight();
        } else {
            return getScreenWidth();
        }
    }

    /** 获得状态栏的高度 */
    public static int getStatusHeight(Context context) {
//        int statusHeight = -1;
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
//            statusHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusHeight;


        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
