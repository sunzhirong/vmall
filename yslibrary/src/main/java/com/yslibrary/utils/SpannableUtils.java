package com.yslibrary.utils;

import android.graphics.Point;
import androidx.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 处理文字点击、颜色高亮处理
 * Created by fysong on 2019-10-18
 **/
public class SpannableUtils {

    public static Spannable getClickadleSpannable(String content, ArrayList<Point> points,
                                                  SpannableClickCallback spannableClickCallback) {
        return getClickadleSpannable(content, points, spannableClickCallback, -1);
    }

    public static Spannable getClickadleSpannable(String content, ArrayList<Point> points, SpannableClickCallback spannableClickCallback,
                                                  int highlightColor) {
        if (!TextUtils.isEmpty(content)) {
            Iterator<Point> pointIterator = points.iterator();
            while (pointIterator.hasNext()) {
                Point point = pointIterator.next();
                if (point.x >= point.y || point.y > content.length()) {
                    pointIterator.remove();
                }
            }

            if (points.size() > 0) {
                Spannable spannable = new SpannableString(content);
                for (int i = 0; i < points.size(); i++) {
                    final int index = i;
                    Point point = points.get(i);
                    if (highlightColor != -1) {
                        spannable.setSpan(new ForegroundColorSpan(highlightColor), point.x, point.y, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    spannable.setSpan(new MultiClickableSpan(i, point, spannableClickCallback), point.x, point.y,
                            Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }

                return spannable;
            }
        }


        return null;

    }


    public interface SpannableClickCallback {
        void onSpannableClick(int markIndex, Point point);
    }

    public static class MultiClickableSpan extends ClickableSpan {

        private int mMarkIndex;
        private Point mPoint;
        private SpannableClickCallback mSpannableClickCallback;

        public MultiClickableSpan(int markIndex, Point point, SpannableClickCallback spannableClickCallback) {
            mMarkIndex = markIndex;
            mPoint = point;
            mSpannableClickCallback = spannableClickCallback;
        }

        @Override
        public void onClick(@NonNull View widget) {
            if (mSpannableClickCallback != null) {
                mSpannableClickCallback.onSpannableClick(mMarkIndex, mPoint);
            }
        }
    }

}
