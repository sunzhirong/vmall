package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.MsgItemVH;
import com.ysarch.vmall.domain.bean.MsgBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 27/10/2020
 **/
public class MsgListAdapter extends AbsRVAdapter {

    private LayoutInflater mLayoutInflater;

    public MsgListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }


    public void refreshData(List<MsgBean> msgBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(msgBeans)) {
            for (int i = 0; i < msgBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, msgBeans.get(i)));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MsgItemVH(mLayoutInflater.inflate(MsgItemVH.getLayoutRes(), parent, false));
    }

}
