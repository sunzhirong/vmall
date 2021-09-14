package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.KeywordItemVH;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchHistoryAdapter extends AbsFRecyclerAdapter {


    private KeywordItemVH.Callback mCallback;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    public SearchHistoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 关键词点击回调
     *
     * @param onItemClickListener
     * @return
     */
    public SearchHistoryAdapter setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }

    /**
     * 关键词删除回调
     *
     * @param callback
     * @return
     */
    public SearchHistoryAdapter setCallback(KeywordItemVH.Callback callback) {
        mCallback = callback;
        return this;
    }


    /**
     * 更新数据
     *
     * @param keywords
     */
    public void refreshData(List<String> keywords) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(keywords)) {
            for (int i = 0; i < keywords.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, keywords.get(i), mCallback)
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 删除关键字
     *
     * @param position
     */
    public void deleteData(int position) {
        if (position < 0 || position >= getItemCount())
            return;

        mMixData.remove(position);
        notifyItemRemoved(getHeaderViewsSize() + position);
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KeywordItemVH(mInflater.inflate(KeywordItemVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
