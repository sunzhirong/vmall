package com.ysarch.vmall.domain.services;

import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.CartGoodsBean;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.SkuBean;

import org.json.JSONArray;

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
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by fysong on 25/09/2020
 **/
public class CartLoader extends ObjectLoader {

    private CartService mService;

    private CartLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), CartService.class);
    }

    public static CartLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 购物车列表
     *
     * @return
     */
    public Flowable<SimpleResponse<List<List<CartGoodsBean>>>> requestCartList() {
        return mService.requestCartList();
    }


    /**
     * 清空购物车
     *
     * @return
     */
    public Flowable<SimpleResponse<Object>> clearCart() {
        return mService.clearCart(0);
    }


    /**
     * 修改购物车数量
     * @return
     */
    public Flowable<SimpleResponse<Object>> updateCartQuantity(int id, int quantity) {
        return mService.updateCartQuantity(id,quantity);
    }


    /**
     * 删除部分商品
     *
     * @param ids
     * @return
     */
    public Call<ResponseBody> deleteCartByIds(List<Integer> ids) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < ids.size(); i++) {
            jsonArray.put(ids.get(i));
        }
        return mService.deleteCartByIds(RequestBody.create(JSON_TYPE, jsonArray.toString()));
    }


    /**
     * 更改购物车中的商品数量
     *
     * @param id
     * @param quantity
     * @return
     */
    public Flowable<SimpleResponse<Object>> changeCartGoodsQuantity(@Query("id") String id, @Query("quantity") int quantity) {
        return mService.changeCartGoodsQuantity(id, quantity);
    }


    /**
     * 更新购物车商品sku信息
     *
     * @param goodsDetailBean
     * @param cartGoodsBean
     * @param skuBean
     * @param picImg
     * @param skuLabel
     * @return
     */
    public Flowable<SimpleResponse<Object>> updateCartGoodsAttr(GoodsDetailBean goodsDetailBean, CartGoodsBean cartGoodsBean,
                                                                SkuBean skuBean, String picImg, String skuLabel,int quantity) {
        if (skuBean != null) {
            return mService.updateCartGoodsAttr(cartGoodsBean.getId(), skuBean.getPrice(), goodsDetailBean.getBrand(),
                    goodsDetailBean.getRootCatId(), goodsDetailBean.getId(), goodsDetailBean.getTitle(),
                    skuBean.getSkuId(), skuLabel, picImg, quantity, 0);
        } else {
            return mService.updateCartGoodsAttr(cartGoodsBean.getId(), goodsDetailBean.getPrice(), goodsDetailBean.getBrand(),
                    goodsDetailBean.getRootCatId(), goodsDetailBean.getId(), goodsDetailBean.getTitle(),
                    "", "", picImg, quantity, 0);
        }

    }

//    /**
//     * 更新购物车商品sku信息
//     *
//     * @param cartGoodsId
//     * @param price
//     * @param productBrand
//     * @param productCategoryId
//     * @param productId
//     * @param productName
//     * @param productSkuId
//     * @param productSkuName
//     * @param productImg
//     * @param quantity
//     * @param source
//     * @return
//     */
//    public Flowable<SimpleResponse<Object>> updateCartGoodsAttr(@Field("id") float cartGoodsId, @Field("price") float price,
//                                                                @Field("productBrand") String productBrand,
//                                                                @Field("productCategoryId") String productCategoryId,
//                                                                @Field("productId") String productId,
//                                                                @Field("productName") String productName,
//                                                                @Field("productSkuId") String productSkuId,
//                                                                @Field("productAttr") String productSkuName,
//                                                                @Field("productPic") String productImg,
//                                                                @Field("quantity") int quantity,
//                                                                @Field("source") int source) {
//        return mService.updateCartGoodsAttr(cartGoodsId, price, productBrand, productCategoryId, productId, productName,
//                productSkuId, productSkuName, productImg, quantity, source);
//    }

    interface CartService {

//        @GET("/cart/list")
        @GET("/cart/listV2")
        Flowable<SimpleResponse<List<List<CartGoodsBean>>>> requestCartList();

        @FormUrlEncoded
        @POST("/cart/clear")
        Flowable<SimpleResponse<Object>> clearCart(@Field("play") int play);


//        @FormUrlEncoded
//        @POST("/cart/delete")
//        Flowable<SimpleResponse<Object>> deleteCartByIds(@Body int[] ids, @Field("e") int e);

        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("/cart/delete")
        Call<ResponseBody> deleteCartByIds(@Body RequestBody body);


        @GET("/cart/update/quantity")
        Flowable<SimpleResponse<Object>> changeCartGoodsQuantity(@Query("id") String id, @Query("quantity") int quantity);

        @FormUrlEncoded
        @POST("/cart/add")
        Flowable<SimpleResponse<Object>> requestAddCart(@Field("price") float price, @Field("productBrand") String productBrand,
                                                        @Field("productCategoryId") String productCategoryId,
                                                        @Field("productId") String productId, @Field("productName") String productName,
                                                        @Field("productSkuId") String productSkuId,
                                                        @Field("productAttr") String productSkuName,
                                                        @Field("productPic") String productImg,
                                                        @Field("quantity") int quantity,
                                                        @Field("source") int source);

        @FormUrlEncoded
        @POST("/cart/update/attr")
        Flowable<SimpleResponse<Object>> updateCartGoodsAttr(@Field("id") long cartGoodsId, @Field("price") double price, @Field("productBrand") String productBrand,
                                                             @Field("productCategoryId") String productCategoryId,
                                                             @Field("productId") String productId, @Field("productName") String productName,
                                                             @Field("productSkuId") String productSkuId,
                                                             @Field("productAttr") String productSkuName,
                                                             @Field("productPic") String productImg,
                                                             @Field("quantity") int quantity,
                                                             @Field("source") int source);

        @GET("/cart/update/quantity")
        Flowable<SimpleResponse<Object>> updateCartQuantity(@Query("id") int id,@Query("quantity") int quantity);
    }


    static class SingletonHolder {
        static CartLoader INSTANCE = new CartLoader();
    }


}
