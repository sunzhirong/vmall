package com.ysarch.uibase.recyclerview.itemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fysong on 2019-06-06.
 */
public class GridLayoutDecoration extends RecyclerView.ItemDecoration {

    private final int mGapV;
    private final int mSpanCount;
    private final int mGapH;

    public GridLayoutDecoration(int gapH, int gapV, int spanCount) {
        mGapH = gapH;
        mGapV = gapV;
        mSpanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        int column = position % mSpanCount;

        outRect.left = column * mGapH / mSpanCount;
        outRect.right = mGapH - (column + 1) * mGapH / mSpanCount;

        if (position >= mSpanCount) {
            outRect.top = mGapV; // item top
        }
    }
}
