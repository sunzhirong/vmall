package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;
import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.DiscoverBannerVH;
import com.ysarch.vmall.common.adapter.viewholder.DiscoverContentEndVH;
import com.ysarch.vmall.common.adapter.viewholder.DiscoverContentMiddleVH;
import com.ysarch.vmall.common.adapter.viewholder.DiscoverTimeVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;

/**
 * Created by fysong on 12/09/2020
 **/
public class DiscoverRcyAdapter extends AbsFRecyclerAdapter {
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_CONTENT_MIDDLE = 2;
    private static final int TYPE_CONTENT_END = 3;
    private static final int TYPE_TIME = 4;

    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;
    private Context mContext;

    public DiscoverRcyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = BeeGlide.with(context);
        mContext = context;

        for (int i = 0; i < 4; i++) {
            mMixData.add(new ItemDataWrapper(TYPE_TIME,1));
            mMixData.add(new ItemDataWrapper(TYPE_BANNER,1));
            mMixData.add(new ItemDataWrapper(TYPE_CONTENT_MIDDLE,1));
            mMixData.add(new ItemDataWrapper(TYPE_CONTENT_MIDDLE,1));
            mMixData.add(new ItemDataWrapper(TYPE_CONTENT_MIDDLE,1));
            mMixData.add(new ItemDataWrapper(TYPE_CONTENT_END,1));
        }
    }



    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsViewHolder holder = null;
        switch (viewType) {
            case TYPE_BANNER:
                holder = new DiscoverBannerVH(mInflater.inflate(DiscoverBannerVH.getLayoutRes(), parent, false), mBeeGlide);
                break;
            case TYPE_CONTENT_MIDDLE:
                holder = new DiscoverContentMiddleVH(mInflater.inflate(DiscoverContentMiddleVH.getLayoutRes(), parent, false), mBeeGlide);
                break;
            case TYPE_CONTENT_END:
                holder = new DiscoverContentEndVH(mInflater.inflate(DiscoverContentEndVH.getLayoutRes(), parent, false), mBeeGlide);
                break;
            case TYPE_TIME:
                holder = new DiscoverTimeVH(DiscoverTimeVH.createItemView(mContext));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
