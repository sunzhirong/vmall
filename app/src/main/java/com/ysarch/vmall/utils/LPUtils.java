package com.ysarch.vmall.utils;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.DimenRes;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LPUtils {

    public static final int FILL = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;

    public static FrameLayout.LayoutParams getFrameLayoutParams(int width, int height) {
        return new FrameLayout.LayoutParams(width, height);
    }

    public static FrameLayout.LayoutParams getFrameLayoutParamsRes(@DimenRes int widthRes, @DimenRes int heightRes) {
        return new FrameLayout.LayoutParams(ResUtils.getDimeI(widthRes), ResUtils.getDimeI(heightRes));
    }

    public static LinearLayout.LayoutParams getLinearLayoutParams(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    public static LinearLayout.LayoutParams getLinearLayoutParamsRes(@DimenRes int widthRes, @DimenRes int heightRes) {
        return new LinearLayout.LayoutParams(ResUtils.getDimeI(widthRes), ResUtils.getDimeI(heightRes));
    }

    public static ViewGroup.LayoutParams getViewGroupLayoutParams(int width, int height) {
        return new ViewGroup.LayoutParams(width, height);
    }

    public static ViewGroup.LayoutParams getViewGroupLayoutParamsRes(@DimenRes int widthRes, @DimenRes int heightRes) {
        return new ViewGroup.LayoutParams(ResUtils.getDimeI(widthRes), ResUtils.getDimeI(heightRes));
    }

    public static ViewGroup.MarginLayoutParams getViewGroupMarginLayoutParams(int width, int height) {
        return new ViewGroup.MarginLayoutParams(width, height);
    }

    public static ViewGroup.MarginLayoutParams getViewGroupMarginLayoutParamsRes(@DimenRes int widthRes, @DimenRes int heightRes) {
        return new ViewGroup.MarginLayoutParams(ResUtils.getDimeI(widthRes), ResUtils.getDimeI(heightRes));
    }

    public static ConstraintLayout.LayoutParams getConstraintLayoutParams(int width, int height) {
        return new ConstraintLayout.LayoutParams(width, height);
    }

    public static ConstraintLayout.LayoutParams getConstraintLayoutParamsRes(@DimenRes int widthRes, @DimenRes int heightRes) {
        return new ConstraintLayout.LayoutParams(ResUtils.getDimeI(widthRes), ResUtils.getDimeI(heightRes));
    }
}
