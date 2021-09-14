package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CateMenuVH;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * 分类左侧列表Adapter
 * Created by fysong on 11/09/2020
 **/
public class CateMenusAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;

    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;


    private int mSelPosition = -1;

    public CateMenusAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void refreshData(List<CateLevelBean> cateLevelBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(cateLevelBeans)) {
            for (int i = 0; i < cateLevelBeans.size(); i++) {
                if(i == 0)
                    cateLevelBeans.get(i).setSelected(true);
                mMixData.add(new ItemDataWrapper(1, cateLevelBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }
            mSelPosition = 0;
        }


        notifyDataSetChanged();
    }

    public void setSelPosition(int position){
        if( position < 0 || position >= getItemCount() || position == mSelPosition){
            return;
        }
        if(mSelPosition != -1){
            ((CateLevelBean)mMixData.get(mSelPosition).getData()).setSelected(false);
        }
        ((CateLevelBean)mMixData.get(position).getData()).setSelected(true);
        mSelPosition = position;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CateMenuVH(mInflater.inflate(CateMenuVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
