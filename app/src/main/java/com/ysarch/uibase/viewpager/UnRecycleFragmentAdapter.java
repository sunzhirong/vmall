package com.ysarch.uibase.viewpager;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ysarch.uibase.viewpager.FragmentPagerItem;

import java.util.List;


/**
 * 防止子fragment被回收的FragmentAdapter
 * @author Darcy yeguozhong@yeah.net
 */
public class UnRecycleFragmentAdapter extends FragmentPagerAdapter {

    private static final String TAG = "UnRecycleFragmentAdapter";

    private List<FragmentPagerItem> mFragmentItems;
    private Context mContext;

    public UnRecycleFragmentAdapter(Context context, FragmentManager fm, List<FragmentPagerItem> fragmentItems) {
        super(fm);
        this.mContext = context;
        this.mFragmentItems = fragmentItems;
    }

    @Override
    public int getCount() {
        return mFragmentItems == null ? 0 : mFragmentItems.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentItems.get(position).instantiate(mContext,position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentItems.get(position).getTitle();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * 重写该方法，不做实现，防止子fragment被回收
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    /**
     * 获取某个FragmentItem
     * @param position
     * @return
     */
    public FragmentPagerItem getFragmentPagerItem(int position){
        if(mFragmentItems != null && mFragmentItems.size() > position){
            return mFragmentItems.get(position);
        } else {
            return null;
        }

    }

    public List<FragmentPagerItem> getFragmentPagerItems() {
        return mFragmentItems;
    }
}
