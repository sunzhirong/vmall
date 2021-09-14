package com.ysarch.vmall.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * 跳转帮助类
 *
 * @author fysong
 */
public class NavHelper {

    public static void startActivity(Activity activity, Class<?> clz) {
        startActivity(activity, clz, null);
    }

    public static void startActivity(Activity activity, Class<?> clz, int requestCode) {
        startActivity(activity, clz, null, requestCode);
    }

    public static void startActivity(Activity activity, Class<?> clz, Bundle b) {
        startActivity(activity, clz, b, -1);
    }

    public static void startActivity(Activity activity, Class<?> clz, Bundle b, int requestCode) {
        Intent intent = new Intent(activity, clz);
        if (b != null) {
            intent.putExtras(b);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Fragment fragment, Class<?> clz) {
        startActivity(fragment, clz, null);
    }

    public static void startActivity(Fragment fragment, Class<?> clz, Bundle b) {
        startActivity(fragment, clz, b, -1);
    }

    public static void startActivity(Fragment fragment, Class<?> clz, Bundle b, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), clz);
        if (b != null) {
            intent.putExtras(b);
        }
        fragment.startActivityForResult(intent, requestCode);
    }
}
