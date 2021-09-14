package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CartPromotionGoodsVH;
import com.ysarch.vmall.common.adapter.viewholder.GoodsPictureVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.local.LocalSkuEntity;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * 订单的商品adapter
 * Created by fysong on 16/09/2020
 **/
public class GoodsPictureRvAdapter extends AbsRVAdapter {

    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;
    private CartPromotionGoodsVH.Callback mCallback;

    public void setCallback(CartPromotionGoodsVH.Callback callback) {
        mCallback = callback;
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public GoodsPictureRvAdapter(Context context, BeeGlide beeGlide) {
        mBeeGlide = BeeGlide.with(context);
        mInflater = LayoutInflater.from(context);
    }


    public void requestData(List<LocalSkuEntity> list,int position) {
        mMixData.clear();
        for (int i = 0; i < list.size(); i++) {
            LocalSkuEntity localSkuEntity = list.get(i);
            localSkuEntity.setSelected(i==position);
            mMixData.add(new ItemDataWrapper(1,localSkuEntity ,mCallback)
            .setOnItemClickListener(mOnItemClickListener));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsPictureVH(mInflater.inflate(GoodsPictureVH.getLayoutRes(),
                parent, false), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return ((LocalSkuEntity) mMixData.get(position).getItemData()).getSkuBelongId().hashCode();
    }
}
