package com.ysarch.vmall.page.goods;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ysarch.uibase.base.BaseActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.PureImageAdapter;
import com.ysarch.vmall.common.banner.GoodsDetailBannerHolder;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.component.GoodsDetailTitleBar;
import com.ysarch.vmall.component.dialog.SkuDialog;
import com.ysarch.vmall.component.dialog.multisku.MultiSelSkuDialog;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResultV2;
import com.ysarch.vmall.domain.bean.SkuBeanV2;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.page.cart.CartActivity;
import com.ysarch.vmall.page.coupon.CouponDrawActivity;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.SkuParser;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * <pre>
 * 商品详情
 * sku数据排序通过解析prop_names来确定
 * banner 数据读取item_imgs
 * </pre>
 * Created by fysong on 21/09/2020
 **/
public class GoodsDetailActivity extends BaseActivity<GoodsDetailPresenter> {

    public GoodsDetailItemBean mGoodsDetailBean;
    @BindView(R.id.tv_rate_percent)
    TextView mTvRatePercent;
    @BindView(R.id.tv_trace_detail)
    TextView mTvTraceDetail;
    @BindView(R.id.tb_goods_detail)
    GoodsDetailTitleBar mTitleBar;
    //    @BindView(R.id.wb_dec_goods_detail)
//    WebView mWebView;
//    @BindView(R.id.ly_dec_images_goods_detail)
//    LinearLayout mLyImages;
    @BindView(R.id.rcy_images_goods_detail)
    RecyclerView mRcyImages;
    @BindView(R.id.cb_goods_detail)
    ConvenientBanner<String> mConvenientBanner;
    @BindView(R.id.tv_banner_indicator_goods_detail)
    TextView mTVBannerIndicator;
    @BindView(R.id.tv_name_goods_detail)
    TextView mTVGoodsName;
    @BindView(R.id.tv_cur_price_goods_detail)
    TextView mTVPriceDollar;
    //    @BindView(R.id.tv_rmb_price_goods_detail)
//    TextView mTVPriceRMB;
    @BindView(R.id.tv_ori_price_goods_detail)
    TextView mTVPriceOri;
    @BindView(R.id.fl_coupons_goods_detail)
    FrameLayout mFlCoupons;
    @BindView(R.id.tv_coupon1_goods_detail)
    TextView mTVCoupon1;
    @BindView(R.id.tv_coupon2_goods_detail)
    TextView mTVCoupon2;
    @BindView(R.id.ly_props_goods_detail)
    LinearLayout mLyProps;
    @BindView(R.id.tv_props_goods_detail)
    TextView mTVProps;
    @BindView(R.id.tv_id_goods_detail)
    TextView mTVGoodsId;
    @BindView(R.id.tv_exchange_rate_goods_detail)
    TextView mTVExchangeRate;
    //    @BindView(R.id.tv_source_goods_detail)
//    TextView mTVSource;
    @BindView(R.id.tv_cart_count)
    TextView mTVCartCount;

    @BindView(R.id.ll_freight)
    LinearLayout mLlFreight;
    @BindView(R.id.tv_freight_detail)
    TextView mTvFreightDetail;


    private boolean bLightMode = false;
    private BeeGlide mBeeGlide;
    private int mBannerImgCount;
    private List<LocalPropSkuEntity> mPropSkuDatas;
    private long mGoodsId = -1;

    private SkuBeanV2 mSelectedSkuBean;
    private long mTestId = 623868822199L;
    private SkuDialog mSkuDialog;
    private List<CouponBean> mCouponBeans;
    private PureImageAdapter mAdapter;

    private int mPlatformType;
    private MultiSelSkuDialog mMultiSkuDialog;

    public static Bundle getBundle(long goodsId) {
        return getBundle(goodsId, Constants.TYPE_PLATFORM_TB);
    }

    public static Bundle getBundle(long goodsId, int platform) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.ARG_GOODS_ID, goodsId);
        bundle.putInt(BundleKey.ARG_PLATFORM_TYPE, platform);
        return bundle;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
