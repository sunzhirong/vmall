package com.ysarch.vmall.common.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.HomeRecommendBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ysarch.vmall.domain.constant.Constants.ID_LAN_KM;

/**
 * Created by fysong on 19/09/2020
 **/
public class CateHomeItemVH extends AbsViewHolder {
    @BindView(R.id.riv_cate_home)
    RoundedImageView mImageView;
    @BindView(R.id.tv_cate_home)
    TextView mTextView;
    private BeeGlide mBeeGlide;
    private HomeRecommendBean mCateLevelBean;

    private Callback mCallback;

    public CateHomeItemVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mBeeGlide = beeGlide;

        itemView.setOnClickListener(v -> {
            if(mCallback != null){
                mCallback.onItemClick(mPosition, mCateLevelBean, mTextView);
            }
        });
    }

    public static int getLayoutRes() {
        return R.layout.item_cate_with_icon;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCallback = (Callback) callback;
        mCateLevelBean = (HomeRecommendBean) data;
        mBeeGlide.load(ImageLoadConfig.create(StringUtil.correctUrl(mCateLevelBean.getPic()))
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

    public interface Callback{
        void onItemClick(int position, HomeRecommendBean data, TextView mTextView);
    }

}
