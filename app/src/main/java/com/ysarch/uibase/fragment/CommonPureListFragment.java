package com.ysarch.uibase.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.recyclerview.FRecyclerView;
import com.ysarch.uibase.recyclerview.LoadMoreUIHandler;
import com.ysarch.uibase.recyclerview.OnRecyclerViewScrollListener;
import com.ysarch.uibase.recyclerview.SimpleLoadMoreFooter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.component.EmptyView;
import com.ysarch.vmall.utils.ResUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.IPresent;

/**
 * <pre>
 * 通用list页面基类，只需传入Adapter，和处理下游业务数据，ui等交互状态不需另外处理
 * 不需分页加载数据时，可重载{@link #isPagingData()},并返回false
 * </pre>
 * <p>
 * Created by fysong on 2020-04-17
 **/
public abstract class CommonPureListFragment<P extends IPresent> extends BaseFragment<P> {


    protected int mPage;
    protected RecyclerView.Adapter mAdapter;
    protected boolean isRecyclerViewInited;
    @BindView(R.id.rcy_common_pure_list)
    FRecyclerView mRecycleView;
    @BindView(R.id.srl_common_pure_list)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.v_empty_common_pure_list)
    EmptyView mEmptyView;
    @BindView(R.id.ly_container_content_common_pure_list)
    public LinearLayout mLinearLayout;
    protected boolean isRefresh = true;
    protected boolean isLoadingMore;

    private LoadMoreUIHandler mLoadMoreUIHandler;


    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public FRecyclerView getRecycleView() {
        return mRecycleView;
    }

    /**
     * 列表是否分页加载数据
     *
     * @return
     */
    protected boolean isPagingData() {
        return true;
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
//        mRootView.setBackgroundColor(ResUtils.getColor(R.color.bg_page_gray));
    }

    protected void initRecyclerView(RecyclerView.ItemDecoration itemDecoration) {
        if (isRecyclerViewInited) return;

        isRecyclerViewInited = true;

        mRecycleView.setLayoutManager(initLayoutManager());

        if (itemDecoration != null) {
            mRecycleView.addItemDecoration(itemDecoration);
        }

        mRecycleView.setAdapter(getAdapter());
        mRecycleView.setEmptyView(mEmptyView);

        mRefreshLayout.setOnRefreshListener(() -> {
            mEmptyView.setVisibility(View.GONE);

            refreshData();
            if (isPagingData()) {
                mLoadMoreUIHandler.onLoadFinish(true);
            }
        });

        if (isPagingData()) {
            SimpleLoadMoreFooter simpleLoadMoreFooter = new SimpleLoadMoreFooter(getContext());
            mRecycleView.addFooterView(simpleLoadMoreFooter);
            mLoadMoreUIHandler = simpleLoadMoreFooter;

            mRecycleView.addOnScrollListener(new OnRecyclerViewScrollListener(getLoadThreshold()) {
                @Override
                public void onLoadMore(RecyclerView recyclerView) {
                    if (mLoadMoreUIHandler != null && !isRefresh && !isLoadingMore && hasMore()) {
                        isLoadingMore = true;
                        mLoadMoreUIHandler.onLoading();
                        requestData(mPage + 1);
                    }
                }
            });
        }
    }


    protected int getLoadThreshold(){
        return 5;
    }

    protected RecyclerView.LayoutManager initLayoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }


    protected RecyclerView.Adapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        return mAdapter;
    }

    protected abstract RecyclerView.Adapter createAdapter();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_pure_list;
    }


    protected void refreshData() {
        isRefresh = true;
        isLoadingMore = false;
        requestData(1);
    }


    protected abstract void requestData(int page);


    /**
     * ui状态更改
     */
    public void resetUIStatus(int page, boolean loadSucc) {
        if(loadSucc)
            mPage = page;
        if (isRecyclerViewInited) {
            if (mPage <= 1) {
                mRecycleView.checkIfEmpty();
            }
        }
        if (isRefresh) {
            mRefreshLayout.setRefreshing(false);
            isRefresh = false;
            if(isPagingData()){
                mLoadMoreUIHandler.onLoadFinish(hasMore());
            }
        } else if (isPagingData()) {
            isLoadingMore = false;
            if (mLoadMoreUIHandler != null) {
                if (loadSucc) {
                    mLoadMoreUIHandler.onLoadFinish(hasMore());
                } else {
                    if (page == 1) {
                        mLoadMoreUIHandler.onLoadFinish(true);
                    } else {
                        mLoadMoreUIHandler.onLoadError();
                    }
                }
            }
        }
    }



    protected void setEmptyOpt(String label, EmptyView.Callback callback){
        mEmptyView.setOptLabel(label);
        mEmptyView.setOptCallback(callback);
    }


    @OnClick(R.id.v_empty_common_pure_list)
    void onViewClick(View view) {
        isRefresh = true;
        isLoadingMore = false;
        requestDataOnEmpty();
    }

    protected void requestDataOnEmpty(){
        refreshData();
    }


    protected boolean hasMore() {
        return true;
    }
}
