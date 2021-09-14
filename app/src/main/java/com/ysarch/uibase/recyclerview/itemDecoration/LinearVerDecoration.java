package com.ysarch.uibase.recyclerview.itemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * linearLayout 竖向布局间距修饰
 * Created by fysong on 2019-10-26
 **/
public class LinearVerDecoration extends RecyclerView.ItemDecoration {

    private int mMarginH = 0;
    private int mGapV = 0;
    private int mHeaderMargin = 0;
    private int mFooterMargin = 0;

    /**
     * @param marginHor    横向间距
     * @param gapVer       单元之间间距
     * @param headerMargin 列表头部间距
     * @param footerMargin 列表底部间距
     */
    public LinearVerDecoration(int marginHor, int gapVer, int headerMargin, int footerMargin) {
        this.mMarginH = marginHor;
        this.mGapV = gapVer;
        this.mHeaderMargin = headerMargin;
        this.mFooterMargin = footerMargin;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == 0) {
            outRect.set(mMarginH, mHeaderMargin, mMarginH, 0);
        } else {
            int marginBottom = parent.getAdapter() != null && position == parent.getAdapter().getItemCount()
                    ? mFooterMargin : 0;
            outRect.set(mMarginH, mGapV, mMarginH, marginBottom);
        }
    }
}
