package com.ysarch.vmall.page.goods;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.component.dialog.multisku.MultiSelSkuDialog;
import com.ysarch.vmall.domain.bean.AddCartBean;
import com.ysarch.vmall.domain.bean.CommonResult;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResultV2;
import com.ysarch.vmall.domain.bean.SkuBeanV2;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.local.MultiSkuEntity;
import com.ysarch.vmall.domain.services.CouponLoader;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fysong on 22/09/2020
 **/
public class GoodsDetailPresenter extends BasePresenter<GoodsDetailActivity> {

    public List<MultiSkuEntity> mSelMultiSkuEntities = new ArrayList<>();
    private MultiSelSkuDialog.Callback mCallback;

    public MultiSelSkuDialog.Callback getCallback() {
        if (mCallback == null) {
            mCallback = new MultiSelSkuDialog.Callback() {
                @Override
                public void onSkuPriceRangeConfirm(List<MultiSkuEntity> multiSkuEntities, double totalAmount,
                                                   double price, boolean submit) {
                    mSelMultiSkuEntities.clear();
                    if (CollectionUtils.isNotEmpty(multiSkuEntities))
                        mSelMultiSkuEntities.addAll(multiSkuEntities);
                    if (submit)
                        requestAddMultiSkuCart(mSelMultiSkuEntities, totalAmount, price);
                }

                @Override
                public void onSkuPriceFixedConfirm(List<MultiSkuEntity> multiSkuEntities, double totalAmount,
                                                   boolean submit) {
                    mSelMultiSkuEntities.clear();
                    if (CollectionUtils.isNotEmpty(multiSkuEntities))
                        mSelMultiSkuEntities.addAll(multiSkuEntities);
                    if (submit)
                        requestAddMultiSkuCart(mSelMultiSkuEntities, totalAmount, 0);
                }

                @Override
                public void onNoneSkuConfirm(MultiSkuEntity multiSkuEntity, double totalAmount, double price,
                                             boolean submit) {
                    mSelMultiSkuEntities.clear();
                    if (multiSkuEntity != null) {
                        mSelMultiSkuEntities.add(multiSkuEntity);
                    }

                    if (submit)
                        requestAddMultiSkuCart(mSelMultiSkuEntities, totalAmount, price);
                }
            };
        }
        return mCallback;
    }


