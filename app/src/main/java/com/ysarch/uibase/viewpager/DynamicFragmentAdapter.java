package com.ysarch.uibase.viewpager;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

;

/**
 * 动态创建Fragment
 * Created by fysong on 2018-06-17
 */
public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "FragmentStatePagerAdapter";

    protected List<FragmentPagerItem> mFragmentItems;
    protected Context mContext;
    private boolean mRecyclable;

    public DynamicFragmentAdapter(Context context, FragmentManager fm, List<FragmentPagerItem> fragmentItems) {
        this(context, fm, fragmentItems, true);
    }


    public DynamicFragmentAdapter(Context mContext, FragmentManager fm, List<FragmentPagerItem> fragmentItems, boolean recyclable) {
        super(fm);
        this.mFragmentItems = fragmentItems;
        this.mContext = mContext;
        this.mRecyclable = recyclable;
    }

    /**
     * 管理的fragment是否可回收
     *
     * @param recyclable
     */
    public void setRecyclable(boolean recyclable) {
        mRecyclable = recyclable;
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


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mRecyclable) {
            super.destroyItem(container, position, object);
        }
    }
}
