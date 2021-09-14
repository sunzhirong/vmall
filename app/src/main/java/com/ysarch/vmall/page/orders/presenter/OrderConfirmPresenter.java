package com.ysarch.vmall.page.orders.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.EnumBean;
import com.ysarch.vmall.domain.bean.GenerateOrderResult;
import com.ysarch.vmall.domain.bean.UpdateCartBean;
import com.ysarch.vmall.domain.bean.WholeGenerateOrderResult;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.services.OrderLoader;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.orders.OrderConfirmFragment;
import com.yslibrary.event.EventCenter;
import com.yslibrary.utils.CollectionUtils;

import org.json.JSONException;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by fysong on 28/09/2020
 **/
public class OrderConfirmPresenter extends BasePresenter<OrderConfirmFragment> {

    //    public void generateOrder(List<Integer> ids, long couponId, long addressId, int payType, int useIntegration) {
    public void generateOrder(List<Long> ids, long couponId,long historyCouponId,
                              long addressId,
                              int payType, int deliveryType,int shippingMethod, int shippingType, int warehouse,
                              int useIntegration, String remark) {
        getV().showLoadingDialog();
        //List<Integer> cartIds, long couponId,
        //                                              long memberReceiveAddressId,
        //                                              int payType, int deliveryType, int shippingType, int warehouse,
        //                                              int useIntegration, String remark

        try {
            OrderLoader.getInstance().generateOrderV2(ids, couponId,  historyCouponId,addressId, payType, deliveryType,
                    shippingMethod,shippingType, warehouse, useIntegration, remark)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            getV().dismissLoadingDialog();
                            if (response.errorBody() != null) {
                                getV().showTs(response.errorBody().toString());
                                return;
                            }
                            if (response.body() != null) {
                                try {
                                    String content = response.body().string();
                                    GenerateOrderResult result = new Gson().fromJson(content, GenerateOrderResult.class);

                                    if (result.getCode() != 200) {
                                        getV().showTs(result.getMessage());
                                    } else {
                                        getV().onOrderConfirmSucc(result);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    getV().showTs(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            getV().dismissLoadingDialog();
                            getV().showTs(t.getMessage());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
            getV().dismissLoadingDialog();
            getV().showTs(e.getMessage());
        }
    }

    public void updateConfirmOrder(List<UpdateCartBean> ids, int couponHistoryId) {
        getV().showLoadingDialog();

        try {
            OrderLoader.getInstance().updateConfirmOrder(ids, couponHistoryId)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            getV().dismissLoadingDialog();
                            if (response.errorBody() != null) {
                                getV().showTs(response.errorBody().toString());
                                return;
                            }
                            if (response.body() != null) {
                                try {
                                    String content = response.body().string();
                                    WholeGenerateOrderResult result = new Gson().fromJson(content, WholeGenerateOrderResult.class);

                                    if (result.getCode() != 200) {
                                        getV().showTs(result.getMessage());
                                    } else {
                                        getV().onOrderUpdateSucc(result);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    getV().showTs(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            getV().dismissLoadingDialog();
                            getV().showTs(t.getMessage());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            getV().dismissLoadingDialog();
            getV().showTs(e.getMessage());
        }
    }



    /**
     * 获取仓库信息
     */
    public void requestWarehouseData() {
        if (CollectionUtils.isNotEmpty(AppContext.getsInstance().getWarehouseDatas())) {
            getV().onWarehouseData(AppContext.getsInstance().getWarehouseDatas());
            return;
        }
        OrderLoader.getInstance().requestWareHouseInfo()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<String>(getV()) {
                    @Override
                    public void onSuccess(String enumBeansStr) {
                        if (parseWarehouseData(enumBeansStr)) {
                            CacheHelper.putString(CacheKeys.KEY_WAREHOUSE_DATA, enumBeansStr);
                        }
                        getV().dismissLoadingDialog();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        String enumBeansStr = CacheHelper.getString(CacheKeys.KEY_WAREHOUSE_DATA);
                        if (!parseWarehouseData(enumBeansStr)) {
                            getV().showTs(error.getMessage());
                        }
                        getV().dismissLoadingDialog();
                    }
                });
    }


    /**
     * 解析仓库数据
     *
     * @param enumStr
     * @return
     */
    private boolean parseWarehouseData(String enumStr) {
        if (!TextUtils.isEmpty(enumStr)) {
            List<EnumBean> enumBeans = new Gson().fromJson(enumStr,
                    new TypeToken<List<EnumBean>>() {
                    }.getType());
            if (CollectionUtils.isNotEmpty(enumBeans)) {
                AppContext.getsInstance().setWarehouseDatas(enumBeans);
                getV().onWarehouseData(enumBeans);
                return true;
            }
        }

        return false;
    }



    /**
     * 校验钱包余额
     */
    public void checkWallet() {
        WalletLoader.getInstance().checkWallet()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Float>(getV()) {
                    @Override
                    public void onSuccess(Float integer) {
                        UserInfoManager.getUser().setWallet(integer);
                        getV().onWalletChecked(integer);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onWalletChecked(0);
                    }
                });
    }

    /**
     * 支付
     *
     * @param orderId
     * @param orderSn
     * @param payPassword
     */
    public void payByBalance(@Field("orderId") long orderId, @Field("orderSn") String orderSn,
                             @Field("payPassword") String payPassword) {
        WalletLoader.getInstance().payByBalance(orderId, orderSn, payPassword)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        EventCenter.getInstance().notify(NotificationDef.EVENT_ORDER_STATUS_CHANGED);
                        getV().onPaySucc();
                    }

                    @Override
                    protected void onPayPwdError() {
                        getV().onPwdError();
                    }

                });
    }

}
