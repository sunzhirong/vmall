package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CouponItemVH;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponListAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;

    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public CouponListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void refreshTestData() {
        mMixData.clear();
        for (int i = 0; i < 6; i++) {
            mMixData.add(new ItemDataWrapper(1, ""));
        }

        notifyDataSetChanged();
    }

    public void refreshData(List<CouponBean> couponBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(couponBeans)) {
            for (int i = 0; i < couponBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, couponBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CouponItemVH(mInflater.inflate(CouponItemVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
