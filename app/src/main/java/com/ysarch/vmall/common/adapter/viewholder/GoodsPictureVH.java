package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 16/09/2020
 **/
public class GoodsPictureVH extends AbsViewHolder {

    @BindView(R.id.riv_goods_item)
    RoundedImageView mRivGoodsItem;
    @BindView(R.id.rl_container)
    RelativeLayout mRlContainer;
    private BeeGlide mBeeGlide;


    private LocalSkuEntity mBean;


    public GoodsPictureVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        ButterKnife.bind(this, itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_goods_picture;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mBean = (LocalSkuEntity) data;
//        mBeeGlide.load(ImageLoadConfig.create(VMallUtils.decodeString(mBean.getImage()))
//                , mRivGoodsItem);
        // TODO: 2021/1/15 暂时这么处理闪烁问题
        Glide.with(VMallApplication.sApplication).load(VMallUtils.decodeString(mBean.getImage())).into(mRivGoodsItem);
        if (mBean.isSelected()) {
            mRlContainer.setBackground(VMallApplication.sApplication.getResources().getDrawable(R.drawable.shape_round_rect_r3_fill_tra_stroke_primary_goods));
        } else {
            mRlContainer.setBackground(null);
        }
    }

//    @OnClick({R.id.iv_minus_goods_cart, R.id.iv_plus_goods_cart})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_minus_goods_cart:
//                if (mCurNum > 1) {
//                    --mCurNum;
//                    mTVCount.setText("" + mCurNum);
//                    mIVMinus.setEnabled(mCurNum > 1);
//                    mBean.setQuantity(mCurNum);
//                    mCallback.onItemNumChange(mPosition, mBean, false);
//                }
//
//
//                break;
//            case R.id.iv_plus_goods_cart:
//                ++mCurNum;
//                mTVCount.setText("" + mCurNum);
//                mIVMinus.setEnabled(mCurNum > 1);
//                mBean.setQuantity(mCurNum);
//                mCallback.onItemNumChange(mPosition, mBean, true);
//                break;
//        }
//    }

}
