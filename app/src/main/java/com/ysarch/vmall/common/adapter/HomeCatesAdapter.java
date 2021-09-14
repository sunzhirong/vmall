package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CateHomeItemVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.HomeRecommendBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 19/09/2020
 **/
public class HomeCatesAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;


    private String mLastDataString;

    private CateHomeItemVH.Callback mCallback;

    public void setCallback(CateHomeItemVH.Callback callback) {
        mCallback = callback;
    }

    public HomeCatesAdapter(Context context, BeeGlide beeGlide) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = beeGlide;
    }

    public void refreshData(List<HomeRecommendBean> cateLevelBeans) {
        if (CollectionUtils.isNotEmpty(cateLevelBeans) && (TextUtils.isEmpty(mLastDataString) ||
                !mLastDataString.equals(cateLevelBeans.toString()))) {
            mLastDataString = cateLevelBeans.toString();
            mMixData.clear();
            for (int i = 0; i < cateLevelBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, cateLevelBeans.get(i), mCallback));
            }
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CateHomeItemVH(mInflater.inflate(CateHomeItemVH.getLayoutRes(), parent,
                false), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }

    @Override
    public int getItemCount() {
        if(mMixData.size()>8){
            return 8;
        }
        return super.getItemCount();
    }
}
