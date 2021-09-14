package com.ysarch.vmall.domain.services;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.KeyValueBean;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderTraceBean;
import com.ysarch.vmall.domain.bean.UpdateCartBean;
import com.ysarch.vmall.domain.bean.UpdateReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by fysong on 27/09/2020
 **/
public class OrderLoader extends ObjectLoader {


    private OrderService mService;

    private OrderLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), OrderService.class);
    }

    public static OrderLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 生成订单
     *
     * @param ids
     * @return
     */
    public Call<ResponseBody> generateConfirmOrder(List<String> ids) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < ids.size(); i++) {
            jsonArray.put(ids.get(i));
        }
        return mService.generateConfirmOrder(RequestBody.create(JSON_TYPE, jsonArray.toString()));
    }

    public Call<ResponseBody> updateConfirmOrder(List<UpdateCartBean> ids, int couponHistoryId) {
        UpdateReq updateReq = new UpdateReq();
        updateReq.cartInfoList = ids;
        updateReq.couponHistoryId = couponHistoryId;
        return mService.updateConfirmOrder(RequestBody.create(JSON_TYPE, JSON.toJSONString(updateReq)));
    }



    /**
     * 生成订单
     *
     * @param cartIds
     * @param couponId
     * @param memberReceiveAddressId
     * @param payType
     * @param useIntegration
     * @return
     */
    public Call<ResponseBody> generateOrder(List<Integer> cartIds, long couponId,
                                            long memberReceiveAddressId,
                                            int payType,
                                            int useIntegration) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < cartIds.size(); i++) {
                jsonArray.put(cartIds.get(i));
            }
            jsonObject.put("cartIds", jsonArray);
            if (couponId > 0)
                jsonObject.put("couponId", couponId);
            jsonObject.put("memberReceiveAddressId", memberReceiveAddressId);
            jsonObject.put("payType", payType);
            jsonObject.put("useIntegration", useIntegration);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mService.generateOrder(RequestBody.create(JSON_TYPE, jsonObject.toString()));

    }



    /**
     * 生成订单
     *
     * @param cartIds
     * @param couponId
     * @param memberReceiveAddressId
     * @param payType
     * @param useIntegration
     * @return
     */
    public Call<ResponseBody> generateOrderV2(List<Long> cartIds, long couponId,
                                              long historyCouponId,
                                              long memberReceiveAddressId,
                                              int payType, int deliveryType,int shippingMethod, int shippingType, int warehouse,
                                              int useIntegration, String remark) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cartIds.size(); i++) {
            jsonArray.put(cartIds.get(i));
        }
        jsonObject.put("cartIds", jsonArray);
        if (couponId > 0)
            jsonObject.put("couponId", couponId);
        jsonObject.put("memberReceiveAddressId", memberReceiveAddressId);
        jsonObject.put("payType", payType);
        jsonObject.put("useIntegration", useIntegration);
        jsonObject.put("deliverlyType", deliveryType);
        jsonObject.put("shippingType", shippingType);
        jsonObject.put("warehouse", warehouse);
        jsonObject.put("historyCouponId", historyCouponId);
        jsonObject.put("shippingMethod", shippingMethod);

        if (!TextUtils.isEmpty(remark)) {
            jsonObject.put("remark", remark);
        }


        return mService.generateOrder(RequestBody.create(JSON_TYPE, jsonObject.toString()));

    }


    /**
     * 获取订单列表数据
     *
     * @param status
     * @param pageSize
     * @return
     */
    public Flowable<SimpleResponse<ListResult<OrderBean>>> requestOrderList(int status,
                                                                            int page, int pageSize) {
        return mService.requestOrderList(status, page, pageSize);
    }

    public Call<ResponseBody> confirmReceiveOrder(@Field("orderId")long orderId){
        return mService.confirmReceiveOrder(RequestBody.create(JSON_TYPE, orderId + ""));
    }


    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    public Call<ResponseBody> cancelOrder(@Field("orderId") long orderId,@Field("cancelReason")int cancelReason) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("cancelReason", cancelReason);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mService.cancelOrder(RequestBody.create(JSON_TYPE, jsonObject.toString()));
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @return
     */
    public Call<ResponseBody> deleteOrder(@Field("orderId") long orderId) {
        return mService.deleteOrder(RequestBody.create(JSON_TYPE, orderId + ""));
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    public Flowable<SimpleResponse<OrderBean>> requestOrderDetail(@Path("orderId") long orderId) {
        return mService.requestOrderDetail(orderId);
    }

    /**
     * 获取仓库数据
     *
     * @return
     */
    public Flowable<SimpleResponse<String>> requestWareHouseInfo() {
        return mService.requestEnumInfo(2);
    }
    /**
     * 获取仓库数据
     *
     * @return
     */
    public Flowable<SimpleResponse<List<OrderTraceBean>>> getOrderTrace(long orderId) {
        return mService.getOrderTrace(orderId);
    }


    interface OrderService {
        @POST("/order/generateConfirmOrder")
        Call<ResponseBody> generateConfirmOrder(@Body RequestBody requestBody);

        //更改购物车信息
        @POST("/order/updateConfirmOrder")
        Call<ResponseBody> updateConfirmOrder(@Body RequestBody requestBody);

        @FormUrlEncoded
        @POST("/order/generateOrder")
        Flowable<SimpleResponse<Object>> generateOrder(@Field("cartIds") int[] cartIds, @Field("couponId") long couponId,
                                                       @Field("memberReceiveAddressId") long memberReceiveAddressId,
                                                       @Field("payType") int payType,
                                                       @Field("useIntegration") int useIntegration);

        @POST("/order/generateOrder")
        Call<ResponseBody> generateOrder(@Body RequestBody requestBody);

        @GET("/order/list")
        Flowable<SimpleResponse<ListResult<OrderBean>>> requestOrderList(@Query("status") int status, @Query("pageNum") int page,
                                                                         @Query("pageSize") int pageSize
        );

        @POST("/order/confirmReceiveOrder")
        Call<ResponseBody> confirmReceiveOrder(@Body RequestBody requestBody);


//        @POST("/order/cancelUserOrder")
        @POST("/order/cancelUserOrderV2")
        Call<ResponseBody> cancelOrder(@Body RequestBody requestBody);

        @POST("/order/deleteOrder")
        Call<ResponseBody> deleteOrder(@Body RequestBody requestBody);


        @GET("order/detail/{orderId}")
        Flowable<SimpleResponse<OrderBean>> requestOrderDetail(@Path("orderId") long orderId);

        @GET("mall/common/enumInfo")
        Flowable<SimpleResponse<String>> requestEnumInfo(@Query("type") int dataType);


        @GET("/order/changeLog")
        Flowable<SimpleResponse<List<OrderTraceBean>>> getOrderTrace(@Query("orderId") long orderId);
//
    }

    static class SingletonHolder {
        static OrderLoader INSTANCE = new OrderLoader();
    }
}
