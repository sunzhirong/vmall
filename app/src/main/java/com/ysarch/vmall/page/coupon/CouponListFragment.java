package com.ysarch.vmall.page.coupon;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.LinearVerDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CouponListAdapter;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.coupon.presenter.CouponListPresenter;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;

import java.util.List;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponListFragment extends CommonPureListFragment<CouponListPresenter> {

    /**
     * 优惠券状态
     */
    private int mCouponStatus;
    public static Bundle getBundle(@Constants.CouponStatus int status) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_COUPON_STATUS, status);
        return bundle;
    }
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
        getPresenter().requestCouponList(mCouponStatus, false);
    }

    @Override
    protected void requestDataOnEmpty() {
        getPresenter().requestCouponList(mCouponStatus, true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if(getArguments() != null){
            mCouponStatus = getArguments().getInt(BundleKey.ARG_COUPON_STATUS);
        }
        int gapV = SizeUtils.dp2px(8);
        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        initRecyclerView(new LinearVerDecoration(marginH,gapV,gapV,gapV));

        getPresenter().requestCouponList(mCouponStatus, true);
    }

    @Override
    public CouponListPresenter newPresenter() {
        return new CouponListPresenter();
    }

    public void onCouponDataSucc(List<CouponBean> couponBeans) {
        ((CouponListAdapter)mAdapter).refreshData(couponBeans);
        resetUIStatus(1, true);
    }

    public void onCouponDataFail() {
        resetUIStatus(1, false);
    }
}
