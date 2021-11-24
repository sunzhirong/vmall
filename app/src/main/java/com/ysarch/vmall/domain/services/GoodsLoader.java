package com.ysarch.vmall.domain.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ysarch.vmall.domain.ApiUrl;
import com.ysarch.vmall.domain.bean.AddCartBean;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResult;
import com.ysarch.vmall.domain.bean.GoodsDetailResultV2;
import com.ysarch.vmall.domain.bean.GoodsItemBean;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.HomeContentResult;
import com.ysarch.vmall.domain.bean.HomeRecResult;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.bean.SkuBeanV2;
import com.ysarch.vmall.domain.constant.Constants;

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
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by fysong on 18/09/2020
 **/
public class GoodsLoader extends ObjectLoader {
    private GoodsService mService;
//    private TestService mTestService;

    public GoodsLoader() {
        mService = getRetrifitService(ApiUrl.getBaseApiUrl(), GoodsService.class);
//        mTestService = getRetrifitService("http://sdk.9game.cn/", TestService.class);
    }

    public static GoodsLoader getInstance() {
        return SingletonHolder.INSTANCE;
    }

//    /**
//     * 获取最受欢迎页面的头部数据
//     *
//     * @return
//     */
//    public Flowable<SimpleResponse<Object>> requestHomeHeaderDatas() {
//        return mService.requestHomeHeaderDatas();
//    }

    /**
     * 分类数据
     *
     * @param cateId
     * @param page
     * @param numPerPage
     * @return
     */
    public Flowable<SimpleResponse<ListResult<GoodsItemBean>>> requestSubjectList(@Query("cateId") int cateId,
                                                                                  @Query("pageNum") int page,
                                                                                  @Query("pageSize") int numPerPage) {
        return mService.requestSubjectList(cateId, page, Constants.COUNT_PER_PAGE);
    }

    /**
     * 获取商品分类列表
     *
     * @return
     */
    public Flowable<SimpleResponse<List<CateLevelBean>>> requestCateTree() {
        return mService.requestCateTree();
    }

    /**
     * 获取首页分类列表
     *
     * @return
     */
    public Flowable<SimpleResponse<List<CateLevelBean>>> requestHeaderCateTree() {
        return mService.requestHeaderCateTree();
    }


    /**
     * 最受欢迎页面推荐商品
     *
     * @return
     */
    public Flowable<SimpleResponse<ListResult<GoodsItemBeanV2>>> requestRecommendGoods(int page) {
//        return mService.requestRecommendGoods();
        return mService.searchGoodsV2(null, page, Constants.TYPE_PLATFORM_TB, Constants.COUNT_PER_PAGE_GRID, false);
    }


    /**
     * 类目下的商品数据
     *
     * @param cateId
     * @return
     */
    public Flowable<SimpleResponse<List<GoodsItemBean>>> productCateList(int cateId) {
        return mService.productCateList(cateId);
    }


    /**
     * 类目下的商品数据
     *
     * @param cateId
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SimpleResponse<ListResult<GoodsItemBean>>> requestCateGoods(int cateId,
                                                                                int page,
                                                                                int pageSize) {
        return mService.requestCateGoods(cateId, page, pageSize);
    }


    /**
     * 综合商品搜索
     *
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    @Deprecated
    public Flowable<SimpleResponse<ListResult<GoodsItemBean>>> searchGoods(String keyword,
                                                                           int page,
                                                                           int pageSize) {
        return mService.searchGoods(keyword, page, pageSize);
    }

    /**
     * 综合商品搜索
     *
     * @param keyword
     * @param page
     * @param platformType
     * @return
     */
    public Flowable<SimpleResponse<ListResult<GoodsItemBeanV2>>> searchGoodsV2(
            @Query("query") String keyword,
            @Query("page") int page,
            @Query("type") int platformType, boolean onCache) {
        return mService.searchGoodsV2(keyword, page, platformType, Constants.COUNT_PER_PAGE_GRID,onCache);
    }

