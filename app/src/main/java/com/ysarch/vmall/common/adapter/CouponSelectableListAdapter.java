package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CouponSelectableItemVH;
import com.ysarch.vmall.component.dialog.CouponDialog;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponSelectableListAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;
    private int mSelPosition = -1;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;
    private CouponBean mSelCouponBean;

    public CouponBean getSelCouponBean() {
        return mSelCouponBean;
    }

    private List<CouponBean> mCouponBeans = new ArrayList<>();

    public CouponSelectableListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setSelCouponBean(CouponBean selCouponBean) {
        if (selCouponBean == null)
            return;

        if (mSelCouponBean == null || mSelCouponBean.getId() != selCouponBean.getId()) {
            mSelCouponBean = selCouponBean;

            if (getItemCount() > 0) {
                for (int i = 0; i < mCouponBeans.size(); i++) {
                    CouponBean couponBean = mCouponBeans.get(i);
                    if (selCouponBean.isLocalSelected()) {
                        couponBean.setLocalSelected(couponBean.getId()==selCouponBean.getId());
                    } else if (couponBean.getId() == selCouponBean.getId()) {
                        couponBean.setLocalSelected(false);
                    }
                }

                notifyDataSetChanged();
            }
        }
    }

    private ItemDataWrapper.OnItemClickListener getOnItemClickListener() {
        if (mOnItemClickListener == null) {
            mOnItemClickListener = (position, data) -> {
                CouponBean couponBean = (CouponBean) data;
                couponBean.setLocalSelected(!couponBean.isLocalSelected());
                if (couponBean.isLocalSelected()) {
                    setSelCouponBean(couponBean);
                    if(mCallback!=null){
                        mCallback.onCouponOpt(couponBean);
                    }
                } else {
                    if (mSelCouponBean != null && mSelCouponBean.getId()==couponBean.getId()) {
                        mSelCouponBean = null;
                    }
                    notifyItemChanged(position);
                }
            };
        }
        return mOnItemClickListener;
    }


    public void refreshData(List<CouponBean> couponBeans) {
        mMixData.clear();
        mCouponBeans.clear();
        if (CollectionUtils.isNotEmpty(couponBeans)) {
            mCouponBeans.addAll(couponBeans);
            for (int i = 0; i < couponBeans.size(); i++) {
                CouponBean couponBean = couponBeans.get(i);
                if (mSelCouponBean != null && couponBean.getId()==mSelCouponBean.getId()) {
                    couponBean.setLocalSelected(true);
                }else {
                    couponBean.setLocalSelected(false);
                }

                mMixData.add(new ItemDataWrapper(1, couponBeans.get(i))
                        .setOnItemClickListener(getOnItemClickListener()));

            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CouponSelectableItemVH(mInflater.inflate(CouponSelectableItemVH.getLayoutRes(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }

    private CouponDialog.Callback mCallback;

    public void setCallback(CouponDialog.Callback callback) {
        mCallback = callback;
    }
}
