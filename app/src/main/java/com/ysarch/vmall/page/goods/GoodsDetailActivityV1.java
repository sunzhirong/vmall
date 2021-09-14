package com.ysarch.vmall.page.goods;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.ysarch.uibase.base.BaseActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.banner.GoodsDetailBannerHolderV1;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.component.GoodsDetailTitleBar;
import com.ysarch.vmall.component.dialog.SkuDialogV1;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResult;
import com.ysarch.vmall.domain.bean.SkuBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.enums.MainPageTag;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.page.coupon.CouponDrawActivity;
import com.ysarch.vmall.page.main.MainActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.SkuParser;
import com.ysarch.vmall.utils.StringUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

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
public class GoodsDetailActivityV1 extends BaseActivity<GoodsDetailPresenterV1> {

    @BindView(R.id.tb_goods_detail)
    GoodsDetailTitleBar mTitleBar;
    @BindView(R.id.wb_dec_goods_detail)
    WebView mWebView;
    @BindView(R.id.cb_goods_detail)
    ConvenientBanner<GoodsDetailBean.ItemImgsBean> mConvenientBanner;
    @BindView(R.id.tv_banner_indicator_goods_detail)
    TextView mTVBannerIndicator;
    @BindView(R.id.tv_name_goods_detail)
    TextView mTVGoodsName;
    @BindView(R.id.tv_cur_price_goods_detail)
    TextView mTVPriceCur;
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

    private boolean bLightMode = false;
    private GoodsDetailBean mGoodsDetailBean;
    private BeeGlide mBeeGlide;
    private int mBannerImgCount;
    private List<LocalPropSkuEntity> mPropSkuDatas;
    private long mGoodsId = -1;

    private SkuBean mSelectedSkuBean;
    private long mTestId = 623868822199L;
    private SkuDialogV1 mSkuDialog;
    private List<CouponBean> mCouponBeans;

    public static Bundle getBundle(long goodsId) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.ARG_GOODS_ID, goodsId);
        return bundle;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        mWebView.setHorizontalScrollBarEnabled(false);
        resetWebViewSettings(mWebView.getSettings());
        mTVPriceOri.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            mGoodsId = getIntent().getExtras().getLong(BundleKey.ARG_GOODS_ID, -1);

            if (mGoodsId != -1) {
                getPresenter().requestGoodsDetail(mGoodsId);
                mBeeGlide = BeeGlide.with(this);
                getPresenter().requestCouponList(mGoodsId);
            }
        } else {
            mGoodsId = mTestId;
            getPresenter().requestGoodsDetail(mGoodsId);
            mBeeGlide = BeeGlide.with(this);
            getPresenter().requestCouponList(mGoodsId);
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
                        StatusBarCompat.changeToLightStatusBar(GoodsDetailActivityV1.this);
                    }
                } else if (bLightMode) {
                    bLightMode = false;
                    StatusBarCompat.cancelLightStatusBar(GoodsDetailActivityV1.this);
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

    @Override
    protected void preInjectView() {
        super.preInjectView();
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail_v1;
    }

    @Override
    public GoodsDetailPresenterV1 newPresenter() {
        return new GoodsDetailPresenterV1();
    }


    public void onDataSuccess(GoodsDetailResult goodsDetailResult) {
        if (goodsDetailResult == null || goodsDetailResult.getItem() == null)
            return;
        mGoodsDetailBean = goodsDetailResult.getItem();

        if (!TextUtils.isEmpty(mGoodsDetailBean.getDesc())) {
            mWebView.loadDataWithBaseURL(null, VMallUtils.fixContentToFitScreen(mGoodsDetailBean.getDesc()),
                    "text/html", "utf-8", null);
        }

        if (mGoodsDetailBean.getOrginalPrice() != 0) {
            mTVPriceOri.setText(mGoodsDetailBean.getOrginalPrice() + "");
            mTVPriceOri.setVisibility(View.VISIBLE);
        } else {
            mTVPriceOri.setVisibility(View.GONE);
        }
        mTVPriceCur.setText(mGoodsDetailBean.getPrice() + "");
        mTVGoodsName.setText(mGoodsDetailBean.getTitle());


        if (CollectionUtils.isNotEmpty(mGoodsDetailBean.getItemImgs())) {
            for (int i = 0; i < mGoodsDetailBean.getItemImgs().size(); i++) {
                mGoodsDetailBean.getItemImgs().get(i).setUrl(StringUtil.correctUrl(mGoodsDetailBean.getItemImgs().get(i).getUrl()));
            }

            mConvenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Holder createHolder(View itemView) {
                    return new GoodsDetailBannerHolderV1(itemView, mBeeGlide);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_banner_goods_detail;
                }
            }, mGoodsDetailBean.getItemImgs());
            mConvenientBanner.setCanLoop(true);
            mBannerImgCount = mGoodsDetailBean.getItemImgs().size();
            mTVBannerIndicator.setVisibility(View.VISIBLE);
            mTVBannerIndicator.setText(1 + "/" + mBannerImgCount);
            if (mBannerImgCount > 1)
                mConvenientBanner.startTurning(3000);
        } else {
            mBannerImgCount = 0;
            mTVBannerIndicator.setVisibility(View.GONE);
        }


        mPropSkuDatas = SkuParser.parseProps(mGoodsDetailBean.getPropsName());
        if (mPropSkuDatas != null) {
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

    public void onAddCartSucc() {
        if (mSkuDialog != null) {
            mSkuDialog.dismiss();
            mSkuDialog = null;
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
        if (mConvenientBanner.isTurning()) {
            mConvenientBanner.stopTurning();
        }
    }

    @OnClick({R.id.ctv_customer_service_goods_detail, R.id.ctv_cart_goods_detail,
            R.id.tv_add_cart_goods_detail, R.id.tv_props_goods_detail,
            R.id.tv_coupon1_goods_detail, R.id.tv_coupon2_goods_detail})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ctv_customer_service_goods_detail:
                // TODO: 24/09/2020 联系客服
                break;
            case R.id.ctv_cart_goods_detail:
                NavHelper.startActivity(GoodsDetailActivityV1.this, MainActivity.class,
                        MainActivity.getBundle(MainPageTag.MAIN_PAGE_CART_TAG));
                finish();
                break;
            case R.id.tv_add_cart_goods_detail:
            case R.id.tv_props_goods_detail:
                SkuDialogV1.Builder builder = new SkuDialogV1.Builder(this)
                        .setSkuBeans(mGoodsDetailBean.getSkus().getSku())
                        .setImages(mGoodsDetailBean.getPropsImg())
                        .setDefaultImg(mGoodsDetailBean.getPicUrl())
                        .setLocalPropSkuEntities(mPropSkuDatas)
                        .setSelectedSkuBean(mSelectedSkuBean)
                        .setGoodsDetailBean(mGoodsDetailBean)
                        .setDialogCallback((skuString, skuBean, img, quantity) -> {
                            mTVProps.setText(skuString);
                            mSelectedSkuBean = skuBean;
                            getPresenter().requestAddCart(mGoodsDetailBean, mSelectedSkuBean, skuString, img, quantity);
                        });
                mSkuDialog = builder.build();
                mSkuDialog.show();
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
                NavHelper.startActivity(GoodsDetailActivityV1.this, CouponDrawActivity.class,
                        CouponDrawActivity.getBundle(mGoodsId, mCouponBeans));
                break;
        }
    }
}
