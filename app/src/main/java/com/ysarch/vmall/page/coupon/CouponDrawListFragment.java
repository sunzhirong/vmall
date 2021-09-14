package com.ysarch.vmall.page.coupon;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.uibase.recyclerview.itemDecoration.LinearVerDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CouponListAdapter;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.services.CouponLoader;
import com.ysarch.vmall.page.coupon.presenter.CouponDrawListPresenter;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * 优惠券领取列表
 * Created by fysong on 17/09/2020
 **/
public class CouponDrawListFragment extends CommonPureListFragment<CouponDrawListPresenter> {

    private long mGoodsId;
    private List<CouponBean> mCouponBeans;


    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new CouponListAdapter(getContext());
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        mRootView.setBackgroundColor(ResUtils.getColor(R.color.bg_page_gray));
    }

    @Override
    protected void requestData(int page) {
        getPresenter().requestCouponList(mGoodsId, false);
    }

    @Override
    protected void requestDataOnEmpty() {
        getPresenter().requestCouponList(mGoodsId, true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mGoodsId = getArguments().getInt(BundleKey.ARG_COUPON_STATUS);
            String couponsJson = getArguments().getString(BundleKey.ARG_COUPON_LIST_JSON);
            if(!TextUtils.isEmpty(couponsJson)){
                mCouponBeans = new Gson().fromJson(couponsJson, new TypeToken<List<CouponBean>>() {
                }.getType());
            }
        }
        int gapV = SizeUtils.dp2px(8);
        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        initRecyclerView(new LinearVerDecoration(marginH, gapV, gapV, gapV));

        ((CouponListAdapter)mAdapter).setOnItemClickListener((position, data) -> {
            getPresenter().drawCoupon((CouponBean)data);
        });

        if(CollectionUtils.isNotEmpty(mCouponBeans)){
            ((CouponListAdapter) mAdapter).refreshData(mCouponBeans);
        } else {
            getPresenter().requestCouponList(mGoodsId, true);
        }
    }

    @Override
    public CouponDrawListPresenter newPresenter() {
        return new CouponDrawListPresenter();
    }

    public void onCouponDataSucc(List<CouponBean> couponBeans) {
        mCouponBeans = couponBeans;
        ((CouponListAdapter) mAdapter).refreshData(couponBeans);
        resetUIStatus(1, true);
    }

    public void onCouponDataFail() {
        resetUIStatus(1, false);
    }

}
