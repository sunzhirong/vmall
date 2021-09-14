package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CateLevel2VH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 11/09/2020
 **/
public class CatesLevel2Adapter extends AbsRVAdapter {



    private BeeGlide mBeeGlide;
    private LayoutInflater mInflater;
    private Context mContext;

    private ItemDataWrapper.OnItemClickListener mClickListener;

    public CatesLevel2Adapter(Context context) {
        mContext = context;
        mBeeGlide = BeeGlide.with(context);
        mInflater = LayoutInflater.from(context);
    }

    public void setClickListener(ItemDataWrapper.OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void refreshData(List<CateLevelBean> cateLevelBeans,int pos) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(cateLevelBeans)) {
//            for (int i = 0; i < cateLevelBeans.size(); i++) {
//                mMixData.add(new ItemDataWrapper(1, cateLevelBeans.get(i)));
//            }
            if(cateLevelBeans.size()>=pos){
                mMixData.add(new ItemDataWrapper(1, cateLevelBeans.get(pos)));
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CateLevel2VH(mInflater.inflate(CateLevel2VH.getLayoutRes(), parent, false), mContext,
                mBeeGlide, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
