package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 11/09/2020
 **/
public class CateLevel3VH extends AbsViewHolder {
    @BindView(R.id.iv_cate_level2)
    ImageView mImageView;
    @BindView(R.id.tv_label_cate_level2)
    TextView mTextView;
    private BeeGlide mBeeGlide;
    private CateLevelBean mCateLevelBean;
    public CateLevel3VH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mBeeGlide = beeGlide;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCateLevelBean = (CateLevelBean) data;
        mBeeGlide.load(ImageLoadConfig.create(mCateLevelBean.getIcon())
        .randomPlaceHolder(), mImageView);


        switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
            case Constants.ID_LAN_KM:
                mTextView.setText(mCateLevelBean.getKhName());
                break;
            case Constants.ID_LAN_ZH:
                mTextView.setText(mCateLevelBean.getName());
                break;
            case Constants.ID_LAN_EN:
                mTextView.setText(mCateLevelBean.getEnName());
                break;
            default:
                mTextView.setText(mCateLevelBean.getName());
                break;

        }
//        mTextView.setText(mCateLevelBean.getName());
    }

    public static int getLayoutRes() {
        return R.layout.item_cate_level3;
    }
}
