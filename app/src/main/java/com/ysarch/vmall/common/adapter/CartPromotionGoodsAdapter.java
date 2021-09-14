package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CartPromotionGoodsVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;

import java.util.List;

/**
 * 订单的商品adapter
 * Created by fysong on 16/09/2020
 **/
public class CartPromotionGoodsAdapter extends AbsRVAdapter {

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

    public CartPromotionGoodsAdapter(Context context, BeeGlide beeGlide) {
        mBeeGlide = BeeGlide.with(context);
        mInflater = LayoutInflater.from(context);
    }


    public void requestData(List<GenerateOrderConfirmResult.CartPromotionItemListBean> cartPromotionItemListBeans) {
        mMixData.clear();
        for (int i = 0; i < cartPromotionItemListBeans.size(); i++) {
            mMixData.add(new ItemDataWrapper(1, cartPromotionItemListBeans.get(i),mCallback)
            .setOnItemClickListener(mOnItemClickListener));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartPromotionGoodsVH(mInflater.inflate(CartPromotionGoodsVH.getLayoutRes(),
                parent, false), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
