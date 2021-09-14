package com.ysarch.vmall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

/**
 * Created by fysong on 2020-07-08
 **/
public class ActivityUtil {
    /**
     * Get activity from context object
     *
     * @param context context
     * @return object of Activity or null if it is not Activity
     */
    public static Activity scanForActivity(Context context) {
        if (context == null) return null;

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }

    /**
     * Get AppCompatActivity from context
     *
     * @param context context
     * @return AppCompatActivity if it's not null
     */
    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) return null;
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    public static Window getWindow(Context context) {
        if (ActivityUtil.getAppCompActivity(context) != null) {
            return ActivityUtil.getAppCompActivity(context).getWindow();
        } else {
            return ActivityUtil.scanForActivity(context).getWindow();
        }
    }
}
