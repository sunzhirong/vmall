package com.ysarch.vmall.page.msg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tendcloud.tenddata.TCAgent;
import com.ysarch.uibase.viewpager.DynamicFragmentAdapter;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.vmall.R;
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
 * Created by fysong on 27/10/2020
 **/
public class MsgVPFragment extends Fragment {
    @BindView(R.id.tb_search)
    TabLayout mTabLayout;
    @BindView(R.id.vp_search)
    ViewPager mViewPager;
    private DynamicFragmentAdapter mFragmentAdapter;
    private List<FragmentPagerItem> mFragmentPagerItems = new ArrayList<>();
    private LayoutInflater mInflater;

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

        initViewPager();
    }

    private void initViewPager() {
        mFragmentPagerItems.add(new FragmentPagerItem(MsgListFragment.class,
                ResUtils.getString(R.string.label_msg_order),
                MsgListFragment.getBundle(Constants.TYPE_MSG_ORDER)));

        mFragmentPagerItems.add(new FragmentPagerItem(MsgListFragment.class,
                ResUtils.getString(R.string.label_msg_recharge),
                MsgListFragment.getBundle(Constants.TYPE_MSG_RECHARGE)));

        mFragmentPagerItems.add(new FragmentPagerItem(MsgListFragment.class,
                ResUtils.getString(R.string.label_msg_delivery),
                MsgListFragment.getBundle(Constants.TYPE_MSG_DELIVERY)));

        mFragmentAdapter = new DynamicFragmentAdapter(getContext(), getChildFragmentManager(), mFragmentPagerItems);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
    @Override
    public void onPause() {
        super.onPause();
        if(!TextUtils.isEmpty(getPageName()))
            TCAgent.onPageEnd(getContext(),getPageName());
    }

    /**
     * 获取当前界面的埋点名字
     * @return
     */
    protected String getPageName(){
        return "消息页";
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(getPageName()))
            TCAgent.onPageStart(getContext(),getPageName());
    }

}
