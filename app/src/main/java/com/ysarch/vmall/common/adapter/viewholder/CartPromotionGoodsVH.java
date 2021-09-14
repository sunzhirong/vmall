package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.CartGoodsBean;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 16/09/2020
 **/
public class CartPromotionGoodsVH extends AbsViewHolder {
    @BindView(R.id.riv_cover_order_goods_item)
    RoundedImageView mRoundedImageView;
    @BindView(R.id.tv_name_order_goods_item)
    TextView mTVGoodsName;
    @BindView(R.id.tv_price_order_goods_item)
    TextView mTVPrice;
    @BindView(R.id.tv_sku_order_goods_item)
    TextView mTVSku;
    @BindView(R.id.tv_count_order_goods_item)
    TextView mTVCount;
    @BindView(R.id.iv_plus_goods_cart)
    ImageView mIVPlus;
    @BindView(R.id.iv_minus_goods_cart)
    ImageView mIVMinus;

    private BeeGlide mBeeGlide;
    private int mCurNum;
    private Callback mCallback;


    private GenerateOrderConfirmResult.CartPromotionItemListBean mBean;


    public CartPromotionGoodsVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        ButterKnife.bind(this, itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_order_detail_goods;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mBean = (GenerateOrderConfirmResult.CartPromotionItemListBean) data;
        mCallback = (Callback) callback;
        mBeeGlide.load(ImageLoadConfig.create(VMallUtils.decodeString(mBean.getProductPic()))
                .randomPlaceHolder(), mRoundedImageView);

        mTVGoodsName.setText(VMallUtils.decodeString(mBean.getProductName()));
        mTVPrice.setText(VMallUtils.convertPriceString(mBean.getPrice()));
//        mTVSku.setText(VMallUtils.decodeString(mBean.getProductAttr()));
        mTVSku.setText(VMallUtils.getProductAttr(mBean.getProductAttr()));
        mTVCount.setText(mBean.getQuantity() + "");
        mCurNum = mBean.getQuantity();
        mIVMinus.setEnabled(mCurNum > 1);
    }

    @OnClick({R.id.iv_minus_goods_cart, R.id.iv_plus_goods_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_minus_goods_cart:
                if (mCurNum > 1) {
                    --mCurNum;
                    mTVCount.setText("" + mCurNum);
                    mIVMinus.setEnabled(mCurNum > 1);
                    mBean.setQuantity(mCurNum);
                    mCallback.onItemNumChange(mPosition, mBean,false);
                }


                break;
            case R.id.iv_plus_goods_cart:
                ++mCurNum;
                mTVCount.setText("" + mCurNum);
                mIVMinus.setEnabled(mCurNum > 1);
                mBean.setQuantity(mCurNum);
                mCallback.onItemNumChange(mPosition, mBean,true);
                break;
        }
    }

    public interface Callback {

        void onItemNumChange(int position, GenerateOrderConfirmResult.CartPromotionItemListBean cartGoodsBean,boolean isAdd);
    }
}
