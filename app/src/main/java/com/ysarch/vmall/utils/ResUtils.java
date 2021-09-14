package com.ysarch.vmall.utils;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.Random;

/**
 * 资源实用类
 *
 * @author Darcy https://yedaxia.github.io/
 * @version 2017/3/29.
 */

public final class ResUtils {

    private static Application sApp;
    private static Resources sResources;

    public static void init(Application application) {
        sApp = application;
        sResources = application.getResources();
    }

    /**
     * 根据id获取字符串
     *
     * @param resId 资源id
     * @return
     */
    public static String getString(@StringRes int resId) {
        return sResources.getString(resId);
    }

    /**
     * 根据id获取格式化填充的字符串
     *
     * @param resId
     * @param values
     * @return
     */
    public static String getString(@StringRes int resId, Object... values) {
        return String.format(sResources.getString(resId), values);
    }

    /**
     * 根据id获取颜色
     *
     * @param resId
     * @return
     */
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(sApp, resId);
    }

    /**
     * 根据id获取drawable
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return ContextCompat.getDrawable(sApp, resId);
        } else {
            // 拷贝出一个无状态的副本
            return ContextCompat.getDrawable(sApp, resId).mutate();
        }

    }

    public static Bitmap getBitmap(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(sResources, resId);
    }

    public static float getDimeF(@DimenRes int id) {
        return sResources.getDimension(id);
    }

    public static int getDimeI(@DimenRes int id) {
        return Math.round(sResources.getDimension(id));
    }

    public static CharSequence[] getTextArray(@ArrayRes int id) {
        return sResources.getTextArray(id);
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        return getAlphaColor(Color.rgb(r, g, b), 0.5F);
    }

    public static int getAlphaColor(int color, float ration) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb((int) (255 * ration), r, g, b);
    }

    public static int parseColor(String hex, @ColorInt int defaultColor) {
        if (TextUtils.isEmpty(hex)) {
            return defaultColor;
        }

        int color = defaultColor;
        try {
            color = Color.parseColor(hex);
        } catch (Exception e) {

        }

        return color;
    }
}
