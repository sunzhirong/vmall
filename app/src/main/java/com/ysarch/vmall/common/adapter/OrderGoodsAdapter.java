package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.HistoryOrderGoodsVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderItemListBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * 订单的商品adapter
 * Created by fysong on 16/09/2020
 **/
public class OrderGoodsAdapter extends AbsRVAdapter {

    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public OrderGoodsAdapter(Context context, BeeGlide beeGlide) {
        mBeeGlide = BeeGlide.with(context);
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void requestData(List<OrderItemListBean> orderItemListBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(orderItemListBeans)) {
            for (int i = 0; i < orderItemListBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, orderItemListBeans.get(i))
                .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryOrderGoodsVH(mInflater.inflate(HistoryOrderGoodsVH.getLayoutRes(), parent, false), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
