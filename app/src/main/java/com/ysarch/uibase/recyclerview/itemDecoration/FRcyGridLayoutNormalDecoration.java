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
public class FRcyGridLayoutNormalDecoration extends RecyclerView.ItemDecoration {

    private final int mSpanCount;
    private final int mGapH;
    private AbsFRecyclerAdapter mAdapter;

    private int mGridPlaceViewType = -1;
    private int mHeadCount = 0;

    public FRcyGridLayoutNormalDecoration setHeadCount(int headCount) {
        mHeadCount = headCount;
        return this;
    }

    public FRcyGridLayoutNormalDecoration(int gapH,  int spanCount,
                                          @NonNull AbsFRecyclerAdapter adapter) {
        mGapH = gapH;
        mSpanCount = spanCount;
        mAdapter = adapter;
    }

    public FRcyGridLayoutNormalDecoration setGridPlaceViewType(int gridPlaceViewType) {
        mGridPlaceViewType = gridPlaceViewType;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        int column = position % mSpanCount;
        if (column == 0) {
            outRect.left = outRect.left + mGapH;
            outRect.right = outRect.right + mGapH / 2;
        }else {
            outRect.right = outRect.right + mGapH;
            outRect.left = outRect.left + mGapH / 2;
        }
        if(position==0){
            outRect.top = outRect.top + mGapH ;
        }else {
            outRect.top = outRect.top + mGapH / 2;
        }
//        if(position == mAdapter.getItemCount()-1){
//            outRect.bottom = outRect.bottom + mGapH ;
//        }else {
//            outRect.bottom = outRect.bottom + mGapH / 2;
//        }


    }

}
