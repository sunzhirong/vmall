package com.ysarch.uibase.recyclerview;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fysong
 */
public abstract class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private int mLoadThreshold = 1;


    public OnRecyclerViewScrollListener() {

    }
    public OnRecyclerViewScrollListener(int loadThreshold) {
        mLoadThreshold = loadThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (canTriggerLoadMore(recyclerView)) {
            onLoadMore(recyclerView);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    }

    public void setLoadThreshold(int loadThreshold) {
        this.mLoadThreshold = loadThreshold < 1 ? 1 : loadThreshold;
    }

    public boolean canTriggerLoadMore(RecyclerView recyclerView) {
        // 当前只支持线性布局
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = linearLayoutManager.getItemCount();
        int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();

        return lastVisiblePosition + mLoadThreshold >= totalItemCount;
    }

    public abstract void onLoadMore(RecyclerView recyclerView);
}
