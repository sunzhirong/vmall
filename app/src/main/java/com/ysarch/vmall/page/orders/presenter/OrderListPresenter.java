package com.ysarch.vmall.page.orders.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.BaseResult;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.services.OrderLoader;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.orders.OrderListFragment;
import com.yslibrary.event.EventCenter;
import com.yslibrary.utils.CollectionUtils;

import java.io.IOException;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by fysong on 15/09/2020
 **/
public class OrderListPresenter extends BasePresenter<OrderListFragment> {

    private boolean mHasMore = true;

    public boolean isHasMore() {
        return mHasMore;
    }

    /**
     * 获取订单数据
     *
     * @param status
     * @param page
     * @param showLoading
     */
    public void requestOrderList(int status, int page, boolean showLoading) {
        OrderLoader.getInstance().requestOrderList(status, page, Constants.COUNT_PER_PAGE)
                .compose(showLoading ? showLoadingDialog() : dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<ListResult<OrderBean>>(getV()) {
                    @Override
                    public void onSuccess(ListResult<OrderBean> orderBeanListResult) {
                        if (CollectionUtils.isNotEmpty(orderBeanListResult.getList())) {
                            mHasMore = orderBeanListResult.getTotalPage() > page;
                        } else {
                            mHasMore = false;
                        }
                        getV().onOrderListSucc(page, orderBeanListResult.getList());
//                        getV().resetUIStatus(page, true);

                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().resetUIStatus(page, false);
                    }
                });
    }

    /**
     * 取消订单
     */
    public void cancelOrder(int position, OrderBean orderBean,int cancelReason){
        getV().showLoadingDialog();
        OrderLoader.getInstance().cancelOrder(orderBean.getId(),cancelReason)
               .enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       getV().dismissLoadingDialog();
                       if(response.errorBody() != null){
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
                                   getV().onCancelSucc(position, orderBean);
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
     * @param position
     * @param orderBean
     */
    public void deleteOrder(int position, OrderBean orderBean ){
        getV().showLoadingDialog();
        OrderLoader.getInstance().deleteOrder(orderBean.getId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        getV().dismissLoadingDialog();
                        if(response.errorBody() != null){
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
                                    getV().onDeleteSucc(position, orderBean);
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
}
