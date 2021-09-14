package com.ysarch.vmall.page.goods;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ysarch.uibase.viewpager.DynamicFragmentAdapter;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.vmall.common.adapter.viewholder.MultiSkuVH;
import com.ysarch.vmall.component.dialog.multisku.MultiSkuFragment;
import com.ysarch.vmall.domain.constant.BundleKey;

import java.util.List;

/**
 * Created by fysong on 2019-10-27
 **/
public class MultiSkuPageFragmentAdapter extends DynamicFragmentAdapter {
    private MultiSkuVH.Callback mCallback;

    public void setCallback(MultiSkuVH.Callback callback) {
        mCallback = callback;
    }

    public MultiSkuPageFragmentAdapter(Context context, FragmentManager fm, List<FragmentPagerItem> fragmentItems) {
        super(context, fm, fragmentItems);
    }

    public MultiSkuPageFragmentAdapter(Context mContext, FragmentManager fm, List<FragmentPagerItem> fragmentItems, boolean recyclable) {
        super(mContext, fm, fragmentItems, recyclable);
    }

    @Override
    public Fragment getItem(int position) {
        FragmentPagerItem fragmentPagerItem =  mFragmentItems.get(position);
        MultiSkuFragment fragment = (MultiSkuFragment) fragmentPagerItem.instantiate(mContext, position);
        fragment.setCallback(mCallback);
        return fragment;
    }
}
