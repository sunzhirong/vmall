package com.ysarch.vmall.common.banner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.GoodsDetailResult;
import com.ysarch.vmall.utils.GlideUtils;

/**
 * Created by fysong on 24/09/2020
 **/
public class GoodsDetailBannerHolder extends Holder<String> {
    private BeeGlide mBeeGlide;
    private ImageView mImageView;
    private Context mContext;

    public GoodsDetailBannerHolder(View itemView, BeeGlide beeGlide, Activity context) {
        super(itemView);
        mBeeGlide = beeGlide;
        mContext = context;
        mImageView = (ImageView) itemView;
    }

    @Override
    protected void initView(View itemView) {
    }

    @Override
    public void updateUI(String data) {
//        mBeeGlide.load(ImageLoadConfig.create(data).randomPlaceHolder(), mImageView);
        GlideUtils.loadImageView(mContext,data,mImageView);
    }
}
