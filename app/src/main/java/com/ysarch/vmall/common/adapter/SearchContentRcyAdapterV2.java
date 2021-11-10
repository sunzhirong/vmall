package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CateHomeItemVH;
import com.ysarch.vmall.common.adapter.viewholder.GoodsItemVHV2;
import com.ysarch.vmall.common.adapter.viewholder.HomeBannerVH;
import com.ysarch.vmall.common.adapter.viewholder.HomeCatesGridVH;
import com.ysarch.vmall.common.adapter.viewholder.RecGoodsHeader;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.HomeBannerBean;
import com.ysarch.vmall.domain.bean.HomeRecommendBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fysong on 09/09/2020
 **/
public class SearchContentRcyAdapterV2 extends AbsFRecyclerAdapter {


    public static final int TYPE_GOODS = 3;

    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public SearchContentRcyAdapterV2(Context context) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = BeeGlide.with(context);

    }


    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    /**
     * 分类数据
     *
     * @param goodsItemBeans
     */
    public void refreshGoods(List<GoodsItemBeanV2> goodsItemBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 分类分页数据
     *
     * @param goodsItemBeans
     */
    public void appendGoods(List<GoodsItemBeanV2> goodsItemBeans) {
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i)).setOnItemClickListener(mOnItemClickListener));
            }
            notifyItemRangeInserted(getItemCount() - goodsItemBeans.size(), goodsItemBeans.size());
        }
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsItemVHV2(mInflater.inflate(GoodsItemVHV2.getLayoutRes(), parent, false), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
