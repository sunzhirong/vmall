package com.ysarch.vmall.common.adapter.viewholder;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.uibase.recyclerview.itemDecoration.GridLayoutDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.HomeCatesAdapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.HomeRecommendBean;
import com.ysarch.vmall.utils.SizeUtils;

import java.util.List;

/**
 * 首页分页分类
 * Created by fysong on 19/09/2020
 **/
public class HomeCatesGridVH extends AbsViewHolder {
    private Context mContext;
    private HomeCatesAdapter mHomeCatesAdapter;
    private BeeGlide mBeeGlide;
    private RecyclerView mRecyclerView;

    private List<HomeRecommendBean> mCateLevelBeans;

    private CateHomeItemVH.Callback mCallback;

    public HomeCatesGridVH(View itemView, Context context, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        mContext = context;
        mRecyclerView = (RecyclerView) itemView;
    }

    public static int getLayoutRes() {
        return R.layout.item_cates_grid;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCateLevelBeans = (List<HomeRecommendBean>) data;
        mCallback = (CateHomeItemVH.Callback) callback;
        initAdapter();
        mHomeCatesAdapter.refreshData(mCateLevelBeans);
    }

    private void initAdapter() {
        if (mHomeCatesAdapter == null) {
            mHomeCatesAdapter = new HomeCatesAdapter(mContext, mBeeGlide);
            mHomeCatesAdapter.setCallback(mCallback);

            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
            mRecyclerView.setLayoutManager(layoutManager);
            int gapH = SizeUtils.dp2px(35);
            int gapV = SizeUtils.dp2px(10);
            GridLayoutDecoration layoutDecoration = new GridLayoutDecoration(gapH, gapV, 4);
            mRecyclerView.addItemDecoration(layoutDecoration);
            mRecyclerView.setAdapter(mHomeCatesAdapter);
        }
    }
}
