package com.ysarch.vmall.page.main.presenter;

import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.CartGoodsBean;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResult;
import com.ysarch.vmall.domain.bean.SkuBean;
import com.ysarch.vmall.domain.bean.WholeGenerateOrderResult;
import com.ysarch.vmall.domain.services.CartLoader;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.domain.services.OrderLoader;
import com.ysarch.vmall.page.main.MainCartFragment;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fysong on 24/08/2020
 **/
public class MainCartPresenter extends BasePresenter<MainCartFragment> {
    private static final String TAG = "MainCartPresenter";

    private SparseArray<WeakReference<GoodsDetailBean>> mSparseArray = new SparseArray<>();

    public void requestCartList(boolean showLoading) {
        CartLoader.getInstance().requestCartList()
                .compose(showLoading ? showLoadingDialog() : dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<CartGoodsBean>>(getV()) {
                    @Override
                    public void onSuccess(List<CartGoodsBean> cartGoodsBeans) {
                        for (int i = 0; i < cartGoodsBeans.size(); i++) {
                            CartGoodsBean cartGoodsBean = cartGoodsBeans.get(i);
                            cartGoodsBean.setProductPic(VMallUtils.correctUrl(cartGoodsBean.getProductPic()));
                        }
                        getV().onCartDataSucc(cartGoodsBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCartDataFail();
                    }
                });
    }


    /**
     * 清空购物车
     */
    public void clearCart() {
        CartLoader.getInstance().clearCart()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onClearCartSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }

    /**
     * 清空购物车
     */
    public void updateCartQuantity(int id, int quantity) {
        CartLoader.getInstance().updateCartQuantity(id,quantity)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
//                        getV().onClearCartSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }


    /**
     * 购物车商品删除
     */
    public void deleteCertainCartGoods(List<CartGoodsBean> cartGoodsBeans) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < cartGoodsBeans.size(); i++) {
            ids.add(cartGoodsBeans.get(i).getId());
        }

        getV().showLoadingDialog();
        CartLoader.getInstance().deleteCartByIds(ids)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        getV().dismissLoadingDialog();
                        if (response.body() != null) {
                            try {
//                                Log.i("Mainactivity", " result:" + response.body().string());
                                String content = response.body().string();
                                Log.i(TAG, "deleteCartByIds2 result: " + content);
                                getV().onDeleteCartSucc(cartGoodsBeans);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.i(TAG, "deleteCartByIds2 error: " + e.getMessage());
                                getV().showTs(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "deleteCartByIds2 error: " + t.getMessage());
                        getV().dismissLoadingDialog();
                        getV().showTs(t.getMessage());
                    }
                });
    }


    /**
     * 获取对应商品sku信息
     *
     * @param position
     * @param cartGoodsBean
     */
    public void requestGoodsDetail(int position, CartGoodsBean cartGoodsBean) {
        if (mSparseArray.get(cartGoodsBean.getId()) != null) {
            GoodsDetailBean goodsDetailBean = mSparseArray.get(cartGoodsBean.getId()).get();
            if (goodsDetailBean != null) {
                getV().onDataSuccess(position, cartGoodsBean, goodsDetailBean);
                return;
            }
        }
        GoodsLoader.getInstance().requestGoodsDetail(0, cartGoodsBean.getProductId())
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<GoodsDetailResult>(getV()) {
                    @Override
                    public void onSuccess(GoodsDetailResult goodsDetailResult) {
                        correctUrl(goodsDetailResult);
                        mSparseArray.put(cartGoodsBean.getId(), new WeakReference<>(goodsDetailResult.getItem()));
                        getV().onDataSuccess(position, cartGoodsBean, goodsDetailResult.getItem());
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail();
                    }
                });
    }


    /**
     * 更新sku信息
     *
     * @param position
     * @param goodsDetailBean
     * @param cartGoodsBean
     * @param skuBean
     * @param picImg
     * @param skuLabel
     */
    public void updateCartGoodsSkuData(int position, GoodsDetailBean goodsDetailBean, CartGoodsBean cartGoodsBean,
                                       SkuBean skuBean, String picImg, String skuLabel, int quantity) {
        CartLoader.getInstance().updateCartGoodsAttr(goodsDetailBean, cartGoodsBean, skuBean, picImg, skuLabel, quantity)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        if (skuBean != null) {
                            cartGoodsBean.setProductSkuId(skuBean.getSkuId());
                            cartGoodsBean.setProductAttr(skuLabel);
                            cartGoodsBean.setPrice(skuBean.getPrice());
                            cartGoodsBean.setProductPic(picImg);
                            cartGoodsBean.setQuantity(quantity);
                        }

                        getV().onSkuChangeSucc(position, cartGoodsBean);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }


    public void generateConfirmOrder(List<String> ids) {
        getV().showLoadingDialog();
        OrderLoader.getInstance().generateConfirmOrder(ids)
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
                                WholeGenerateOrderResult generateOrderResult = new Gson().fromJson(content, WholeGenerateOrderResult.class);
                                if (generateOrderResult.getCode() != 200) {
                                    getV().showTs(generateOrderResult.getMessage());
                                } else {
                                    getV().onGenerateConfirmOrderSucc(generateOrderResult.getData());
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                getV().showTs(e.getMessage());
                                Log.i(TAG, "deleteCartByIds2 error: " + e.getMessage());
//                                getV().showTs(e.getMessage());
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


    private void correctUrl(GoodsDetailResult goodsDetailResult) {
        if (goodsDetailResult.getItem() != null
                && CollectionUtils.isNotEmpty(goodsDetailResult.getItem().getPropsImg())) {
            Map<String, String> img = goodsDetailResult.getItem().getPropsImg().get(0);
            for (String key : img.keySet()) {
                img.put(key, VMallUtils.correctUrl(img.get(key)));
            }
        }
    }
}
