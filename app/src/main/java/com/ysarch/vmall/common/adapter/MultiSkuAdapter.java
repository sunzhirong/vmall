package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.MultiSkuVH;
import com.ysarch.vmall.domain.local.MultiSkuEntity;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 12/10/2020
 **/
public class MultiSkuAdapter extends AbsRVAdapter {

    private LayoutInflater mInflater;

    private MultiSkuVH.Callback mCallback;

    public MultiSkuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setCallback(MultiSkuVH.Callback callback) {
        mCallback = callback;
    }

    public void refreshData(List<MultiSkuEntity> multiSkuEntities) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(multiSkuEntities)) {
            for (int i = 0; i < multiSkuEntities.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, multiSkuEntities.get(i), mCallback));
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MultiSkuVH(mInflater.inflate(MultiSkuVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
