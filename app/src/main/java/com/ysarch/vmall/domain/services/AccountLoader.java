package com.ysarch.vmall.domain.services;

import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.domain.bean.MemberBean;
import com.ysarch.vmall.domain.bean.MsgBean;
import com.ysarch.vmall.domain.bean.UpdateBean;
import com.ysarch.vmall.domain.bean.UserInfoResult;
import com.ysarch.vmall.domain.constant.Constants;

import java.util.List;

import cn.droidlover.xdroidmvp.net.SimpleResponse;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 账号相关接口
 * Created by fysong on 14/09/2020
 **/
public class AccountLoader extends ObjectLoader {


    private AccountService mService;

    private AccountLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), AccountService.class);
    }

    public static AccountLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    public Flowable<SimpleResponse<Object>> requestAuthCode(String phone) {
        return mService.requestAuthCode(phone);
    }


    /**
     * 刷新token
     *
     * @return
     */
    public Flowable<SimpleResponse<LoginResult>> refreshToken() {
        return mService.refreshToken();
    }

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    public Flowable<SimpleResponse<LoginResult>> login(@Field("telephone") String phone, @Field("password") String password) {
        return mService.login(phone, password);
    }
    public Flowable<SimpleResponse<LoginResult>> facebookLogin(@Field("outId") String outId, @Field("token") String token,@Field("nickname") String nickname) {
        return mService.facebookLogin(outId, token,nickname);
    }






    /**
     * 注册
     *
     * @param phone
     * @param authCode
     * @param nickname
     * @param password
     * @return
     */
    public Flowable<SimpleResponse<Object>> register(@Field("telephone") String phone, @Field("authCode") String authCode,
                                                     @Field("username") String nickname, @Field("password") String password) {
        return mService.register(phone, authCode, nickname, password);
    }

    /**
     * 修改密码/忘记密码
     *
     * @param phone
     * @param authCode
     * @param password
     * @return
     */
    public Flowable<SimpleResponse<Object>> updatePassword(@Field("telephone") String phone, @Field("authCode") String authCode,
                                                           @Field("password") String password) {
        return mService.updatePassword(phone, authCode, password);
    }


    /**
     * 获取会员信息
     *
     * @return
     */
    public Flowable<SimpleResponse<MemberBean>> requestUserInfo() {
        return mService.requestUserInfo();
    }

    public Flowable<SimpleResponse<Object>> updateMemberInfo(@Field("birthday") String birthday,
                                                             @Field("gender") int gender,
                                                             @Field("icon") String icon,
                                                             @Field("nickname") String nickname) {
        return mService.updateMemberInfo(birthday,gender,icon,nickname);
    }


    public Flowable<SimpleResponse<UpdateBean>> checkUpdate(@Query("versionCode") int versionCode){
        return mService.checkUpdate(versionCode);
    }


    /**
     * 消息列表
     * @param msgType
     * @return
     */
    public Flowable<SimpleResponse<List<MsgBean>>> requestMsgDatas(@Constants.MsgType int msgType){
        return mService.requestMsgDatas(msgType);
    }

    /**
     * 心跳判断是否存在新消息
     * @return
     */
    public Call<ResponseBody> checkHasNewMsg(){
        return mService.checkHasNewMsg();
    }

    interface AccountService {
        @GET("sso/getAuthCode")
        Flowable<SimpleResponse<Object>> requestAuthCode(@Query("telephone") String phone);

        @FormUrlEncoded
        @POST("sso/login")
        Flowable<SimpleResponse<LoginResult>> login(@Field("telephone") String phone, @Field("password") String password);


        @FormUrlEncoded
        @POST("sso/thirdPartyLogin")
        Flowable<SimpleResponse<LoginResult>> facebookLogin(@Field("outId") String outId, @Field("token") String token,@Field("nickname") String nickname);

        @GET("sso/refreshToken")
        Flowable<SimpleResponse<LoginResult>> refreshToken();

        @FormUrlEncoded
        @POST("sso/register")
        Flowable<SimpleResponse<Object>> register(@Field("telephone") String phone, @Field("authCode") String authCode,
                                                  @Field("username") String nickname, @Field("password") String password);

        @FormUrlEncoded
        @POST("sso/updatePassword")
        Flowable<SimpleResponse<Object>> updatePassword(@Field("telephone") String phone, @Field("authCode") String authCode,
                                                        @Field("password") String password);

        @GET("sso/info")
        Flowable<SimpleResponse<MemberBean>> requestUserInfo();

        @FormUrlEncoded
        @POST("sso/updateMemberInfo")
        Flowable<SimpleResponse<Object>> updateMemberInfo(@Field("birthday") String birthday,
                                                          @Field("gender") int gender,
                                                          @Field("icon") String icon,
                                                          @Field("nickname") String nickname);


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




//        @GET("member/address/list")
//        Flowable<SimpleResponse<List<AddressItemBean>>> requestAddressList();

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


        @GET("sso/umsMsgs")
        Flowable<SimpleResponse<List<MsgBean>>> requestMsgDatas(@Query("type") int msgType);


        @GET("sso/hasNewMsg")
        Call<ResponseBody> checkHasNewMsg();

        @GET("app/updateInfo")
        Flowable<SimpleResponse<UpdateBean>> checkUpdate(@Query("versionCode") int versionCode);


    }

    static class SingletonHolder {
        static AccountLoader INSTANCE = new AccountLoader();
    }
}
