package com.ysarch.vmall.page.main.shoye;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.uibase.viewpager.UnRecycleFragmentAdapter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.main.presenter.MainShouYePresenter;
import com.ysarch.vmall.page.msg.MsgActivity;
import com.ysarch.vmall.page.search.SearchActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 24/08/2020
 **/
public class MainShouYeFragment extends BaseFragment<MainShouYePresenter> {
    @BindView(R.id.tb_shouye_page)
    TabLayout mTabLayout;
    @BindView(R.id.vp_shouye_page)
    ViewPager mViewPager;

    private UnRecycleFragmentAdapter mFragmentAdapter;
    private List<FragmentPagerItem> mFragmentPagerItems = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().requestCateDatas();
    }


    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
//        initViewPager();
    }

    private void initViewPager() {

        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeWelcomeFragment.class, getString(R.string.label_most_popular)));
        if (CollectionUtils.isNotEmpty(AppContext.getsInstance().getCateHeaderBeans())) {
            for (int i = 0; i < AppContext.getsInstance().getCateHeaderBeans().size(); i++) {
                CateLevelBean cateLevelBean = AppContext.getsInstance().getCateHeaderBeans().get(i);
                String name ;
                switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
                    case Constants.ID_LAN_KM:
                        name = cateLevelBean.getKhName();
                        break;
                    case Constants.ID_LAN_ZH:
                        name = cateLevelBean.getName();
                        break;
                    case Constants.ID_LAN_EN:
                        name = cateLevelBean.getEnName();
                        break;
                    default:
                        name = cateLevelBean.getName();
                        break;
                }
                mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, name,
                        ShouyeSubpageFragment.getBundle(name,cateLevelBean.getKeywords(), cateLevelBean.getChildren())));
            }
        }
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "本地发货"));
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "家居"));
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "女人"));
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "男人"));
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "儿童"));
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "电子产品"));
//        mFragmentPagerItems.add(new FragmentPagerItem(ShouyeSubpageFragment.class, "哈哈哈"));

        mFragmentAdapter = new UnRecycleFragmentAdapter(getContext(), getChildFragmentManager(), mFragmentPagerItems);
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View view = layoutInflater.inflate(R.layout.item_home_tab_text, null);

            TextView textView = view.findViewById(R.id.tv_uncheck_home_tab_text);
            textView.setText(mFragmentPagerItems.get(i).getTitle());
            textView = view.findViewById(R.id.tv_checked_home_tab_text);
            textView.setText(mFragmentPagerItems.get(i).getTitle());
            tab.setCustomView(view);
        }


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabStatusChanged(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                onTabStatusChanged(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        onTabStatusChanged(mTabLayout.getTabAt(mViewPager.getCurrentItem()), true);
    }

    private void onTabStatusChanged(TabLayout.Tab tab, boolean checked) {
        if (tab.getCustomView() == null) return;
        if (checked) {
            tab.getCustomView().findViewById(R.id.tv_uncheck_home_tab_text).setVisibility(View.INVISIBLE);
            tab.getCustomView().findViewById(R.id.tv_checked_home_tab_text).setVisibility(View.VISIBLE);
            tab.getCustomView().findViewById(R.id.v_indicator_home_tab_text).setVisibility(View.VISIBLE);
        } else {
            tab.getCustomView().findViewById(R.id.tv_uncheck_home_tab_text).setVisibility(View.VISIBLE);
            tab.getCustomView().findViewById(R.id.tv_checked_home_tab_text).setVisibility(View.INVISIBLE);
            tab.getCustomView().findViewById(R.id.v_indicator_home_tab_text).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shouye_main;
    }

    @Override
    public MainShouYePresenter newPresenter() {
        return new MainShouYePresenter();
    }


    @OnClick(R.id.include_search_bar_shouye_page)
    void onSearchBarClick() {
        NavHelper.startActivity(getActivity(), SearchActivity.class,
                SearchActivity.getBundle(true));
    }

    @OnClick(R.id.iv_shouye_msg)
    void onMsgClick() {
        if (UserInfoManager.judeIsLogin(getActivity())) {
            NavHelper.startActivity(getActivity(), MsgActivity.class);
        }
    }

    public void onCateDatasSucc(List<CateLevelBean> cateLevelBeans) {
        initViewPager();
    }

    public void onCateDatasFail() {
    }
}
