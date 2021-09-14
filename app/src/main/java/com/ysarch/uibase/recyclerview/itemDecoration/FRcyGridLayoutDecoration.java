package com.ysarch.uibase.recyclerview.itemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;

/**
 * Created by fysong on 19/09/2020
 **/
public class FRcyGridLayoutDecoration extends RecyclerView.ItemDecoration {

    private final int mGapV;
    private final int mSpanCount;
    private final int mGapH;
    private final int mMarginH;
    private AbsFRecyclerAdapter mAdapter;

    private int mGridPlaceViewType = -1;
    private int mHeadCount = 0;

    public FRcyGridLayoutDecoration setHeadCount(int headCount) {
        mHeadCount = headCount;
        return this;
    }

    public FRcyGridLayoutDecoration(int gapH, int gapV, int marginH, int spanCount,
                                    @NonNull AbsFRecyclerAdapter adapter) {
        mGapH = gapH;
        mGapV = gapV;
        mSpanCount = spanCount;
        mAdapter = adapter;
        mMarginH = marginH;
    }

    public FRcyGridLayoutDecoration setGridPlaceViewType(int gridPlaceViewType) {
        mGridPlaceViewType = gridPlaceViewType;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (mAdapter != null && mAdapter.getHeaderViewsSize() <= position && mAdapter.getItemCount() > 0
                && position < mAdapter.getItemCount()) {
            if (mGridPlaceViewType != -1 &&
                    mAdapter.getItemViewType(position - mAdapter.getHeaderViewsSize()) != mGridPlaceViewType) {
                if (position > 0) {
                    outRect.top = mGapV;
                }
            } else {
                position = position - mHeadCount;
                int column = (position - mAdapter.getHeaderViewsSize()) % mSpanCount;
                outRect.left = column * mGapH / mSpanCount;
                if (column == 0) {
                    outRect.left = outRect.left + mMarginH;
                }

                if (position >= mSpanCount) {
                    outRect.top = mGapV; // item top
                }
            }

        }

    }

}
