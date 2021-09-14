package com.ysarch.vmall.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.library.PhotoView;
import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsDetailsPhotoView extends RelativeLayout {

    @BindView(R.id.photoview)
    PhotoView mPhotoview;

    public GoodsDetailsPhotoView(Context context) {
        super(context);
        init(context, null);
    }

    public GoodsDetailsPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GoodsDetailsPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_goods_photo_view, this, true);
        ButterKnife.bind(this, this);
        mPhotoview.enable();
        mPhotoview.setScaleType(ImageView.ScaleType.FIT_XY);
    }


    public ImageView getPhotoview() {
        return mPhotoview;
    }
}
