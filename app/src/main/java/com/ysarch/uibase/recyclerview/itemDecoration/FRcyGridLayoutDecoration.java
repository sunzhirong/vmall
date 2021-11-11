package com.ysarch.uibase.recyclerview.itemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;
import com.ysarch.vmall.utils.Log;

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
//                outRect.left = column * mGapH / mSpanCount;
//                if (column == 0) {
//                    outRect.left = outRect.left + mMarginH;
//                }
//
//                if (position >= mSpanCount) {
//                    outRect.top = mGapV; // item top
//                }
                if (column == 0) {
                    outRect.left = outRect.left + mGapH;
                    outRect.right = outRect.right + mGapH / 2;
                }else {
                    outRect.right = outRect.right + mGapH;
                    outRect.left = outRect.left + mGapH / 2;
                }
                outRect.top = outRect.top + mGapH;
//                if(position==mHeadCount){
//                    outRect.top = outRect.top + mGapH ;
//                }else {
//                    outRect.top = outRect.top + mGapH / 2;
//                }
//                if(position == mAdapter.getItemCount()-1){
//                    outRect.bottom = outRect.bottom + mGapH ;
//                }else {
//                    outRect.bottom = outRect.bottom + mGapH / 2;
//                }
            }

        }

        Log.e("getItemOffsets", JSON.toJSONString(outRect)+"position="+position);

    }

}
