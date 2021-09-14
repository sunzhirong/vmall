package com.ysarch.vmall.common.adapter.viewholder;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CatesLevel3Adapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.utils.SizeUtils;

/**
 * Created by fysong on 11/09/2020
 **/
public class CateLevel2VH extends AbsViewHolder {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private BeeGlide mBeeGlide;
    private CatesLevel3Adapter mAdapter;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;


    public CateLevel2VH(View itemView, Context context, BeeGlide beeGlide,
                        ItemDataWrapper.OnItemClickListener onItemClickListener) {
        super(itemView);
        mRecyclerView = (RecyclerView) itemView;
        mContext = context;
        mBeeGlide = beeGlide;
        mOnItemClickListener = onItemClickListener;
    }

    public static int getLayoutRes() {
        return R.layout.item_pure_recyclerview;
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new CatesLevel3Adapter(mContext, mBeeGlide);
            mAdapter.setOnItemClickListener(mOnItemClickListener);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = mAdapter.getItemViewType(position);
                    if(type == CatesLevel3Adapter.TYPE_CATE_LEVEl2_HEADER || type == CatesLevel3Adapter.TYPE_CATE_LEVEl3_HEADER)
                        return 3;
                    return 1;
                }
            });
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


    @Override
    public void onBindData(int position, Object data, Object callback) {
        initAdapter();
        mAdapter.refreshData((CateLevelBean) data);
    }
}