    public void requestGoodsDetail(long goodsId, int platformType) {
        GoodsLoader.getInstance().requestGoodsDetailV2(platformType, goodsId)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<GoodsDetailResultV2>(getV()) {
                    @Override
                    public void onSuccess(GoodsDetailResultV2 goodsDetailResult) {
                        correctUrl(goodsDetailResult);
                        getV().onDataSuccess(goodsDetailResult);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail();
                    }
                });
    }

    private void correctUrl(GoodsDetailResultV2 goodsDetailResult) {

        if (goodsDetailResult.getData() != null && goodsDetailResult.getData().getItem() != null) {
            if (CollectionUtils.isNotEmpty(goodsDetailResult.getData().getItem().getDescImgs())) {
                List<String> urls = goodsDetailResult.getData().getItem().getDescImgs();
                Iterator<String> iterator = urls.iterator();
                while (iterator.hasNext()) {
                    String url = iterator.next();
                    if (url.toLowerCase().contains(".gif")) {
                        iterator.remove();
                    }
                }
                for (int i = 0; i < urls.size(); i++) {
                    urls.set(i, VMallUtils.correctUrl(urls.get(i)));
                }
            }

            if (CollectionUtils.isNotEmpty(goodsDetailResult.getData().getItem().getImages())) {
                List<String> urls = goodsDetailResult.getData().getItem().getImages();
                Iterator<String> iterator = urls.iterator();
                while (iterator.hasNext()) {
                    String url = iterator.next();
                    if (url.toLowerCase().contains(".gif")) {
                        iterator.remove();
                    }
                }
                for (int i = 0; i < urls.size(); i++) {
                    urls.set(i, VMallUtils.correctUrl(urls.get(i)));
                }
            }

            if (CollectionUtils.isNotEmpty(goodsDetailResult.getData().getItem().getProps())) {
                List<GoodsDetailItemBean.PropsBean> propsBeans = goodsDetailResult.getData().getItem().getProps();
                if (CollectionUtils.isNotEmpty(propsBeans)) {
                    for (int i = 0; i < propsBeans.size(); i++) {
                        GoodsDetailItemBean.PropsBean propsBean = propsBeans.get(i);
                        List<GoodsDetailItemBean.PropsBean.ValuesBean> valuesBeans = propsBean.getValues();
                        if (CollectionUtils.isNotEmpty(valuesBeans)) {
                            for (int j = 0; j < valuesBeans.size(); j++) {
                                if (!TextUtils.isEmpty(valuesBeans.get(j).getImage())) {
                                    valuesBeans.get(j).setImage(VMallUtils.correctUrl(valuesBeans.get(j).getImage()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加商品到购物车
     *
     * @param goodsDetailBean
     * @param skuBean
     * @param skuLabel
     * @param img
     * @param quantity
     */
    public void requestAddCart(GoodsDetailItemBean goodsDetailBean, SkuBeanV2 skuBean,
                               String skuLabel, String img, int quantity, int platformType) {
        if (TextUtils.isEmpty(img)) {
            if (CollectionUtils.isNotEmpty(goodsDetailBean.getImages())) {
                img = goodsDetailBean.getImages().get(0);
            }
        }
        GoodsLoader.getInstance().requestAddCartV2(goodsDetailBean, skuBean, img, skuLabel, quantity, platformType)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        if(o instanceof Number) {
                            getV().onAddCartSucc(((Number) o).intValue());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }

    /**
     * 获取优惠券数据
     *
     * @param goodsId
     */
    public void requestCouponList(long goodsId) {
        CouponLoader.getInstance().requestCouponList(goodsId)
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<CouponBean>>() {
                    @Override
                    public void onSuccess(List<CouponBean> couponBeans) {
                        getV().onCouponSucc(couponBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCouponFail();
                    }
                });
    }


    /**
     * 领取优惠券
     *
     * @param couponBean
     */
    public void drawCoupon(CouponBean couponBean) {
        CouponLoader.getInstance().drawCoupon(couponBean.getId())
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().showTs(ResUtils.getString(R.string.tip_draw_succ));
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }


    /**
     * 1688 添加至购物车
     *
     * @param multiSkuEntities
     * @param totalAmount
     * @param price
     */
    public void requestAddMultiSkuCart(List<MultiSkuEntity> multiSkuEntities, double totalAmount, double price) {
        if (CollectionUtils.isEmpty(mSelMultiSkuEntities))
            return;

        getV().showLoadingDialog();

        List<AddCartBean> addCartBeanList = new ArrayList<>();
        for (int i = 0; i < multiSkuEntities.size(); i++) {
            MultiSkuEntity entity = multiSkuEntities.get(i);
            SkuBeanV2 skuBeanV2 = entity.getSkuBeanV2();
            AddCartBean addCartBean = new AddCartBean();
            if (skuBeanV2 != null) {
                if (skuBeanV2.getFloatPrice() > 0)
                    addCartBean.setPrice(skuBeanV2.getFloatPrice());
                else
                    addCartBean.setPrice(price);
                addCartBean.setProductAttr(skuBeanV2.getPropPath());
                addCartBean.setProductSkuId(skuBeanV2.getSkuId());
            }
            addCartBean.setProductCategoryId(getV().mGoodsDetailBean.getCategoryId());
            addCartBean.setProductId(getV().mGoodsDetailBean.getItemId());
            addCartBean.setProductName(getV().mGoodsDetailBean.getTitle());
            addCartBean.setProductPic(entity.getPic());
            addCartBean.setQuantity(entity.getAmount());
            addCartBean.setSource(Constants.TYPE_PLATFORM_1688);
            addCartBeanList.add(addCartBean);
        }


        GoodsLoader.getInstance().requestAdd1688Cart(addCartBeanList)
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
                                if (TextUtils.isEmpty(content))
                                    return;

                                Gson gson = new Gson();
                                CommonResult commonResult =
                                        gson.fromJson(content, CommonResult.class);

                                if (commonResult.getCode() == 200) {
                                    getV().onAddCartSucc(0);
                                } else {
                                    getV().showTs(commonResult.getMessage());
                                }

                            } catch (IOException e) {
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
    }

}