    /**
     * 对应类型的商品搜索
     *
     * @param cateId
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    public Flowable<SimpleResponse<ListResult<GoodsItemBean>>> searchCateGoods(@Query("catId") int cateId,
                                                                               @Query("keyword") String keyword,
                                                                               @Query("page") int page,
                                                                               @Query("pageSize") int pageSize) {
        return mService.searchCateGoods(cateId, keyword, page, pageSize);
    }


    /**
     * 商品详情
     *
     * @param type
     * @param goodsId
     * @return
     */
    @Deprecated
    public Flowable<SimpleResponse<GoodsDetailResult>> requestGoodsDetail(@Path("type") int type, @Path("itemId") long goodsId) {
        return mService.requestGoodsDetail(type, goodsId);
    }

    /**
     * 商品详情
     *
     * @param type
     * @param goodsId
     * @return
     */
    public Flowable<SimpleResponse<GoodsDetailResultV2>> requestGoodsDetailV2(@Path("type") int type, @Path("itemId") long goodsId) {
        return mService.requestGoodsDetailV2(type, goodsId);
    }

    /**
     * 最受欢迎页面的banner、营销数据
     *
     * @return
     */
    public Flowable<SimpleResponse<HomeContentResult>> requestWelcomeContent() {
        return mService.requestWelcomeContent();
    }


//    public Flowable<SimpleResponse<Object>> requestAddCart(GoodsDetailBean goodsDetailBean, SkuBean skuBean, String picImg, String skuLabel, int quantity) {
////        if (skuBean != null)
////            return mService.requestAddCart(skuBean.getPrice(), goodsDetailBean.getBrand(), goodsDetailBean.getRootCatId(),
////                    goodsDetailBean.getId(), goodsDetailBean.getTitle(), skuBean.getSkuId(), skuLabel, picImg, quantity, 0);
////        else
////            return mService.requestAddCart(goodsDetailBean.getPrice(), goodsDetailBean.getBrand(), goodsDetailBean.getRootCatId(),
////                    goodsDetailBean.getId(), goodsDetailBean.getTitle(), "", "", picImg, quantity, 0);
//    }

    /**
     * 添加单品至购物车
     *
     * @param goodsDetailBean
     * @param skuBean
     * @param picImg
     * @param skuLabel
     * @param quantity
     * @return
     */
    public Flowable<SimpleResponse<Object>> requestAddCartV2(GoodsDetailItemBean goodsDetailBean, SkuBeanV2 skuBean, String picImg,
                                                             String skuLabel, int quantity, int platformType) {
        if (skuBean != null)
            return mService.requestAddCart(Float.parseFloat(goodsDetailBean.getDollarPrice()), "", goodsDetailBean.getCategoryId(),
                    goodsDetailBean.getItemId(), goodsDetailBean.getTitle(), skuBean.getSkuId(), skuLabel, picImg, quantity, platformType);
        else
            return mService.requestAddCart(Float.parseFloat(goodsDetailBean.getDollarPrice()), "",
                    goodsDetailBean.getCategoryId(),
                    goodsDetailBean.getItemId(), goodsDetailBean.getTitle(), "0", "",
                    picImg, quantity, platformType);
    }


    /**
     * 添加购物车
     *
     * @param addCartBeans
     * @return
     */
    public Call<ResponseBody> requestAdd1688Cart(List<AddCartBean> addCartBeans) {
        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();

        /*
         * price = 158;
         * * productAttr = "[{\"\U5c3a\U7801\":\"37\"},{\"\U989c\U8272\":\"YQ01\"}]";
         * * productBrand = 803456557;
         * * productId = 43986072195;
         * * productName = "\U79cb\U5b63\U7537\U58eb\U957f\U8896\U886c\U886b\U5168\U68c9\U514d\U70eb\U5546\U52a1\U6b63\U88c5\U4e2d\U5e74\U7eaf\U68c9\U6761\U7eb9\U5927\U7801\U804c\U4e1a\U5de5\U88c5\U886c\U8863";
         * * productPic = "//img.alicdn.com/imgextra/i3/2412317789/TB20qmfj29TBuNjy0FcXXbeiFXa_!!2412317789.jpg";
         * * productSkuId = 3640406291012;
         * * quantity = 1;
         * * source = 0;
         * * productCategoryId=""*/
        for (int i = 0; i < addCartBeans.size(); i++) {
            AddCartBean addCartBean = addCartBeans.get(i);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("price", addCartBean.getPrice());
            jsonObject.addProperty("productAttr", addCartBean.getProductAttr());
            jsonObject.addProperty("productId", addCartBean.getProductId());
            jsonObject.addProperty("productName", addCartBean.getProductName());
            jsonObject.addProperty("productPic", addCartBean.getProductPic());
            jsonObject.addProperty("productSkuId", addCartBean.getProductSkuId());
            jsonObject.addProperty("quantity", addCartBean.getQuantity());
            jsonObject.addProperty("source", addCartBean.getSource());
            jsonObject.addProperty("productCategoryId", addCartBean.getProductCategoryId());
            jsonArray.put(jsonObject);
        }

        String js = jsonArray.toString();
        js = js.replaceAll("\"\\{", "{");
        js = js.replaceAll("\\}\"", "}");
        js = js.replaceAll("\\\\\"", "\"");

        return mService.requestAdd1688Cart(RequestBody.create(JSON_TYPE, js));
    }


