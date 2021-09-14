package com.ysarch.uibase.recyclerview;

/**
 * Created by fysong on 02/09/2020
 **/
public abstract class AbsFRecyclerAdapter extends AbsRVAdapter {

    private FRecyclerAdapter mFRecyclerAdapter;

    public int getHeaderViewsSize() {
        if (mFRecyclerAdapter != null) {
            return mFRecyclerAdapter.mHeaderViews.size();
        }

        return 0;
    }

    protected int getFooterViewsSize() {
        if (mFRecyclerAdapter != null) {
            return mFRecyclerAdapter.mFooterViews.size();
        }
        return 0;
    }

    public void setFRecyclerAdapter(FRecyclerAdapter FRecyclerAdapter) {
        mFRecyclerAdapter = FRecyclerAdapter;
    }
}
