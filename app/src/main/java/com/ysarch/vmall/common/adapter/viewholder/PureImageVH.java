package com.ysarch.vmall.common.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.utils.LPUtils;

/**
 * Created by fysong on 10/10/2020
 **/
public class PureImageVH extends AbsViewHolder {
    private BeeGlide mBeeGlide;
    private String mImage;
    private ImageView mImageView;

    public PureImageVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        mImageView = (ImageView) itemView;
    }

    public static View createItemView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new RecyclerView.LayoutParams(LPUtils.FILL, LPUtils.WRAP));
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        String url = (String) data;

        if (url != null && !url.equals(mImage)) {
            mImage = url;
            mBeeGlide.load(ImageLoadConfig.create(mImage).randomPlaceHolder(), mImageView);
        }
    }
}
