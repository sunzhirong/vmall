package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.SkuItemVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by fysong on 24/09/2020
 **/
public class SkuFlowAdapter extends AbsRVAdapter {
    private  BeeGlide mBeeGlide;
    private LayoutInflater mInflater;
    private Callback mCallback;
    private int mPosition;
    private int mLastSelPosition = -1;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener = (position, data) -> {
        if (mLastSelPosition != position) {
            if (mLastSelPosition != -1) {
                ((LocalSkuEntity) mMixData.get(mLastSelPosition).getData()).setSelected(false);
                notifyItemChanged(mLastSelPosition);
            }
            mLastSelPosition = position;
            LocalSkuEntity entity = ((LocalSkuEntity) mMixData.get(mLastSelPosition).getData());
            entity.setSelected(true);
            notifyItemChanged(mLastSelPosition);


            if (mCallback != null) {
                mCallback.onSkuChanged(mPosition, entity,mLastSelPosition);
            }
        }
    };
    public SkuFlowAdapter(int position, Context context) {
        mInflater = LayoutInflater.from(context);
        mPosition = position;
        mBeeGlide = BeeGlide.with(context);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void refreshData(List<LocalSkuEntity> localSkuEntities) {
        if (CollectionUtils.isNotEmpty(localSkuEntities)) {
            mMixData.clear();
            for (int i = 0; i < localSkuEntities.size(); i++) {
                if (localSkuEntities.get(i).isSelected()) {
                    mLastSelPosition = i;
                }
                mMixData.add(new ItemDataWrapper(1, localSkuEntities.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }

            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkuItemVH(mInflater.inflate(SkuItemVH.getLayoutRes(), parent, false),mBeeGlide);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }

    public LocalSkuEntity getSelectSkuCode() {
        if (mLastSelPosition != -1) {
            return (LocalSkuEntity) mMixData.get(mLastSelPosition).getData();
        }
        return null;
    }


    public interface Callback {
        void onSkuChanged(int position, LocalSkuEntity entity,int selectPosition);
    }

}
