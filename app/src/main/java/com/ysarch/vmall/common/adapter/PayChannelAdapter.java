package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.PayChannelItemVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.PayChannelBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import androidx.annotation.NonNull;

public class PayChannelAdapter extends AbsRVAdapter {


    private  BeeGlide mBeeGlide;
    private LayoutInflater mInflater;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public PayChannelAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = BeeGlide.with(context);
    }

    public void refreshData(List<PayChannelBean> payChannelBeans,int selectPos) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(payChannelBeans)) {
            for (int i = 0; i < payChannelBeans.size(); i++) {
                PayChannelBean payChannelBean = payChannelBeans.get(i);
                if(selectPos != -1) {
                    payChannelBean.setSelect(i==selectPos);
                }else {
                    payChannelBean.setSelect(false);
                }
                mMixData.add(new ItemDataWrapper(1, payChannelBean)
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayChannelItemVH(mInflater.inflate(PayChannelItemVH.getLayoutRes(), parent, false),mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }

}
