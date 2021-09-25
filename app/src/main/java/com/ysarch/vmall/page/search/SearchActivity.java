package com.ysarch.vmall.page.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.yslibrary.event.hailer.FunctionsManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchActivity extends BaseTitleActivity {


    public static final int TYPE_NOR = 0;
    public static final int TYPE_SEARCH_KEYWORD = 1;
    public static final int TYPE_SEARCH_KEYWORD_WITH_CACHE = 2;
    public static final int TYPE_SEARCH_CATE_ID = 3;



    private static List<String> mCacheKeyword = new ArrayList<>();


//    public static Bundle getBundle(int cateId) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(BundleKey.ARG_CATE_ID, cateId);
//        bundle.putInt(BundleKey.ARG_PAGE_LAUNCH_TYPE, TYPE_SEARCH_CATE_ID);
//        return bundle;
//    }



    public static Bundle getBundle(String keyword, boolean cache) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_KEYWORD, keyword);
        bundle.putInt(BundleKey.ARG_PAGE_LAUNCH_TYPE, TYPE_SEARCH_KEYWORD);
        bundle.putBoolean(BundleKey.ARG_CACHE, cache);
        return bundle;
    }

    public static Bundle getBundle(boolean showKeyBorad) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(BundleKey.ARG_SHOW_KEYBORAD, showKeyBorad);
        return bundle;
    }



    public static boolean needCache(String keyword){
        if(TextUtils.isEmpty(keyword))
            return false;
        return mCacheKeyword.contains(keyword);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent == null && intent.getExtras() == null)
            return;

        boolean cache = intent.getExtras().getBoolean(BundleKey.ARG_CACHE, false);
        if(cache) {
            String keyword = getIntent().getExtras().getString(BundleKey.ARG_KEYWORD, "");

            if(!TextUtils.isEmpty(keyword)){
                if(!mCacheKeyword.contains(keyword)){
                    mCacheKeyword.add(keyword);
                }
            }

            if(mSearchFragment != null){

            }
        }
    }

    @Override
    protected String getCustomTitle() {
        return null;
    }


    private SearchFragment mSearchFragment;
    @Override
    protected Fragment createFragment() {
        mSearchFragment = new SearchFragment();
        if (getIntent().getExtras() != null) {
            boolean cache = getIntent().getExtras().getBoolean(BundleKey.ARG_CACHE, false);
            if(cache) {
                String keyword = getIntent().getExtras().getString(BundleKey.ARG_KEYWORD, "");

                if(!TextUtils.isEmpty(keyword)){
                    if(!mCacheKeyword.contains(keyword)){
                        mCacheKeyword.add(keyword);
                    }
                }
            }
            mSearchFragment.setArguments(getIntent().getExtras());
        }
        return mSearchFragment;
    }

    public void setKeyword(String keyword){
        FunctionsManager.getInstance().invokeFunction(HailerFunctionDef.SEARCH_KEYWORD, keyword);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        StatusBarCompat.setStatusBarColor(this, 0xffffffff);
        StatusBarCompat.changeToLightStatusBar(this);
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
