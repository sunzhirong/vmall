package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CartGoodsVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CartGoodsBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 10/09/2020
 **/
public class CartGoodsAdapter extends AbsRVAdapter {


    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;

    private CartGoodsVH.Callback mCallback;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCallback(CartGoodsVH.Callback callback) {
        mCallback = callback;
    }

    public CartGoodsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = BeeGlide.with(context);
    }

    public void refreshData(List<CartGoodsBean> cartGoodsBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(cartGoodsBeans)) {

            for (int i = 0; i < cartGoodsBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, cartGoodsBeans.get(i), mCallback)
                .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    public void deleteCart(int position) {
        if(position >= 0 && position < mMixData.size()){
            mMixData.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * @param position
     * @param cartGoodsBean
     */
    public void refreshCertainCartGoods(int position, CartGoodsBean cartGoodsBean) {
        if(position >= 0 && position < mMixData.size()){
            CartGoodsBean cartGoodsBean1 = (CartGoodsBean) mMixData.get(position).getData();
            if(cartGoodsBean1.getId() == cartGoodsBean.getId()){
                notifyItemChanged(position);
                return;
            }
        }

        for (int i = 0; i < mMixData.size(); i++) {
            CartGoodsBean cartGoodsBean1 = (CartGoodsBean) mMixData.get(position).getData();
            if(cartGoodsBean1.getId() == cartGoodsBean.getId()){
                notifyItemChanged(i);
            }
        }
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartGoodsVH(mInflater.inflate(CartGoodsVH.getLayoutRes(), parent, false), mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        ((CartGoodsVH) holder).showLine(position != getItemCount() - 1);
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