    /**
     * 解析复制内容
     *
     * @param content
     * @return
     */
    public Call<ResponseBody> analyzeTBShareCmd(String content) {
        return mService.analyzeTBShareCmd(RequestBody.create(JSON_TYPE, content));
    }


    interface GoodsService {
//        @GET("/product/categoryTreeList")
        @GET("/product/categoryTreeThreeLevel")
        Flowable<SimpleResponse<List<CateLevelBean>>> requestCateTree();

        @GET("/product/categoryTreeList")
        Flowable<SimpleResponse<List<CateLevelBean>>> requestHeaderCateTree();

        @GET("/home/content")
        Flowable<SimpleResponse<Object>> requestHomeHeaderDatas();

        @GET("/home/subjectList")
        Flowable<SimpleResponse<ListResult<GoodsItemBean>>> requestSubjectList(@Query("cateId") int cateId,
                                                                               @Query("pageNum") int page,
                                                                               @Query("pageSize") int numPerPage);


        @GET("/home/recommendProductList")
        Flowable<SimpleResponse<HomeRecResult>> requestRecommendGoods();

        @GET("/home/productCateList/{parentId}")
        Flowable<SimpleResponse<List<GoodsItemBean>>> productCateList(@Path("parentId") int cateId);


        @GET("/home/subjectList")
        Flowable<SimpleResponse<ListResult<GoodsItemBean>>> requestCateGoods(@Query("cateId") int cateId,
                                                                             @Query("pageNum") int page,
                                                                             @Query("pageSize") int pageSize);


        @GET("/product/search")
        Flowable<SimpleResponse<ListResult<GoodsItemBean>>> searchRecGoods(
                @Query("keyword") String keyword,
                @Query("page") int page,
                @Query("pageSize") int pageSize);

        @GET("/product/search")
        Flowable<SimpleResponse<ListResult<GoodsItemBean>>> searchGoods(
                @Query("keyword") String keyword,
                @Query("page") int page,
                @Query("pageSize") int pageSize);

        @GET("/product/searchV2")
        Flowable<SimpleResponse<ListResult<GoodsItemBeanV2>>> searchGoodsV2(
                @Query("query") String keyword,
                @Query("page") int page,
                @Query("type") int platformType,
                @Query("pageSize") int pageSize,
                @Query("noCache") boolean noCache);

        @GET("/product/search")
        Flowable<SimpleResponse<ListResult<GoodsItemBean>>> searchCateGoods(@Query("catId") int cateId,
                                                                            @Query("keyword") String keyword,
                                                                            @Query("page") int page,
                                                                            @Query("pageSize") int pageSize);


        @GET("/product/detail/{type}/{itemId}")
        Flowable<SimpleResponse<GoodsDetailResult>> requestGoodsDetail(@Path("type") int type, @Path("itemId") long goodsId);

        @GET("/product/detailV2/{type}/{itemId}")
        Flowable<SimpleResponse<GoodsDetailResultV2>> requestGoodsDetailV2(@Path("type") int type, @Path("itemId") long goodsId);

        @GET("/home/content")
        Flowable<SimpleResponse<HomeContentResult>> requestWelcomeContent();


