package com.ysarch.vmall.domain.services;

import com.alibaba.fastjson.JSON;
import com.ysarch.vmall.domain.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public class UploadLogLoader extends ObjectLoader{

    static class SingletonHolder {
        static UploadLogLoader INSTANCE = new UploadLogLoader();
    }

    private UploadLogLoader.UploadLogService mService;

    private UploadLogLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), UploadLogLoader.UploadLogService.class);
    }

    public static UploadLogLoader getInstance() {
        return UploadLogLoader.SingletonHolder.INSTANCE;
    }


    public Call<ResponseBody> generateOrderLog(Map<String,Object> map) {
        return mService.generateOrderLog(RequestBody.create(JSON_TYPE, JSON.toJSONString(map)));
    }

    public Call<ResponseBody> loginLog(Map<String,Object> map) {
        return mService.loginLog(RequestBody.create(JSON_TYPE, JSON.toJSONString(map)));
    }

    public Call<ResponseBody> productDetailLog(Map<String,Object> map) {
        return mService.productDetailLog(RequestBody.create(JSON_TYPE, JSON.toJSONString(map)));
    }

    public Call<ResponseBody> rechargeLogParam(Map<String,Object> map) {
        return mService.rechargeLogParam(RequestBody.create(JSON_TYPE, JSON.toJSONString(map)));
    }


    interface UploadLogService {
        /**
         * 记录提交订单日志
         * @param requestBody
         * @return
         */
        @POST("/log/generateOrderLog")
        Call<ResponseBody> generateOrderLog(@Body RequestBody requestBody);

        /**
         * 记录登录日志
         * @param requestBody
         * @return
         */
        @POST("/log/loginLog")
        Call<ResponseBody> loginLog(@Body RequestBody requestBody);

        /**
         *记录商品详情页日志
         * @param requestBody
         * @return
         */
        @POST("/log/productDetailLog")
        Call<ResponseBody> productDetailLog(@Body RequestBody requestBody);

        /**
         * 记录充值日志
         * @param requestBody
         * @return
         */
        @POST("/log/rechargeLogParam")
        Call<ResponseBody> rechargeLogParam(@Body RequestBody requestBody);

    }
}
