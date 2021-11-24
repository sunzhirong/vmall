package com.ysarch.vmall.page.coupon;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.vmall.R;
import com.ysarch.uibase.viewpager.UnRecycleFragmentAdapter;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.coupon.presenter.CouponPresenter;
import com.ysarch.vmall.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponFragment extends BaseFragment<CouponPresenter> {

    @BindView(R.id.tl_coupon)
    TabLayout mTabLayout;
    @BindView(R.id.vp_coupon)
    ViewPager mViewPager;
    private UnRecycleFragmentAdapter mFragmentAdapter;
    private List<FragmentPagerItem> mPagerItems;
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        initAdapter();
    }

    private void initAdapter() {
        if( mFragmentAdapter == null ){
            mPagerItems = new ArrayList<>();
            mPagerItems.add(new FragmentPagerItem(CouponListFragment.class, ResUtils.getString(R.string.label_coupon_unused),
                    CouponListFragment.getBundle(Constants.STATUS_COUPON_UNUSE)));
            mPagerItems.add(new FragmentPagerItem(CouponListFragment.class, ResUtils.getString(R.string.label_coupon_used),
                    CouponListFragment.getBundle(Constants.STATUS_COUPON_USED)));
            mPagerItems.add(new FragmentPagerItem(CouponListFragment.class, ResUtils.getString(R.string.label_coupon_overtime),
                    CouponListFragment.getBundle(Constants.STATUS_COUPON_OVER_TIME)));
            mFragmentAdapter = new UnRecycleFragmentAdapter(getContext(), getChildFragmentManager(), mPagerItems);

            mViewPager.setAdapter(mFragmentAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public CouponPresenter newPresenter() {
        return new CouponPresenter();
    }

    @Override
    protected String getPageName() {
        return "优惠券页";
    }
}
