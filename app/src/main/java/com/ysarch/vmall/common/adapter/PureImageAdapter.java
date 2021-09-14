package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.PureImageVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 10/10/2020
 **/
public class PureImageAdapter extends AbsRVAdapter {
    private Context mContext;
    private BeeGlide mBeeGlide;

    public PureImageAdapter(Context context) {
        mContext = context;
        mBeeGlide = BeeGlide.with(context);
    }


    public void refreshData(List<String> images) {
        mMixData.clear();
        if(CollectionUtils.isNotEmpty(images)){
            for (int i = 0; i < images.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, images.get(i)));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PureImageVH(PureImageVH.createItemView(mContext), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
