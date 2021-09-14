package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.RechargeItemVH;
import com.ysarch.vmall.common.adapter.viewholder.WalletLogItemVH;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * 充值明细
 * Created by fysong on 2020/10/18
 **/
public class RechargeHistoryAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;

    public RechargeHistoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    public void refreshData(List<RechargeItemBean> rechargeItemBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(rechargeItemBeans)) {
            addDatas(rechargeItemBeans);
        }
        notifyDataSetChanged();
    }

    public void appendData(List<RechargeItemBean> rechargeItemBeans) {
        if (CollectionUtils.isNotEmpty(rechargeItemBeans)) {
            addDatas(rechargeItemBeans);
            notifyItemRangeInserted(getItemCount() - rechargeItemBeans.size(), rechargeItemBeans.size());
        }
    }

    private void addDatas(List<RechargeItemBean> rechargeItemBeans) {
        for (int i = 0; i < rechargeItemBeans.size(); i++) {
            mMixData.add(new ItemDataWrapper(1, rechargeItemBeans.get(i)));
        }
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RechargeItemVH(mInflater.inflate(RechargeItemVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
