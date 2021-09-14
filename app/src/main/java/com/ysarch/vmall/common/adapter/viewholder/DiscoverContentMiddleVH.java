package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 12/09/2020
 **/
public class DiscoverContentMiddleVH extends AbsViewHolder {
    @BindView(R.id.tv_discover_content_middle)
    TextView mTextView;
    @BindView(R.id.riv_cover_discover_content_middle)
    RoundedImageView mImageView;
    private BeeGlide mBeeGlide;
    public DiscoverContentMiddleVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        ButterKnife.bind(this, itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_discover_content_middle;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mBeeGlide.load(ImageLoadConfig.create("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599905446632&di=8858214be516f9394d6724cbf721dddd&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170314%2F40c4dc9748b84b4abcf0e9a435e32a41_th.jpg")
                .randomPlaceHolder(), mImageView);
        mTextView.setText("沙世哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
    }
}
