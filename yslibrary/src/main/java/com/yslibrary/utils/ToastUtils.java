package com.yslibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by fysong on 2020-02-14
 */
public class ToastUtils {
    private static Toast sToast;
    private static String sText;

    /**
     * @param context 上下文
     * @param text    如果和上一个toast相同，则取消上一个显示当前的，如果不相同，就自动排序显示
     */
    public static void showLongToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * @param context  上下文
     * @param stringId 如果和上一个toast相同，则取消上一个显示当前的，如果不相同，就自动排序显示
     */
    public static void showLongToast(Context context, int stringId) {
        String text;
        try {
            text = context.getString(stringId);
        } catch (Exception e) {
            e.printStackTrace();
            text = stringId + "";
        }
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * @param context  上下文
     * @param stringId 如果和上一个toast相同，则取消上一个显示当前的，如果不相同，就自动排序显示
     */
    public static void showShortToast(Context context, int stringId) {
        String text;
        try {
            text = context.getString(stringId);
        } catch (Exception e) {
            e.printStackTrace();
            text = stringId + "";
        }
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * @param context 上下文
     * @param text    如果和上一个toast相同，则取消上一个显示当前的，如果不相同，就自动排序显示
     */
    public static void showShortToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void hideToast(Context context) {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    /**
     * @param context  上下文
     * @param text     如果和上一个toast相同，则取消上一个显示当前的，如果不相同，就自动排序显示
     * @param duration see{@link Toast#LENGTH_LONG},{@link Toast#LENGTH_SHORT}
     */
    private static void showToast(Context context, String text, int duration) {
        if (context != null) {
            if (text == null) {
                text = "null";
            }
            if (sToast == null) {//当前没有toast
                sText = text;
                sToast = Toast.makeText(context.getApplicationContext(), sText, duration);
                sToast.setGravity(Gravity.BOTTOM, 0, 200);
                sToast.show();
            } else if (text.equals(sText)) {//当前toast相同内容
                sToast.setGravity(Gravity.BOTTOM, 0, 200);
                sToast.show();
            } else {//当前toast内容不相同
                sText = text;
                sToast.setText(text);
                sToast.setGravity(Gravity.BOTTOM, 0, 200);
                sToast.show();
            }
        }
    }

}
