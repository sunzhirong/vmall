package com.ysarch.vmall.page.coupon.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.services.CouponLoader;
import com.ysarch.vmall.page.coupon.CouponListFragment;
import com.ysarch.vmall.utils.ResUtils;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponListPresenter extends BasePresenter<CouponListFragment> {
    public void requestCouponList(int status, boolean showLoading){
        CouponLoader.getInstance().requestUserCouponList(status)
                .compose(showLoading? showLoadingDialog():dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<CouponBean>>(getV()) {
                    @Override
                    public void onSuccess(List<CouponBean> couponBeans) {
                        for(CouponBean bean:couponBeans){
                            if(bean!=null){
                                bean.setCouponType(status);
                            }
                        }
                        getV().onCouponDataSucc(couponBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCouponDataFail();
                    }
                });
    }

    /**
     * 领取优惠券
     * @param couponBean
     */
    public void drawCoupon(CouponBean couponBean) {
        CouponLoader.getInstance().drawCoupon(couponBean.getId())
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().showTs(ResUtils.getString(R.string.tip_draw_succ));
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}
