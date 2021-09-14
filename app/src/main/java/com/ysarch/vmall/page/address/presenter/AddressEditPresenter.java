package com.ysarch.vmall.page.address.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.RegionsResult;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.services.AddressLoader;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.address.AddressEditFragment;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import retrofit2.http.Field;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressEditPresenter extends BasePresenter<AddressEditFragment> {
    /**
     * 添加新的收货地址
     *
     * @param province
     * @param city
     * @param region
     * @param detailAddress
     * @param postCode
     * @param phoneNumber
     * @param name
     * @param defaultStatus
     */
    public void addAddress(@Field("province") String province,
                           @Field("city") String city,
                           @Field("region") String region,
                           @Field("detailAddress") String detailAddress,
                           @Field("postCode") String postCode,
                           @Field("phoneNumber") String phoneNumber,
                           @Field("name") String name,
                           @Field("defaultStatus") int defaultStatus) {
        AddressLoader.getInstance().requestAddAddress(province, city, region, detailAddress, postCode, phoneNumber, name, defaultStatus)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onSubmitAddressSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }

    /**
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
     */
    public void updateAddress(@Field("id") long addressId,
                              @Field("memberId") long memberId,
                              @Field("province") String province,
                              @Field("city") String city,
                              @Field("region") String region,
                              @Field("detailAddress") String detailAddress,
                              @Field("postCode") String postCode,
                              @Field("phoneNumber") String phoneNumber,
                              @Field("name") String name,
                              @Field("defaultStatus") int defaultStatus) {
        AddressLoader.getInstance().updateAddress(addressId, memberId, province, city, region, detailAddress,
                postCode, phoneNumber, name, defaultStatus)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onSubmitAddressSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }

    /**
     * 获取行政区地址信息
     */
    public void checkRegionDatas() {
        String json = CacheHelper.getString(CacheKeys.KEY_REGION_DATA + AppContext.getsInstance().getLanguageEntity().getLabel());
        if (TextUtils.isEmpty(json)) {
            AddressLoader.getInstance().requestRegionsData()
                    .compose(showLoadingDialog())
                    .compose(getV().bindToLifecycle())
                    .subscribe(new ApiSubscriber<RegionsResult>(getV()) {
                        @Override
                        public void onSuccess(RegionsResult regionsResult) {
                            CacheHelper.putString(CacheKeys.KEY_REGION_DATA + AppContext.getsInstance().getLanguageEntity().getLabel(),
                                    new Gson().toJson(regionsResult));
                            getV().onRegionDataSucc(regionsResult);
                        }


                        @Override
                        protected void onFail(NetError error) {
                            super.onFail(error);
                            getV().onRegionDataFail();
                        }
                    });
        } else {
            RegionsResult regionsResult = new Gson().fromJson(json, RegionsResult.class);
            getV().onRegionDataSucc(regionsResult);
        }
    }

    /**
     * 获取地址列表
     * 从确认订单界面跳转过来之后新建地址，然后获取唯一地址 带回确认订单界面
     */
    public void requestAddressList() {
        AddressLoader.getInstance().requestAddressList()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<AddressItemBean>>(getV()) {
                    @Override
                    public void onSuccess(List<AddressItemBean> addressItemBeans) {
                        getV().onAddressListSucc(addressItemBeans.get(0));
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
//                        getV().onAddressListFail();
                    }
                });
    }
}
