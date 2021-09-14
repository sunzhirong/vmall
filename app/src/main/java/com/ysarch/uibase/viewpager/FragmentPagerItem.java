package com.ysarch.uibase.viewpager;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * 装载Fragment title、类名和参数数据。便于动态初始化fragment
 */
public class FragmentPagerItem {

    private final Class<? extends Fragment> mFClazz;
    private final String mTitle;
    private Bundle mArgs;

    private Fragment mFragment;

    public FragmentPagerItem(Class<? extends Fragment> clazz) {
        this(clazz, (String) null);
    }

    public FragmentPagerItem(Class<? extends Fragment> clazz, String title) {
        this(clazz, title, null);
    }

    public FragmentPagerItem(Class<? extends Fragment> clazz, Bundle args) {
        this(clazz, null, args);
    }

    public FragmentPagerItem(Class<? extends Fragment> clazz, String title, Bundle args) {
        this.mFClazz = clazz;
        this.mTitle = title;
        if (args != null) {
            this.mArgs = new Bundle(args);
        }
    }

    public void setArgs(Bundle args) {
        mArgs = args;
    }

    public String getTitle() {
        return mTitle;
    }

    public Class<? extends Fragment> getFClazz() {
        return mFClazz;
    }

    public Bundle getArgs() {
        return mArgs;
    }

    /**
     * 创建并初始化Fragment
     */
    public Fragment instantiate(Context context, int position) {
        if (mFClazz == null) {
            return null;
        }
        mFragment = Fragment.instantiate(context, mFClazz.getName(), mArgs);
        return mFragment;
    }


    public Fragment getFragment() {
        return mFragment;
    }

}