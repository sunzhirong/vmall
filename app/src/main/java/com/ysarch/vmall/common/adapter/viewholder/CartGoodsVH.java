package com.ysarch.vmall.common.adapter.viewholder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.component.dialog.DeleteCartOrderDialog;
import com.ysarch.vmall.component.dialog.EditCartOrderDialog;
import com.ysarch.vmall.domain.bean.CartGoodsBean;
import com.ysarch.vmall.utils.GlideUtils;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.VMallUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车具体商品vh
 * Created by fysong on 10/09/2020
 **/
public class CartGoodsVH extends AbsViewHolder {
    @BindView(R.id.iv_radio_goods_cart)
    ImageView mIVRadio;
    @BindView(R.id.riv_cover_goods_cart)
    RoundedImageView mRIVCover;
    @BindView(R.id.tv_name_goods_cart)
    TextView mTVName;
    @BindView(R.id.ctv_sku_goods_cart)
    CompatTextView mCTVSku;
    @BindView(R.id.tv_price_goods_cart)
    TextView mTVPrice;
    @BindView(R.id.iv_plus_goods_cart)
    ImageView mIVPlus;
    @BindView(R.id.iv_minus_goods_cart)
    ImageView mIVMinus;
    @BindView(R.id.tv_num_goods_cart)
    TextView mTVNum;
    @BindView(R.id.v_divide_line_goods_cart)
    View mVDivideLine;

    @BindView(R.id.view_top)
    View mVTop;
    @BindView(R.id.view_bottom)
    View mVBottom;
    @BindView(R.id.cl_container)
    View mCl;

    private int mCurNum;
    private BeeGlide mBeeGlide;
    private CartGoodsBean mCartGoodsBean;
    private Callback mCallback;
    private String mCurImg;

    public CartGoodsVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mBeeGlide = beeGlide;

    }

    public static int getLayoutRes() {
        return R.layout.item_goods_cart;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCallback = (Callback) callback;
        mCartGoodsBean = (CartGoodsBean) data;
        showLine(false);
        switch (mCartGoodsBean.getType()){
            case CartGoodsBean.TYPE_TOP:
                mVTop.setVisibility(View.VISIBLE);
                mCl.setBackgroundResource(R.drawable.shape_round_rect_r10_fill_white_top);
                break;
            case CartGoodsBean.TYPE_BOTTOM:
                mVTop.setVisibility(View.GONE);
                mCl.setBackgroundResource(R.drawable.shape_round_rect_r10_fill_white_bottom);
                break;
            case CartGoodsBean.TYPE_ONE:
                mVTop.setVisibility(View.VISIBLE);
                mCl.setBackgroundResource(R.drawable.shape_round_rect_r10_fill_white);
                break;
            default:
                showLine(true);
                mCl.setBackgroundColor(Color.parseColor("#ffffff"));
                mVTop.setVisibility(View.GONE);
                break;
        }
        mCurNum = mCartGoodsBean.getQuantity();
        mTVPrice.setText("$ " + VMallUtils.convertTo2String(mCartGoodsBean.getPrice()));
        try {
            mTVName.setText(URLDecoder.decode(mCartGoodsBean.getProductName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            mTVName.setText(mCartGoodsBean.getProductName());
        }


        if (!TextUtils.isEmpty(mCartGoodsBean.getProductAttr())) {
//            mCTVSku.setText(URLDecoder.decode(mCartGoodsBean.getProductAttr()));
            mCTVSku.setText(VMallUtils.getProductAttr(mCartGoodsBean.getProductAttr()));
            mCTVSku.setVisibility(View.VISIBLE);
        } else {
            mCTVSku.setVisibility(View.GONE);
        }

        mTVNum.setText(mCurNum + "");
        mIVRadio.setSelected(mCartGoodsBean.isSelected());

        mIVMinus.setEnabled(mCurNum >= 0);

        if (!TextUtils.isEmpty(mCartGoodsBean.getProductPic())) {
            if (!mCartGoodsBean.getProductPic().equals(mCurImg)) {
                mCurImg = mCartGoodsBean.getProductPic();
                mBeeGlide.load(
                        ImageLoadConfig.create(VMallUtils.decodeString(mCartGoodsBean.getProductPic()))
                                .randomPlaceHolder(), mRIVCover);
            }
        }
    }

    @OnClick({R.id.iv_radio_goods_cart, R.id.iv_plus_goods_cart, R.id.iv_minus_goods_cart,
            R.id.ctv_sku_goods_cart,R.id.tv_num_goods_cart})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_radio_goods_cart:
                view.setSelected(!view.isSelected());
                mCartGoodsBean.setSelected(view.isSelected());
                if (mCallback != null) {
                    mCallback.onItemSelectStatusChange(mPosition, mCartGoodsBean);
                }
                break;

            case R.id.iv_plus_goods_cart:
                ++mCurNum;
                mTVNum.setText("" + mCurNum);
                mIVMinus.setEnabled(mCurNum > 1);
                mCartGoodsBean.setQuantity(mCurNum);
                if (mIVRadio.isSelected() && mCallback != null) {
                    mCallback.onItemNumChange(mPosition, mCartGoodsBean);
                }
                if(mCallback!=null){
                    mCallback.onQuantityChange(mCartGoodsBean.getId(),mCurNum);
                }
                break;

            case R.id.iv_minus_goods_cart:
                if (mCurNum > 1) {
                    --mCurNum;
                    mTVNum.setText("" + mCurNum);
                    mIVMinus.setEnabled(mCurNum > 1);
                    mCartGoodsBean.setQuantity(mCurNum);
                    if (mIVRadio.isSelected() && mCallback != null) {
                        mCallback.onItemNumChange(mPosition, mCartGoodsBean);
                    }
                    if(mCallback!=null){
                        mCallback.onQuantityChange(mCartGoodsBean.getId(),mCurNum);
                    }
                }
                break;
            case R.id.ctv_sku_goods_cart:
//                if (mCallback != null) {
//                    mCallback.onSkuClick(mPosition, mCartGoodsBean);
//                }
                break;
            case R.id.tv_num_goods_cart:
                new EditCartOrderDialog.Builder(itemView.getContext())
                        .setNum(String.valueOf(mCurNum))
                        .setDeleteCallback(new EditCartOrderDialog.DeleteCallback() {
                            @Override
                            public void onConfirm(String num) {
                                if(TextUtils.isEmpty(num)){return;}
                                int curNum = Integer.parseInt(num);
                                if(mCurNum==curNum){return;}
                                if(curNum>0) {
                                    if (mCallback != null) {
                                        mCurNum = Integer.parseInt(num);
                                        mTVNum.setText("" + mCurNum);
                                        mIVMinus.setEnabled(mCurNum > 1);
                                        mCartGoodsBean.setQuantity(mCurNum);

                                        mCallback.onItemNumChange(mPosition, mCartGoodsBean);
                                        mCallback.onQuantityChange(mCartGoodsBean.getId(), mCurNum);
                                    }
                                }
                            }
                        }).build().show();
                break;
        }
    }

    public void showLine(boolean visible) {
        mVDivideLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void showLast(boolean visible){
        mVBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public interface Callback {
        void onItemSelectStatusChange(int position, CartGoodsBean cartGoodsBean);

        void onSkuClick(int position, CartGoodsBean cartGoodsBean);

        void onItemNumChange(int position, CartGoodsBean cartGoodsBean);

        void onQuantityChange(int id,int quantity);
    }

}