        /**
         * {
         *   "createDate": "2020-09-24T07:40:17.073Z",
         *   "deleteStatus": 0,
         *   "id": 0,
         *   "memberId": 0,
         *   "memberNickname": "string",
         *   "modifyDate": "2020-09-24T07:40:17.073Z",
         *   "price": 0,
         *   "productAttr": "string",
         *   "productBrand": "string",
         *   "productCategoryId": 0,
         *   "productId": 0,
         *   "productName": "string",
         *   "productPic": "string",
         *   "productSkuCode": "string",
         *   "productSkuId": 0,
         *   "productSn": "string",
         *   "productSubTitle": "string",
         *   "quantity": 0,
         *   "source": 0
         * }*/

        /**
         * {
         * lang = 0;
         * price = 198;
         * productAttr = "[{\"\U5c3a\U7801\":\"31\"},{\"\U989c\U8272\":\"\U7070\U8272\"}]";
         * productBrand = 3253120;
         * productId = 546970807416;
         * productName = "a.llegr \U9ad8\U7aef \U6761\U7eb9\U68c9\U5f39  \U4fa7\U888b\U5c0f\U76f4\U7b52\U4f11\U95f2\U7537\U88e4 \U6625\U590f\U65e9\U79cb";
         * productPic = "//img.alicdn.com/imgextra/i4/269521112/TB2_TQPi4xmpuFjSZFNXXXrRXXa_!!269521112.jpg";
         * productSkuId = 3473206115169;
         * productSubTitle = "";
         * quantity = 1;
         * source = 0;
         * }
         */

       /* @FormUrlEncoded
        @POST("/cart/add")
        Flowable<SimpleResponse<Object>> requestAddCart(@Field("memberId") String memberId, @Field("memberNickname") String memberNickname,
                                                        @Field("price") float price,
                                                        @Field("productAttr") String productAttr, @Field("productBrand") String productBrand,
                                                        @Field("productCategoryId") String productCategoryId,
                                                        @Field("productId") String productId, @Field("productName") String productName,
                                                        @Field("productSkuId") String productSkuId, @Field("quantity") int quantity, @Field("source") int source);*/

        /**
         * {
         * price = 158;
         * productAttr = "[{\"\U5c3a\U7801\":\"37\"},{\"\U989c\U8272\":\"YQ01\"}]";
         * productBrand = 803456557;
         * productId = 43986072195;
         * productName = "\U79cb\U5b63\U7537\U58eb\U957f\U8896\U886c\U886b\U5168\U68c9\U514d\U70eb\U5546\U52a1\U6b63\U88c5\U4e2d\U5e74\U7eaf\U68c9\U6761\U7eb9\U5927\U7801\U804c\U4e1a\U5de5\U88c5\U886c\U8863";
         * productPic = "//img.alicdn.com/imgextra/i3/2412317789/TB20qmfj29TBuNjy0FcXXbeiFXa_!!2412317789.jpg";
         * productSkuId = 3640406291012;
         * quantity = 1;
         * source = 0;
         * productCategoryId=""
         * }
         */
        @FormUrlEncoded
        @POST("/cart/add")
        Flowable<SimpleResponse<Object>> requestAddCart(@Field("price") double price, @Field("productBrand") String productBrand,
                                                        @Field("productCategoryId") String productCategoryId,
                                                        @Field("productId") long productId, @Field("productName") String productName,
                                                        @Field("productSkuId") String productSkuId,
                                                        @Field("productAttr") String productSkuName,
                                                        @Field("productPic") String productImg,
                                                        @Field("quantity") int quantity,
                                                        @Field("source") int source);


        @POST("cart/batchAdd")
        Call<ResponseBody> requestAdd1688Cart(@Body RequestBody requestBody);

        @POST("product/tklAnalyze")
        Call<ResponseBody> analyzeTBShareCmd(@Body RequestBody requestBody);


    }

    //    interface TestService{
//        @FormUrlEncoded
//        @POST("cp/account.verifySession")
//        Flowable<SimpleResponse<Object>> requestData(@Field("id") String id, @Field("data") String data,
//                                                     @Field("game") String game, @Field("sign") String sign);
//    }
    static class SingletonHolder {
        static GoodsLoader INSTANCE = new GoodsLoader();
    }
}
