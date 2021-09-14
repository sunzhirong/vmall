package com.ysarch.vmall.common.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ysarch.uibase.viewpager.FragmentPagerItem;

import java.util.List;


/**
 * @author fysong
 */
public class RecycleFragmentAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "FragmentStatePagerAdapter";

    private List<FragmentPagerItem> mFragmentItems;
    private Context mContext;


    public RecycleFragmentAdapter(Context mContext, FragmentManager fm, List<FragmentPagerItem> mFragmentItems) {
        super(fm);
        this.mFragmentItems = mFragmentItems;
        this.mContext = mContext;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentItems.get(position).getTitle();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentItems.get(position).instantiate(mContext, position);
    }

    @Override
    public int getCount() {
        return mFragmentItems == null ? 0 : mFragmentItems.size();
    }
}
