package com.ysarch.vmall.domain.services;

import android.media.ImageReader;

import androidx.annotation.IntRange;

import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.constant.Constants;

import java.util.List;

import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fysong on 25/09/2020
 **/
public class CouponLoader extends ObjectLoader{
    private CouponService mService;
    public CouponLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), CouponService.class);
    }


    /**
     * 获取商品优惠券
     * @param goodsId
     * @return
     */
    public Flowable<SimpleResponse<List<CouponBean>>> requestCouponList(@Path("productId") long goodsId){
        return mService.requestCouponList(goodsId);
    }


    /**
     * 领取优惠券
     * @param couponId
     * @return
     */
    public Flowable<SimpleResponse<Object>> drawCoupon(@Path("couponId") long couponId){
        return mService.drawCoupon(couponId);
    }


    /**
     * 获取用户优惠券列表
     * @param status
     * @return
     */
    public Flowable<SimpleResponse<List<CouponBean>>> requestUserCouponList(@Constants.CouponStatus int status){
        return mService.requestUserCouponList(status);
    }


    /**
     * 获取购物车相关优惠券
     * @param useAvailable
     * @return
     */
    public Flowable<SimpleResponse<List<CouponBean>>> requestGoodsReferCouponDatas(@IntRange(from = 0,to = 1) int useAvailable){
        return mService.requestGoodsReferCouponDatas(useAvailable);
    }

    /**
     * 获取优惠券历史列表
     * @param status
     * @return
     */
    public Flowable<SimpleResponse<List<CouponBean>>> requestCouponHistory(@Query("useStatus") int status){
        return mService.requestCouponHistory(status);
    }


    interface CouponService{

        @GET("/member/coupon/listByProduct/{productId}")
        Flowable<SimpleResponse<List<CouponBean>>> requestCouponList(@Path("productId") long goodsId);

        @FormUrlEncoded
        @POST("/member/coupon/add/{couponId}")
        Flowable<SimpleResponse<Object>> drawCoupon(@Path("couponId") long couponId);

        @GET("member/coupon/list")
        Flowable<SimpleResponse<List<CouponBean>>> requestUserCouponList(@Query("useStatus") int status);


        @GET("member/coupon/list/cart")
        Flowable<SimpleResponse<List<CouponBean>>> requestGoodsReferCouponDatas(@Query("type") int useAvailable);

        @GET("member/coupon/listHistory")
        Flowable<SimpleResponse<List<CouponBean>>> requestCouponHistory(@Query("useStatus") int status);
    }



    public static CouponLoader getInstance(){
        return SingletonHolder.INSTANCE;
    }
    static class SingletonHolder{
        static CouponLoader INSTANCE = new CouponLoader();
    }
}
