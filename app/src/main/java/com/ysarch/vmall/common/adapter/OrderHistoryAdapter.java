package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.OrderHistoryItemVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * 历史订单列表
 * Created by fysong on 16/09/2020
 **/
public class OrderHistoryAdapter extends AbsRVAdapter {

    private Context mContext;
    private BeeGlide mBeeGlide;

    private OrderHistoryItemVH.Callback mCallback;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public OrderHistoryAdapter(Context context) {
        mContext = context;
        mBeeGlide = BeeGlide.with(context);
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCallback(OrderHistoryItemVH.Callback callback) {
        mCallback = callback;
    }

    public void refreshData(List<OrderBean> orderBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(orderBeans)) {
            for (int i = 0; i < orderBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, orderBeans.get(i), mCallback)
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }

    public void appendData(List<OrderBean> orderBeans) {
        if (CollectionUtils.isNotEmpty(orderBeans)) {
            for (int i = 0; i < orderBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, orderBeans.get(i), mCallback)
                        .setOnItemClickListener(mOnItemClickListener));
            }
            notifyItemRangeInserted(getItemCount() - orderBeans.size(), orderBeans.size());
        }
    }

    public void updateOrder(int position, OrderBean orderBean) {
        if (position >= 0 && position < mMixData.size() &&
                ((OrderBean) mMixData.get(position).getData()).getId() == orderBean.getId()) {
            notifyItemChanged(position);
        } else {
            for (int i = 0; i < mMixData.size(); i++) {
                OrderBean orderBean1 = (OrderBean) mMixData.get(i).getData();
                if (orderBean.getId() == orderBean1.getId()) {
                    notifyItemChanged(i);
                    break;
                }
            }
        }

    }


    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryItemVH(OrderHistoryItemVH.createItemView(mContext), mContext, mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }

    public void removeOrder(int position, OrderBean orderBean) {
        if (position >= 0 && position < mMixData.size() &&
                ((OrderBean) mMixData.get(position).getData()).getId() == orderBean.getId()) {
            mMixData.remove(position);
            notifyItemRemoved(position);
        } else {
            for (int i = 0; i < mMixData.size(); i++) {
                OrderBean orderBean1 = (OrderBean) mMixData.get(i).getData();
                if (orderBean.getId() == orderBean1.getId()) {
                    mMixData.remove(i);
                    notifyItemRemoved(i);
                    break;
                }
            }
        }
    }
}
