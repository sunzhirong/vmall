package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.BankItemVH;
import com.ysarch.vmall.common.adapter.viewholder.CouponItemVH;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import androidx.annotation.NonNull;

public class RechargeBankAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;

    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public RechargeBankAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    public void refreshData(List<BankItemBean> bankItemBeans,int selectPos) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(bankItemBeans)) {
            for (int i = 0; i < bankItemBeans.size(); i++) {
                BankItemBean bankItemBean = bankItemBeans.get(i);
                if(selectPos != -1) {
                    bankItemBean.setSelect(i==selectPos);
                }else {
                    bankItemBean.setSelect(false);
                }
                mMixData.add(new ItemDataWrapper(1, bankItemBean)
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BankItemVH(mInflater.inflate(BankItemVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
