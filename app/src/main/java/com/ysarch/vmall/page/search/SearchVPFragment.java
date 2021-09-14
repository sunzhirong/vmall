package com.ysarch.vmall.page.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.vmall.R;
import com.ysarch.uibase.viewpager.UnRecycleFragmentAdapter;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;

import com.ysarch.vmall.utils.ResUtils;
import com.yslibrary.event.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 11/10/2020
 **/
public class SearchVPFragment extends Fragment {

    @BindView(R.id.tb_search)
    TabLayout mTabLayout;
    @BindView(R.id.vp_search)
    ViewPager mViewPager;
    private SearchFragmentAdapter mFragmentAdapter;
    private List<FragmentPagerItem> mFragmentPagerItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private String mKeyWord;

    public static Bundle getBundle(String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_KEYWORD, keyword);
        return bundle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_search_vp, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mKeyWord = getArguments().getString(BundleKey.ARG_KEYWORD);
        }
        mTabLayout.setVisibility(View.GONE);
        initViewPager();
    }

    private void initViewPager() {
        mFragmentPagerItems.add(new FragmentPagerItem(SearchContentFragmentV2.class,
                ResUtils.getString(R.string.label_platform_tb),
                SearchContentFragmentV2.getBundle(mKeyWord, Constants.TYPE_PLATFORM_TB)));

//        mFragmentPagerItems.add(new FragmentPagerItem(SearchContentFragmentV2.class,
//                ResUtils.getString(R.string.label_platform_1688),
//                SearchContentFragmentV2.getBundle(mKeyWord, Constants.TYPE_PLATFORM_1688)));

        mFragmentAdapter = new SearchFragmentAdapter(getContext(), getChildFragmentManager(), mFragmentPagerItems);
        mFragmentAdapter.setKeyword(mKeyWord);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void resetKeyword(String keyword) {
        EventCenter.getInstance().notify(NotificationDef.EVENT_KEYWORD_CHANGED, keyword);
    }
}
