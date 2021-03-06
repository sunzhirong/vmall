package com.ysarch.vmall.common.adapter.viewholder;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tendcloud.tenddata.TCAgent;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.GlideUtils;
import com.ysarch.vmall.utils.Log;
import com.yslibrary.utils.CollectionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 18/09/2020
 **/
public class GoodsItemVHV2 extends AbsViewHolder {

    @BindView(R.id.riv_cover_goods_item)
    RoundedImageView mRivCover;
    @BindView(R.id.tv_dec_goods_item)
    TextView mTVDec;
    @BindView(R.id.tv_monetary_unit_goods_item)
    TextView mTVMUnit;
    @BindView(R.id.tv_price_goods_item)
    TextView mTVPrice;
    @BindView(R.id.tv_ori_price_goods_item)
    TextView mTVOriPrice;
    private GoodsItemBeanV2 mGoodsItemBean;
    private BeeGlide mBeeGlide;

    private String mImageUrl;

    public GoodsItemVHV2(View itemView, BeeGlide beeGlide) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mBeeGlide = beeGlide;
        mTVOriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    public static int getLayoutRes() {
        return R.layout.item_goods;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        GoodsItemBeanV2 goodsItemBean = (GoodsItemBeanV2) data;
        if (mGoodsItemBean == null || mGoodsItemBean.getId() != goodsItemBean.getId()) {
            mGoodsItemBean = goodsItemBean;

            if(!mGoodsItemBean.isOnItemView()) {
                Log.e("GoodsItemVHV2 1","isOnItemView");
                try {
                    double aDouble = Double.parseDouble(mGoodsItemBean.getDollarPrice())*100;
                    TCAgent.onViewItem(String.valueOf(mGoodsItemBean.getId()), mGoodsItemBean.getCategory(), mGoodsItemBean.getShopName(), (int) aDouble);
                    mGoodsItemBean.setOnItemView(true);
                    Log.e("GoodsItemVHV2 2","isOnItemView");
                }catch (Exception e){
                    Log.e("GoodsItemVHV2 3","isOnItemView");
                }
            }else {
                Log.e("GoodsItemVHV2 4","isOnItemView");
            }

            mTVDec.setText(mGoodsItemBean.getTitle());
            mTVMUnit.setText("$");
            mTVPrice.setText(mGoodsItemBean.getDollarPrice());

            if (CollectionUtils.isNotEmpty(mGoodsItemBean.getImageUrls())
                    && !TextUtils.isEmpty(mGoodsItemBean.getImageUrls().get(0))
                    && !mGoodsItemBean.getImageUrls().get(0).equals(mImageUrl)) {
                mImageUrl = mGoodsItemBean.getImageUrls().get(0);
//                mBeeGlide.load(ImageLoadConfig.create(mGoodsItemBean.getImageUrls().get(0)).randomPlaceHolder(), mRivCover);
                GlideUtils.loadImageView(mBeeGlide.getContext(),mGoodsItemBean.getImageUrls().get(0),mRivCover);
            } else
                mBeeGlide.load(ImageLoadConfig.create("").randomPlaceHolder(), mRivCover);


            if(!TextUtils.isEmpty(mGoodsItemBean.getDollarMarketPrice()) &&
                    !mGoodsItemBean.getDollarMarketPrice().equals(mGoodsItemBean.getDollarPrice())){
                mTVOriPrice.setVisibility(View.VISIBLE);
                mTVOriPrice.setText("$" + mGoodsItemBean.getDollarMarketPrice());
            } else {
                mTVOriPrice.setVisibility(View.GONE);
            }
        }
    }
}
