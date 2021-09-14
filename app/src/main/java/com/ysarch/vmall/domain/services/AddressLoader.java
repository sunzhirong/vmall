package com.ysarch.vmall.domain.services;

import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.RegionsResult;

import java.util.List;

import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by fysong on 07/10/2020
 **/
public class AddressLoader extends ObjectLoader{


    private AddressService mService;
    private AddressLoader(){
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), AddressService.class);
    }

    /**
     * 添加地址
     *
     * @param province
     * @param city
     * @param region
     * @param detailAddress
     * @param postCode
     * @param phoneNumber
     * @param name
     * @param defaultStatus 1：默认
     * @return
     */
    public Flowable<SimpleResponse<Object>> requestAddAddress(@Field("province") String province,
                                                              @Field("city") String city,
                                                              @Field("region") String region,
                                                              @Field("detailAddress") String detailAddress,
                                                              @Field("postCode") String postCode,
                                                              @Field("phoneNumber") String phoneNumber,
                                                              @Field("name") String name,
                                                              @Field("defaultStatus") int defaultStatus) {
        return mService.requestAddAddress(province, city, region, detailAddress, postCode, phoneNumber, name, defaultStatus);
    }


    /**
     * 地址列表数据
     *
     * @return
     */
    public Flowable<SimpleResponse<List<AddressItemBean>>> requestAddressList() {
        return mService.requestAddressList();
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    public Flowable<SimpleResponse<Object>> deleteAddress(@Path("id") long addressId) {
        return mService.deleteAddress(addressId);
    }


    /**
     * 修改地址信息
     *
     * @param addressId
     * @param memberId
     * @param province
     * @param city
     * @param region
     * @param detailAddress
     * @param postCode
     * @param phoneNumber
     * @param name
     * @param defaultStatus
     * @return
     */
    public Flowable<SimpleResponse<Object>> updateAddress(@Field("id") long addressId,
                                                          @Field("memberId") long memberId,
                                                          @Field("province") String province,
                                                          @Field("city") String city,
                                                          @Field("region") String region,
                                                          @Field("detailAddress") String detailAddress,
                                                          @Field("postCode") String postCode,
                                                          @Field("phoneNumber") String phoneNumber,
                                                          @Field("name") String name,
                                                          @Field("defaultStatus") int defaultStatus) {
        return mService.updateAddress(addressId, memberId, province, city, region, detailAddress, postCode,
                phoneNumber, name, defaultStatus);
    }


    /**
     * 地址详情数据
     *
     * @param addressId
     * @return
     */
    public Flowable<SimpleResponse<AddressItemBean>> requestAddressDetail(@Path("id") long addressId) {
        return mService.requestAddressDetail(addressId);
    }


    /**
     * 获取柬埔寨行政区（省市区）数据
     * @return
     */
    public Flowable<SimpleResponse<RegionsResult>> requestRegionsData(){
        return mService.requestRegionsData();
    }

    interface AddressService{
        @FormUrlEncoded
        @POST("member/address/add")
        Flowable<SimpleResponse<Object>> requestAddAddress(@Field("province") String province,
                                                           @Field("city") String city,
                                                           @Field("region") String region,
                                                           @Field("detailAddress") String detailAddress,
                                                           @Field("postCode") String postCode,
                                                           @Field("phoneNumber") String phoneNumber,
                                                           @Field("name") String name,
                                                           @Field("defaultStatus") int defaultStatus);


        @GET("member/address/list")
        Flowable<SimpleResponse<List<AddressItemBean>>> requestAddressList();

        @GET("member/address/delete/{id}")
        Flowable<SimpleResponse<Object>> deleteAddress(@Path("id") long addressId);


        @FormUrlEncoded
        @POST("member/address/update")
        Flowable<SimpleResponse<Object>> updateAddress(@Field("id") long addressId,
                                                       @Field("memberId") long memberId,
                                                       @Field("province") String province,
                                                       @Field("city") String city,
                                                       @Field("region") String region,
                                                       @Field("detailAddress") String detailAddress,
                                                       @Field("postCode") String postCode,
                                                       @Field("phoneNumber") String phoneNumber,
                                                       @Field("name") String name,
                                                       @Field("defaultStatus") int defaultStatus);

        @GET("member/address/{id}")
        Flowable<SimpleResponse<AddressItemBean>> requestAddressDetail(@Path("id") long addressId);

        @GET("member/address/listAddress")
        Flowable<SimpleResponse<RegionsResult>> requestRegionsData();
    }



    public static AddressLoader getInstance(){
        return SingletonHolder.INSTANCE;
    }



    static class SingletonHolder{
        static AddressLoader INSTANCE= new AddressLoader();
    }
}
