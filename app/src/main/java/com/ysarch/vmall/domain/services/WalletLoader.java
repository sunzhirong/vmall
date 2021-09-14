package com.ysarch.vmall.domain.services;

import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.PayChannelBean;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.ysarch.vmall.domain.bean.WalletLogBean;

import java.util.List;

import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by fysong on 2020/10/15
 **/
public class WalletLoader extends ObjectLoader {

    private WalletService mService;

    public WalletLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), WalletService.class);
    }

    public static WalletLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 修改支付密码
     *
     * @param phone
     * @param authCode
     * @param payPassword
     * @return
     */
    public Flowable<SimpleResponse<Object>> modifyPayPassword(@Query("telephone") String phone, @Query("authCode") String authCode,
                                                              @Query("payPassword") String payPassword) {
        return mService.modifyPayPassword(phone, authCode, payPassword);
    }

    public Flowable<SimpleResponse<Object>> verifyPayPassword(String payPassword) {
        return mService.verifyPayPassword(payPassword);
    }

    public Flowable<SimpleResponse<Object>> updatePayPwd(String newPayPassword,String oldPayPassword) {
        return mService.updatePayPwd(newPayPassword,oldPayPassword);
    }

    /**
     * 获取充值账号
     *
     * @return
     */
    public Flowable<SimpleResponse<List<BankItemBean>>> listBanks() {
        return mService.listBanks();
    }

    public Flowable<SimpleResponse<List<PayChannelBean>>> payChannelList() {
        return mService.payChannelList();
    }


    /**
     * 提交充值信息
     * @param amount
     * @param bankId
     * @param rechargePic
     * @param rechargeTime
     * @param remark
     * @return
     */
    public Flowable<SimpleResponse<String>> postRechargeInfo(String amount, long bankId,
                                                            String rechargePic,String rechargeTime,
                                                               String remark){
        return mService.postRechargeInfo(amount, bankId, rechargePic, rechargeTime, remark);
    }

    /**
     * 余额支付
     * @param orderId
     * @param orderSn
     * @param payPassword
     * @return
     */
    public Flowable<SimpleResponse<Object>> payByBalance(@Field("orderId")long orderId, @Field("orderSn")String orderSn,
                                                         @Field("payPassword") String payPassword){
        return mService.payByBalance(orderId, orderSn, payPassword);
    }

    /**
     * 余额支付
     * @param orderId
     * @param orderSn
     * @param payPassword
     * @return
     */
    public Flowable<SimpleResponse<Object>> payFreightByBalance(@Field("orderId")long orderId, @Field("orderSn")String orderSn,
                                                         @Field("payPassword") String payPassword){
        return mService.payFreightByBalance(orderId, orderSn, payPassword);
    }

    /**
     * 检测余额
     * @return
     */
    public Flowable<SimpleResponse<Float>> checkWallet(){
        return mService.checkWallet();
    }

    /**
     * 交易明细
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SimpleResponse<List<WalletLogBean>>> requestWalletLogs(@Query("page") int page, @Query("pageSize") int pageSize){
        return mService.requestWalletLogs(page, pageSize);
    }

    /**
     * 充值明细
     * @return
     */
    public Flowable<SimpleResponse<List<RechargeItemBean>>> requestRechargeHistory(){
        return mService.requestRechargeHistory();
    }


    interface WalletService {
        //设置支付密码
        @FormUrlEncoded
        @POST("sso/modifyPayPassword")
        Flowable<SimpleResponse<Object>> modifyPayPassword(@Field("telephone") String phone, @Field("authCode") String authCode,
                                                           @Field("payPassword") String payPassword);

        @GET("pay/listBank")
        Flowable<SimpleResponse<List<BankItemBean>>> listBanks();


        @GET("pay/payChannelList")
        Flowable<SimpleResponse<List<PayChannelBean>>> payChannelList();

        //验证旧的支付密码
        @GET("sso/verifyPayPassword")
        Flowable<SimpleResponse<Object>> verifyPayPassword( @Query("payPassword") String payPassword);

        //修改支付密码
        @FormUrlEncoded
        @POST("sso/updatePayPassword")
        Flowable<SimpleResponse<Object>> updatePayPwd(@Field("newPayPassword") String newPayPassword, @Field("oldPayPassword") String oldPayPassword);
        /*
        * {
  "amount": 0,
  "bankId": 0,
  "rechargePic": "string",
  "rechargeTime": "2020-10-15T17:05:30.436Z",
  "remark": "string"
}*/
        @FormUrlEncoded
        @POST("pay/recharge")
        Flowable<SimpleResponse<String>> postRechargeInfo(@Field("amount") String amount, @Field("bankId") long bankId,
                                                           @Field("rechargePic") String rechargePic,
                                                           @Field("rechargeTime") String rechargeTime,
                                                           @Field("remark") String remark);

        @FormUrlEncoded
        @POST("pay/payByBalance")
        Flowable<SimpleResponse<Object>> payByBalance(@Field("orderId")long orderId, @Field("orderSn")String orderSn,
                                                      @Field("payPassword") String payPassword);

        @FormUrlEncoded
        @POST("/pay/payFreightByBalance")
        Flowable<SimpleResponse<Object>> payFreightByBalance(@Field("orderId")long orderId, @Field("orderSn")String orderSn,
                                                      @Field("payPassword") String payPassword);

        @GET("sso/userWallet")
        Flowable<SimpleResponse<Float>> checkWallet();

        @GET("pay/walletLogs")
        Flowable<SimpleResponse<List<WalletLogBean>>> requestWalletLogs(@Query("page") int page, @Query("pageSize") int pageSize);

        @GET("pay/listRecharge")
        Flowable<SimpleResponse<List<RechargeItemBean>>> requestRechargeHistory();
    }

    static class SingletonHolder {
        static WalletLoader INSTANCE = new WalletLoader();
    }
}
