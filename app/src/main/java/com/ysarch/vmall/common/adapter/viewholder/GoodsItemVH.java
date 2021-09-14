package com.ysarch.vmall.common.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.GoodsItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 18/09/2020
 **/
public class GoodsItemVH extends AbsViewHolder {

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
    private GoodsItemBean mGoodsItemBean;
    private BeeGlide mBeeGlide;

    public GoodsItemVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mBeeGlide = beeGlide;
    }

    public static int getLayoutRes() {
        return R.layout.item_goods;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        GoodsItemBean goodsItemBean = (GoodsItemBean) data;
        if (mGoodsItemBean == null || mGoodsItemBean.getId() != goodsItemBean.getId()) {
            mGoodsItemBean = goodsItemBean;
            mTVDec.setText(mGoodsItemBean.getTitle());
            mTVMUnit.setText("KHC");
            mTVPrice.setText(mGoodsItemBean.getPrice());
            mBeeGlide.load(ImageLoadConfig.create(mGoodsItemBean.getPicUrl()).randomPlaceHolder(), mRivCover);


        }
    }
}
