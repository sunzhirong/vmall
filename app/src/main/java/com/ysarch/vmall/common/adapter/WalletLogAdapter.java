package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.WalletLogItemVH;
import com.ysarch.vmall.domain.bean.WalletLogBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * 交易流水
 * Created by fysong on 2020/10/18
 **/
public class WalletLogAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;

    public WalletLogAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    public void refreshData(List<WalletLogBean> walletLogBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(walletLogBeans)) {
            addDatas(walletLogBeans);
        }
        notifyDataSetChanged();
    }

    public void appendData(List<WalletLogBean> walletLogBeans) {
        if (CollectionUtils.isNotEmpty(walletLogBeans)) {
            addDatas(walletLogBeans);
            notifyItemRangeInserted(getItemCount() - walletLogBeans.size(), walletLogBeans.size());
        }
    }

    private void addDatas(List<WalletLogBean> walletLogBeans) {
        for (int i = 0; i < walletLogBeans.size(); i++) {
            mMixData.add(new ItemDataWrapper(1, walletLogBeans.get(i)));
        }
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletLogItemVH(mInflater.inflate(WalletLogItemVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
