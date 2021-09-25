package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.uibase.viewpager.UnRecycleFragmentAdapter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.orders.presenter.OrderHistoryPresenter;
import com.ysarch.vmall.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fysong on 15/09/2020
 **/
public class OrderHistoryFragment extends BaseFragment<OrderHistoryPresenter> {
    @BindView(R.id.tl_history_order)
    TabLayout mTabLayout;
    @BindView(R.id.vp_history_order)
    ViewPager mViewPager;
    private List<FragmentPagerItem> mFragmentPagerItems;
    private UnRecycleFragmentAdapter mFragmentAdapter;
    private List<Integer> mPageIds = new ArrayList<>();

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        initViewPager();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            int id = getArguments().getInt(BundleKey.ARG_TARGET_PAGE, -1);
            int navStatus = 0;
            for (int i = 0; i < mPageIds.size(); i++) {
                if (id == mPageIds.get(i)) {
                    navStatus = i;
                    break;
                }
            }
            mViewPager.setCurrentItem(navStatus);
        }
    }

    private void initViewPager() {
        if (mFragmentAdapter == null) {
            mFragmentPagerItems = new ArrayList<>();

            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_all),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_ALL)));
            mPageIds.add(Constants.STATUS_ORDER_ALL);
            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_audit_waiting),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_AUDIT_WAITING)));
            mPageIds.add(Constants.STATUS_ORDER_AUDIT_WAITING);
            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_unpay),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_UNPAY)));
            mPageIds.add(Constants.STATUS_ORDER_UNPAY);
            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_deliver_waiting),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_DELIVER_WAITING)));
            mPageIds.add(Constants.STATUS_ORDER_DELIVER_WAITING);
            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_delivered),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_DELIVERED)));
            mPageIds.add(Constants.STATUS_ORDER_DELIVERED);
            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_complete),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_COMPLETE)));
            mPageIds.add(Constants.STATUS_ORDER_COMPLETE);
            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class, ResUtils.getString(R.string.label_order_close),
                    OrderListFragment.getBundle(Constants.STATUS_ORDER_CLOSED)));
            mPageIds.add(Constants.STATUS_ORDER_CLOSED);

//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_supplementary),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_SUPPLEMENTARY)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_warehouse_china),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_WAREHOUSE_CHINA)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_warehouse_oversea),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_WAREHOUSE_OVERSEA)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_warehouse_oversea_signed),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_WAREHOUSE_OVERSEA_SIGNED)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_delivered),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_DELIVERED)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_user_signed),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_USER_SIGNED)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_complete),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_COMPLETE)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_close),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_CLOSED)));
//            mFragmentPagerItems.add(new FragmentPagerItem(OrderListFragment.class,  ResUtils.getString(R.string.label_order_invalid),
//                    OrderListFragment.getBundle(Constants.STATUS_ORDER_INVALID)));

           /* public static final int STATUS_ORDER_ALL = -1;                          //全部
            public static final int STATUS_ORDER_UNPAY = 0;                         //待付款
            public static final int STATUS_ORDER_DELIVER_WAITING = 1;               //待发货
            public static final int STATUS_ORDER_DELIVERED = 2;                     //已发货
            public static final int STATUS_ORDER_COMPLETE = 3;                      //已完成
            public static final int STATUS_ORDER_CLOSED = 4;                        //已关闭
            public static final int STATUS_ORDER_INVALID = 5;                       //无效订单
            public static final int STATUS_ORDER_AUDIT_WAITING = 6;                 //待审核
            public static final int STATUS_ORDER_SUPPLEMENTARY = 7;                 //待补款
            public static final int STATUS_ORDER_warehouse_CHINA = 8;               //在中国仓库
            public static final int STATUS_ORDER_warehouse_oversea = 9;             //已发往海外仓库
            public static final int STATUS_ORDER_warehouse_oversea_signed = 10;     //海外仓已签收
            public static final int STATUS_ORDER_user_signed = 11;                  //用户签收
            public static final int STATUS_ORDER_cancellation = 12;                 //作废单*/
            mFragmentAdapter = new UnRecycleFragmentAdapter(getContext(), getChildFragmentManager(), mFragmentPagerItems);

            mViewPager.setAdapter(mFragmentAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_history;
    }

    @Override
    public OrderHistoryPresenter newPresenter() {
        return new OrderHistoryPresenter();
    }
}
