package com.ysarch.vmall.common.banner;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;

/**
 * Created by fysong on 24/09/2020
 **/
public class GoodsDetailBannerHolderV1 extends Holder<GoodsDetailBean.ItemImgsBean> {
    private BeeGlide mBeeGlide;
    private ImageView mImageView;

    public GoodsDetailBannerHolderV1(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        mImageView = (ImageView) itemView;
    }

    @Override
    protected void initView(View itemView) {
    }

    @Override
    public void updateUI(GoodsDetailBean.ItemImgsBean data) {
        mBeeGlide.load(ImageLoadConfig.create(data.getUrl()).randomPlaceHolder(), mImageView);
    }
}
