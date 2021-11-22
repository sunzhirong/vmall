package com.ysarch.vmall.common.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.OrderItemListBean;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 16/09/2020
 **/
public class HistoryOrderGoodsDetailsVH extends AbsViewHolder {
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


    private BeeGlide mBeeGlide;

    private OrderItemListBean mOrderItemListBean;
    private String mLastImage;
    public HistoryOrderGoodsDetailsVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        ButterKnife.bind(this, itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_order_history_details_goods;
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


        mTVCount.setText("x" + mOrderItemListBean.getProductQuantity());
    }

//    private String getProductAttr(String productAttr){
//        try {
//            List<KeyValueBean> keyValueBeans = JSON.parseArray(productAttr, KeyValueBean.class);
//            StringBuilder stringBuilder = new StringBuilder();
//            for(KeyValueBean bean : keyValueBeans){
//                stringBuilder.append(bean.key).append(":").append(bean.value).append(";");
//            }
//            return stringBuilder.toString();
//        }catch (Exception e){
//            return productAttr;
//        }
//
//    }
}
