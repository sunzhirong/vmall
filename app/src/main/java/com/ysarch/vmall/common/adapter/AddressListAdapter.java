package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.AddressItemVH;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressListAdapter extends AbsRVAdapter {
    private LayoutInflater mInflater;

    private AddressItemVH.Callback mCallback;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;
    private AddressItemBean mSelAddressItemBean;
    private long mSelAddressId;
    private int mMode = Constants.MODE_ADDRESS_EDIT;

    public AddressListAdapter(Context context) {
        this(context, null);
    }

    public AddressListAdapter(Context context, AddressItemBean addressItemBean) {
        mInflater = LayoutInflater.from(context);
        mSelAddressItemBean = addressItemBean;
        if (addressItemBean != null) {
            mSelAddressId = addressItemBean.getId();
        }
    }

    public void setMode(int mode) {
        mMode = mode;
    }

    public void setSelAddressItemBean(AddressItemBean selAddressItemBean) {
        mSelAddressItemBean = selAddressItemBean;
        if (selAddressItemBean != null) {
            mSelAddressId = selAddressItemBean.getId();
        }
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCallback(AddressItemVH.Callback callback) {
        mCallback = callback;
    }

    /**
     * 刷新数据
     *
     * @param addressItemBeans
     */
    public void refreshData(List<AddressItemBean> addressItemBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(addressItemBeans)) {
            for (int i = 0; i < addressItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(1, addressItemBeans.get(i), mCallback)
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 删除一项地址
     *
     * @param addressItemBean
     */
    public void deleteAddress(AddressItemBean addressItemBean) {
        for (int i = 0; i < mMixData.size(); i++) {
            AddressItemBean itemBean = (AddressItemBean) mMixData.get(i).getData();
            if (itemBean.getId() == addressItemBean.getId()) {
                mMixData.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    /**
     * 默认地址更新
     *
     * @param position
     * @param addressItemBean
     */
    public void changeDefaultAddress(int position, AddressItemBean addressItemBean) {
        if (addressItemBean.getDefaultStatus() == 0) {
            for (int i = 0; i < mMixData.size(); i++) {
                AddressItemBean itemBean = (AddressItemBean) mMixData.get(i).getData();
                if (itemBean.getId() == addressItemBean.getId()) {
                    notifyItemChanged(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < mMixData.size(); i++) {
                AddressItemBean itemBean = (AddressItemBean) mMixData.get(i).getData();
                if (itemBean.getId() != addressItemBean.getId() && itemBean.getDefaultStatus() == 1) {
                    itemBean.setDefaultStatus(0);
                    break;
                }
            }
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressItemVH(mInflater.inflate(AddressItemVH.getLayoutRes(), parent, false),
                mMode, mSelAddressId);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
