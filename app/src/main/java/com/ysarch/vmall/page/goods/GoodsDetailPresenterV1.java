package com.ysarch.vmall.page.goods;

import android.text.TextUtils;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResult;
import com.ysarch.vmall.domain.bean.SkuBean;
import com.ysarch.vmall.domain.services.CouponLoader;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 22/09/2020
 **/
public class GoodsDetailPresenterV1 extends BasePresenter<GoodsDetailActivityV1> {

    public void requestGoodsDetail(long goodsId) {
        GoodsLoader.getInstance().requestGoodsDetail(0, goodsId)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<GoodsDetailResult>(getV()) {
                    @Override
                    public void onSuccess(GoodsDetailResult goodsDetailResult) {
                        correctUrl(goodsDetailResult);
                        getV().onDataSuccess(goodsDetailResult);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail();
                    }
                });
    }

    private void correctUrl(GoodsDetailResult goodsDetailResult) {
        if (goodsDetailResult.getItem() != null
                && CollectionUtils.isNotEmpty(goodsDetailResult.getItem().getPropsImg())) {
            Map<String, String> img = goodsDetailResult.getItem().getPropsImg().get(0);
            for (String key : img.keySet()) {
                img.put(key, VMallUtils.correctUrl(img.get(key)));
            }
        }
    }

    /**
     * 添加商品到购物车
     * @param goodsDetailBean
     * @param skuBean
     * @param skuLabel
     * @param img
     * @param quantity
     */
    public void requestAddCart(GoodsDetailBean goodsDetailBean, SkuBean skuBean, String skuLabel, String img, int quantity) {
        if (TextUtils.isEmpty(img)) {
            if (!TextUtils.isEmpty(goodsDetailBean.getPicUrl())) {
                img = goodsDetailBean.getPicUrl();
            }
        }
//        GoodsLoader.getInstance().requestAddCart(goodsDetailBean, skuBean, img,skuLabel,  quantity)
//                .compose(showLoadingDialog())
//                .compose(getV().bindToLifecycle())
//                .subscribe(new ApiSubscriber<Object>(getV()) {
//                    @Override
//                    public void onSuccess(Object o) {
//                        getV().onAddCartSucc();
//                    }
//
//                    @Override
//                    protected void onFail(NetError error) {
//                        super.onFail(error);
////                        getV().onAddCartFail();
//                    }
//                });
    }

    /**
     * 获取优惠券数据
     *
     * @param goodsId
     */
    public void requestCouponList(long goodsId) {
        CouponLoader.getInstance().requestCouponList(goodsId)
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<CouponBean>>() {
                    @Override
                    public void onSuccess(List<CouponBean> couponBeans) {
                        getV().onCouponSucc(couponBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCouponFail();
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
