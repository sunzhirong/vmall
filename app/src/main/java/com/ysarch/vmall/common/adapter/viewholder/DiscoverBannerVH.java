package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;

/**
 * Created by fysong on 12/09/2020
 **/
public class DiscoverBannerVH extends AbsViewHolder {
    private RoundedImageView mImageView;
    private BeeGlide mBeeGlide;

    public DiscoverBannerVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        mImageView = (RoundedImageView) itemView;
    }

    public static int getLayoutRes() {
        return R.layout.item_discover_banner;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mBeeGlide.load(ImageLoadConfig.create("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599904163178&di=22b791507e32b2bc50f4dc90aad50cbf&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201207%2F21%2F20120721090438_XJnrY.jpeg")
                .randomPlaceHolder(), mImageView);
    }
}
