package com.ysarch.vmall.page.search;

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
public class SearchFragmentAdapter extends DynamicFragmentAdapter {
    private String mKeyword;

    public SearchFragmentAdapter(Context context, FragmentManager fm, List<FragmentPagerItem> fragmentItems) {
        super(context, fm, fragmentItems);
    }

    public SearchFragmentAdapter(Context mContext, FragmentManager fm, List<FragmentPagerItem> fragmentItems, boolean recyclable) {
        super(mContext, fm, fragmentItems, recyclable);
    }

    public void setKeyword(String value){
        mKeyword = value;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentPagerItem fragmentPagerItem =  mFragmentItems.get(position);
        fragmentPagerItem.getArgs().putString(BundleKey.ARG_KEYWORD, mKeyword);
        return fragmentPagerItem.instantiate(mContext, position);
    }
}
