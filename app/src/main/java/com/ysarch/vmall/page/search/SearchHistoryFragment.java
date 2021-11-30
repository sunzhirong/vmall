package com.ysarch.vmall.page.search;

import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.SearchHistoryAdapter;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.page.search.presenter.SearchHistoryPresenter;
import com.yslibrary.event.hailer.FunctionsManager;
import com.yslibrary.utils.CollectionUtils;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchHistoryFragment extends BaseFragment<SearchHistoryPresenter> {

    public SearchHistoryAdapter mAdapter;
    @BindView(R.id.rcy_search_history)
    RecyclerView mRecyclerView;

    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        List<String> keywords = getPresenter().queryHistoryKeyword();
        Collections.reverse(keywords);
        if (CollectionUtils.isNotEmpty(keywords))
            mAdapter.refreshData(keywords);
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new SearchHistoryAdapter(getContext());
            mAdapter.setCallback((position, keyword) -> {
                getPresenter().deleteKeyword(keyword);
                mAdapter.deleteData(position);
            }).setOnItemClickListener((position, data) -> {
                FunctionsManager.getInstance().invokeFunction(HailerFunctionDef.SEARCH_KEYWORD, data);
            });
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_history;
    }

    @Override
    public SearchHistoryPresenter newPresenter() {
        return new SearchHistoryPresenter();
    }


    @OnClick(R.id.iv_clear_search_history)
    void onViewClick() {
        getPresenter().clearKeywords();
    }

    @Override
    protected String getPageName() {
        return "搜索页";
    }

}