//        mWebView.setHorizontalScrollBarEnabled(false);
//        resetWebViewSettings(mWebView.getSettings());
//        mTVPriceRMB.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mTVPriceOri.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            mGoodsId = getIntent().getExtras().getLong(BundleKey.ARG_GOODS_ID, -1);
            mPlatformType = getIntent().getExtras().getInt(BundleKey.ARG_PLATFORM_TYPE, Constants.TYPE_PLATFORM_TB);
            if (mGoodsId != -1) {
                getPresenter().requestGoodsDetail(mGoodsId, mPlatformType);
                mBeeGlide = BeeGlide.with(this);
                getPresenter().requestCouponList(mGoodsId);
            }
        } else {
            mGoodsId = mTestId;
            getPresenter().requestGoodsDetail(mGoodsId, mPlatformType);
            mBeeGlide = BeeGlide.with(this);
            getPresenter().requestCouponList(mGoodsId);
        }

        if(UserInfoManager.getCartItemCount()>0){
            mTVCartCount.setVisibility(View.VISIBLE);
            mTVCartCount.setText(UserInfoManager.getCartItemCount()+"");
        }else {
            mTVCartCount.setVisibility(View.GONE);
        }

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mTitleBar.setCallback(new GoodsDetailTitleBar.Callback() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onShareClick() {
// TODO: 24/09/2020 分享点击
            }

            @Override
            public void onMsglick() {
// TODO: 24/09/2020 消息点击
            }

            @Override
            public void onAlphaChange(float alpha) {
                if (alpha > 0.5) {
                    if (!bLightMode) {
                        bLightMode = true;
                        StatusBarCompat.changeToLightStatusBar(GoodsDetailActivity.this);
                    }
                } else if (bLightMode) {
                    bLightMode = false;
                    StatusBarCompat.cancelLightStatusBar(GoodsDetailActivity.this);
                }
            }
        });


        mConvenientBanner.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onPageSelected(int index) {
                index = index + 1;
                mTVBannerIndicator.setText(index + "/" + mBannerImgCount);
            }
        });
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new PureImageAdapter(this);
            mRcyImages.setLayoutManager(new LinearLayoutManager(this));
            mRcyImages.setAdapter(mAdapter);
        }
    }

    @Override
    protected void preInjectView() {
        super.preInjectView();
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public GoodsDetailPresenter newPresenter() {
        return new GoodsDetailPresenter();
    }


    public void onDataSuccess(GoodsDetailResultV2 goodsDetailResult) {
        if (goodsDetailResult == null || goodsDetailResult.getData() == null
                || goodsDetailResult.getData().getItem() == null)
            return;
        mGoodsDetailBean = goodsDetailResult.getData().getItem();

        if (CollectionUtils.isNotEmpty(mGoodsDetailBean.getDescImgs())) {
            initAdapter();
            mAdapter.refreshData(mGoodsDetailBean.getDescImgs());
        }

//        if (!TextUtils.isEmpty(mGoodsDetailBean.getPriceRange())) {
//            mTVPriceRMB.setText("RMB￥" + mGoodsDetailBean.getPriceRange());
//            mTVPriceRMB.setVisibility(View.VISIBLE);
//        } else {
//            mTVPriceRMB.setVisibility(View.GONE);
//        }

        if (!TextUtils.isEmpty(mGoodsDetailBean.getDollarMarketPrice())) {
            mTVPriceOri.setVisibility(View.VISIBLE);
            mTVPriceOri.setText("$" + mGoodsDetailBean.getDollarMarketPrice());
        } else {
            mTVPriceOri.setVisibility(View.GONE);
        }

        mTVPriceDollar.setText("$" + mGoodsDetailBean.getDollarPrice());
        mTVGoodsName.setText(mGoodsDetailBean.getTitle());

        mTVGoodsId.setText(getString(R.string.label_goods_id_prefix)+" " + mGoodsDetailBean.getItemId());
//        mTVGoodsId.setText(getString(R.string.label_goods_id_prefix) + mGoodsDetailBean.getItemId());
        mTVExchangeRate.setText(getString(R.string.label_exchange_rate_prefix) + mGoodsDetailBean.getExchangeRate());


//        mTVSource.setText(getString(R.string.label_goods_source_prefix) +
//                VMallUtils.getPlatformName(mGoodsDetailBean.getSource()));

        if (CollectionUtils.isNotEmpty(mGoodsDetailBean.getImages())) {

            mConvenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Holder createHolder(View itemView) {
                    return new GoodsDetailBannerHolder(itemView, mBeeGlide,context);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_banner_goods_detail;
                }
            }, mGoodsDetailBean.getImages());
            mConvenientBanner.setCanLoop(true);
            mBannerImgCount = mGoodsDetailBean.getImages().size();
            mTVBannerIndicator.setVisibility(View.VISIBLE);
            mTVBannerIndicator.setText(1 + "/" + mBannerImgCount);
//            if (mBannerImgCount > 1)
//                mConvenientBanner.startTurning(3000);
        } else {
            mBannerImgCount = 0;
            mTVBannerIndicator.setVisibility(View.GONE);
        }


        mPropSkuDatas = SkuParser.parseSkuData(mGoodsDetailBean);

        if (CollectionUtils.isNotEmpty(mPropSkuDatas)) {
            mLyProps.setVisibility(View.VISIBLE);
            String propsLabel = "";
            for (int i = 0; i < mPropSkuDatas.size(); i++) {
                if (!TextUtils.isEmpty(propsLabel)) {
                    propsLabel = propsLabel + "、" + mPropSkuDatas.get(i).getPropLabel();
                } else {
                    propsLabel = mPropSkuDatas.get(i).getPropLabel();
                }
            }
            mTVProps.setText(propsLabel);
        } else {
            mLyProps.setVisibility(View.GONE);
        }


