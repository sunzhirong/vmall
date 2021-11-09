package com.ysarch.vmall.page.orders.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.BaseResult;
import com.ysarch.vmall.domain.bean.EnumBean;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.services.OrderLoader;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.orders.OrderDetailFragment;
import com.yslibrary.event.EventCenter;
import com.yslibrary.utils.CollectionUtils;

import java.io.IOException;
import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by fysong on 30/09/2020
 **/
public class OrderDetailPresenter extends BasePresenter<OrderDetailFragment> {


    public void requestOrderDetail(long orderId) {
        OrderLoader.getInstance().requestOrderDetail(orderId)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<OrderBean>(getV()) {
                    @Override
                    public void onSuccess(OrderBean orderBean) {
                        getV().onDataSucc(orderBean);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail();
                    }
                });
    }

    /**
     * 取消订单
     *
     */
    public void cancelOrder(OrderBean orderBean,int cancelReason) {
        getV().showLoadingDialog();
        OrderLoader.getInstance().cancelOrder(orderBean.getId(),cancelReason)
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
                                BaseResult<Object> generateOrderResult = new Gson().fromJson(content, BaseResult.class);
                                if (generateOrderResult.getCode() != 200) {
                                    getV().showTs(generateOrderResult.getMessage());
                                } else {
                                    orderBean.setStatus(Constants.STATUS_ORDER_CLOSED);
                                    EventCenter.getInstance().notify(NotificationDef.EVENT_ORDER_STATUS_CHANGED);
                                    getV().onCancelSucc(orderBean);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                getV().dismissLoadingDialog();
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
    }


    /**
     * 删除订单
     *
     * @param orderBean
     */
    public void deleteOrder(OrderBean orderBean) {
        getV().showLoadingDialog();
        OrderLoader.getInstance().deleteOrder(orderBean.getId())
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
                                BaseResult<Object> generateOrderResult = new Gson().fromJson(content, BaseResult.class);
                                if (generateOrderResult.getCode() != 200) {
                                    getV().showTs(generateOrderResult.getMessage());
                                } else {
                                    EventCenter.getInstance().notify(NotificationDef.EVENT_ORDER_STATUS_CHANGED);
                                    getV().onDeleteSucc(orderBean);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                getV().dismissLoadingDialog();
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

    /**
     * 支付运费
     *
     * @param orderId
     * @param orderSn
     * @param payPassword
     */
    public void payFreightByBalance(@Field("orderId") long orderId, @Field("orderSn") String orderSn,
                             @Field("payPassword") String payPassword) {
        WalletLoader.getInstance().payFreightByBalance(orderId, orderSn, payPassword)
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
                        Log.e("12311111--aa","123");
                    }
                });
    }


    /**
     * 确认收货
     * @param orderId
     */
    public void confirmReceiveOrder(@Field("orderId") long orderId) {
        getV().showLoadingDialog();
        OrderLoader.getInstance().confirmReceiveOrder(orderId)
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
                                BaseResult<Object> generateOrderResult = new Gson().fromJson(content, BaseResult.class);
                                if (generateOrderResult.getCode() != 200) {
                                    getV().showTs(generateOrderResult.getMessage());
                                } else {
                                    EventCenter.getInstance().notify(NotificationDef.EVENT_ORDER_STATUS_CHANGED);
                                    getV().onConfirmSucc();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                getV().dismissLoadingDialog();
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


}
