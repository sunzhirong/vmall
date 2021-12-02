package com.ysarch.vmall.common.adapter.viewholder;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.OrderItemListBean;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ysarch.vmall.common.adapter.viewholder.CartPromotionGoodsVH.TYPE_NORMAL;

/**
 * Created by fysong on 16/09/2020
 **/
public class OrderGoodsDetailsVH extends AbsViewHolder {
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

    @BindView(R.id.tv_account_order_goods_item)
    TextView mTVCountOrderGoods;
    @BindView(R.id.tv_delivery_order_goods_item)
    TextView mTVDeliveryOrderGoods;
    @BindView(R.id.tv_total_price_order_goods_item)
    TextView mTVTotalPrice;
    @BindView(R.id.tv_delivery_order_goods)
    TextView mTVDeliveryOrder;


    @BindView(R.id.view_line)
    View mLine;
    @BindView(R.id.ll_bottom)
    View mLlBottom;

    private BeeGlide mBeeGlide;

    private OrderItemListBean mOrderItemListBean;
    private String mLastImage;
    public OrderGoodsDetailsVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        ButterKnife.bind(this, itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_order_details_goods;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mOrderItemListBean = (OrderItemListBean) data;
        String img = VMallUtils.correctUrl(mOrderItemListBean.getProductPic());
        if(TextUtils.isEmpty(mLastImage) || !mLastImage.equals(img)){
            mBeeGlide.load(ImageLoadConfig.create(img)
                    .randomPlaceHolder(), mRoundedImageView);
            mLastImage = img;
        }

        mTVGoodsName.setText(mOrderItemListBean.getProductName());
        mTVPrice.setText(VMallUtils.convertPriceString(mOrderItemListBean.getProductPrice()));
        if(!TextUtils.isEmpty(mOrderItemListBean.getProductAttr())){
            mTVSku.setText(VMallUtils.getProductAttr(mOrderItemListBean.getProductAttr()));
            mTVSku.setVisibility(View.VISIBLE);
        }else {
            mTVSku.setVisibility(View.GONE);
        }

        if(mOrderItemListBean.getType()==TYPE_NORMAL){
            mLlBottom.setVisibility(View.GONE);
            mLine.setVisibility(View.GONE);
        }else {
            mLlBottom.setVisibility(View.VISIBLE);
            mLine.setVisibility(View.VISIBLE);
            mTVTotalPrice.setText(VMallUtils.convertPriceString(mOrderItemListBean.getAmount()));
            String numString = String.format(ResUtils.getString(R.string.format_order_total), mOrderItemListBean.getNumber());
            SpannableStringBuilder builder = new SpannableStringBuilder(numString);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#f94956"));
            builder.setSpan(span, 1, 1 + String.valueOf(mOrderItemListBean.getNumber()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTVCountOrderGoods.setText(builder);
            mTVDeliveryOrderGoods.setVisibility(mOrderItemListBean.getDollorDelivery()==0?View.GONE:View.VISIBLE);
            mTVDeliveryOrder.setVisibility(mOrderItemListBean.getDollorDelivery()==0?View.GONE:View.VISIBLE);
            mTVDeliveryOrderGoods.setText(VMallUtils.convertPriceString(mOrderItemListBean.getDollorDelivery()));
        }

        mTVCount.setText("x" + mOrderItemListBean.getProductQuantity());
    }

}