//        GoodsSellerBean seller = goodsDetailResult.getData().getSeller();
//        if (seller != null) {
//            mTvRatePercent.setText(seller.getGoodRatePercentage());
//        }
        if (mGoodsDetailBean.getDiscount() == 0) {
            mTvRatePercent.setVisibility(View.GONE);
        }else {
            mTvRatePercent.setText("-" + (int)mGoodsDetailBean.getDiscount()+"%");
            mTvRatePercent.setVisibility(View.VISIBLE);
        }
        mTvTraceDetail.setText(getString(R.string.label_expected_arrival)+" "+VMallUtils.getTranceDateString());
    }

    public void onDataFail() {
    }

    protected void resetWebViewSettings(WebSettings webSettings) {
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
        webSettings.setSupportZoom(true); // 可以缩放

        try {
            webSettings.setJavaScriptEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         *  目前
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void onAddCartSucc(Integer cartItemCount) {
        if (mSkuDialog != null) {
            mSkuDialog.dismiss();
            mSkuDialog = null;
        }

        if (mMultiSkuDialog != null) {
            mMultiSkuDialog.dismiss();
            mMultiSkuDialog = null;
        }

        UserInfoManager.updateCartItemCount(cartItemCount);
        if(UserInfoManager.getCartItemCount()>0){
            mTVCartCount.setVisibility(View.VISIBLE);
            mTVCartCount.setText(UserInfoManager.getCartItemCount()+"");
        }else {
            mTVCartCount.setVisibility(View.GONE);
        }

        showTs(getString(R.string.tip_add_cart_succ));
    }

    public void onCouponSucc(List<CouponBean> couponBeans) {
        if (CollectionUtils.isNotEmpty(couponBeans)) {
            mCouponBeans = couponBeans;
            mFlCoupons.setVisibility(View.VISIBLE);

            CouponBean couponBean = couponBeans.get(0);
            if (couponBean != null) {
                mTVCoupon1.setVisibility(View.VISIBLE);
                mTVCoupon1.setText(couponBean.getName());
                if (couponBeans.size() > 1) {
                    couponBean = mCouponBeans.get(1);
                    if (couponBean != null) {
                        mTVCoupon2.setVisibility(View.VISIBLE);
                        mTVCoupon2.setText(couponBean.getName());
                    }
                }
            }
        }
    }

    public void onCouponFail() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mConvenientBanner.isTurning()) {
//            mConvenientBanner.stopTurning();
//        }
    }


    @OnClick({R.id.ctv_customer_service_goods_detail, R.id.ctv_cart_goods_detail, R.id.ctl_service,
            R.id.tv_add_cart_goods_detail, R.id.tv_props_goods_detail,R.id.ll_trace,
            R.id.tv_coupon1_goods_detail, R.id.tv_coupon2_goods_detail,R.id.ll_freight})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ctv_customer_service_goods_detail:
//                if (mGoodsDetailBean == null)
//                    return;
//                if (UserInfoManager.judeIsLogin(GoodsDetailActivity.this))
//                    EaseHelper.getInstance().navKefu(this, this, mGoodsDetailBean);
//                NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("https://m.me/105800634679932?ref=sabayshop"));
                NavHelper.startWeb(context,"https://m.me/105800634679932?ref=sabayshop");
                break;
            case R.id.ctv_cart_goods_detail:
//                NavHelper.startActivity(GoodsDetailActivity.this, MainActivity.class,
//                        MainActivity.getBundle(MainPageTag.MAIN_PAGE_CART_TAG));
//                finish();
                if (UserInfoManager.judeIsLogin(GoodsDetailActivity.this))
                    NavHelper.startActivity(GoodsDetailActivity.this, CartActivity.class);
                break;
            case R.id.tv_add_cart_goods_detail:
            case R.id.tv_props_goods_detail:
                if (mGoodsDetailBean == null)
                    return;
                if (mGoodsDetailBean.getSource() == Constants.TYPE_PLATFORM_1688) {
                    MultiSelSkuDialog.Builder builder = new MultiSelSkuDialog.Builder(GoodsDetailActivity.this)
                            .setGoodsDetailBean(mGoodsDetailBean)
                            .setLocalPropSkuEntities(mPropSkuDatas)
                            .setSkuBeans(mGoodsDetailBean.getSku())
                            .setSelectedMultiSkuEntities(getPresenter().mSelMultiSkuEntities)
                            .setCallback(getPresenter().getCallback());

                    mMultiSkuDialog = builder.build();
                    mMultiSkuDialog.show(getSupportFragmentManager(), "multi");
                } else {
                    SkuDialog.Builder builder = new SkuDialog.Builder(this)
                            .setSkuBeans(mGoodsDetailBean.getSku())
                            .setDefaultImg(mGoodsDetailBean.getImages().get(0))
                            .setLocalPropSkuEntities(mPropSkuDatas)
                            .setSelectedSkuBean(mSelectedSkuBean)
                            .setGoodsDetailBean(mGoodsDetailBean)
                            .setDialogCallback((skuString, skuBean, img, quantity) -> {
                                mTVProps.setText(skuString);
                                mSelectedSkuBean = skuBean;
                                getPresenter().requestAddCart(mGoodsDetailBean, mSelectedSkuBean, skuString, img, quantity, mGoodsDetailBean.getSource());
                            });
                    mSkuDialog = builder.build();
                    mSkuDialog.setOwnerActivity(this);
                    mSkuDialog.show();
                }
                break;
            case R.id.tv_coupon1_goods_detail:
//                if (CollectionUtils.isNotEmpty(mCouponBeans))
//                    getPresenter().drawCoupon(mCouponBeans.get(0));
//                break;
            case R.id.tv_coupon2_goods_detail:
//                if (CollectionUtils.isNotEmpty(mCouponBeans) && mCouponBeans.size() > 1)
//                    getPresenter().drawCoupon(mCouponBeans.get(1));
//                break;
            case R.id.v_gap_coupon_goods_detail:
            case R.id.v_arrow_coupon_goods_detail:
                if (mGoodsDetailBean == null)
                    return;
                NavHelper.startActivity(GoodsDetailActivity.this, CouponDrawActivity.class,
                        CouponDrawActivity.getBundle(mGoodsId, mCouponBeans));
                break;

            case R.id.ctl_service:
                BottomSheetDialog serviceDialog = new BottomSheetDialog(context);
                View serviceView = getLayoutInflater().inflate(R.layout.dialog_goods_service, null);
                serviceView.findViewById(R.id.tv_confirm).setOnClickListener(v -> serviceDialog.dismiss());
                serviceDialog.setContentView(serviceView);
                serviceDialog.show();
                break;

            case R.id.ll_trace:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_trace, null);
                dialogView.findViewById(R.id.tv_confirm).setOnClickListener(v -> bottomSheetDialog.dismiss());
                ((TextView) dialogView.findViewById(R.id.tv_trace_time)).setText(VMallUtils.getTranceDateString());
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();
                break;
            case R.id.ll_freight:
                BottomSheetDialog freightDialog = new BottomSheetDialog(context);
                View freightView = getLayoutInflater().inflate(R.layout.dialog_freight, null);
                freightView.findViewById(R.id.tv_confirm).setOnClickListener(v -> freightDialog.dismiss());
                freightDialog.setContentView(freightView);
                freightDialog.show();
                break;
        }
    }
}
