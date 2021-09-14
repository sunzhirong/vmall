package com.yslibrary.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Darcy https://yedaxia.github.io/
 * @version 2016/12/2
 */

public class ViewUtils {
    /**
     * Calculate the font height of a specific TextPaint
     *
     * @throws NullPointerException
     */
    public static float calculateFontHeight(TextPaint textPaint) {
        if (textPaint == null) {
            throw new NullPointerException("textPaint is null");
        }
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }


    /**
     * calculate the width of a string with a specific TextPaint
     *
     * @throws NullPointerException
     */
    public static int calculateStringWidth(Paint paint, String s) {
        if (paint == null || s == null) {
            throw new NullPointerException("paint or s is null");
        }
        int iRet = 0;
        int len = s.length();
        if (len > 0) {
            float[] widths = new float[len];
            paint.getTextWidths(s, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }


    /**
     * 设置View的背景
     */
    public static void setViewBackground(View v, Drawable background) {
        if (v == null || background == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(background);
        } else {
            v.setBackgroundDrawable(background);
        }
    }


    /**
     * parent是否包含child
     *
     * @throws NullPointerException
     */
    public static boolean containChild(ViewGroup parent, View child) {

        if (parent == null || child == null) {
            throw new NullPointerException("parent view group or child view is null");
        }

        if (parent == child) {
            return true;
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            if (parent.getChildAt(i) == child) {
                return true;
            }
        }

        return false;
    }


    /**
     * 滚动ListView到第一条
     */
    public static void scrollListViewToTop(final ListView listView) {
        if (listView != null && listView.getChildCount() > 0) {
            listView.smoothScrollToPositionFromTop(0, 0, 100);
            listView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listView.smoothScrollToPositionFromTop(0, 0, 0);
                    listView.setSelection(0);
                }
            }, 200);
        }
    }

    /**
     * 滚动RecyclerView到第一条
     *
     * @param recyclerView
     */
    public static void scrollRecyclerViewToTop(final RecyclerView recyclerView) {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(0);
                }
            }, 200);
        }
    }

    /**
     * 获取忽略两端空白的字符串
     */
    public static String trimText(TextView textView) {
        if (textView == null) {
            return null;
        }
        return textView.getText().toString().trim();
    }


    /**
     * 设置TextView左边的drawable
     */
    public static void setTextViewLeftDrawable(TextView tv, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(tv.getContext(), drawableId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
    }


    public static void setTextViewLeftDrawable(TextView tv, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        tv.setCompoundDrawables(drawable, null, null, null);
    }


    /**
     * 设置TextView左边的drawable
     */
    public static void setTextViewRightDrawable(TextView tv, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(tv.getContext(), drawableId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        tv.setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 清楚TextView的drawable
     */
    public static void clearTextViewDrawable(TextView tv) {
        tv.setCompoundDrawables(null, null, null, null);
    }


    /**
     * position位置是否在当前屏幕的listView显示
     */
    public static boolean isVisibleInListView(ListView listView, int position) {
        int headerCount = listView.getHeaderViewsCount();
        int footerCount = listView.getFooterViewsCount();
        int firstPos = listView.getFirstVisiblePosition();
        int lastPos = listView.getLastVisiblePosition();
        return firstPos <= position + headerCount &&
                position + footerCount <= lastPos;
    }


    /**
     * position位置是否在当前屏幕的recyclerview显示
     */
    public static boolean isVisibleInListView(LinearLayoutManager layoutManager, int position) {
        int fristPos = layoutManager.findFirstVisibleItemPosition();
        int lastPos = layoutManager.findLastVisibleItemPosition();

        return position >= fristPos && position <= lastPos;
    }


    public static int argb(int alpha, @ColorInt int color) {
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }


    public static int changeToOther(float ratio, int color, int other) {
        int red = color >> 16 & 0xff;
        red = (int) (red * (1 - ratio) + Color.red(other) * ratio);
        int green = color >> 8 & 0xff;
        green = (int) (green * (1 - ratio) + Color.green(other) * ratio);
        int blue = color & 0xff;
        blue = (int) (blue * (1 - ratio) + Color.blue(other) * ratio);
        return (255 << 24) | (red << 16) | (green << 8) | blue;
    }
}
