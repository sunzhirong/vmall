package com.ysarch.vmall.common.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.VMallUtils;

/**
 * Created by fysong on 24/09/2020
 **/
public class SkuItemVH extends AbsViewHolder {
    private BeeGlide mBeeGlide;
    TextView mTextView;
    LinearLayout mLlView;
    ImageView mIvView;
    private LocalSkuEntity mLocalSkuEntity;
//    public SkuItemVH(View itemView) {
//        super(itemView);
//        mTextView =  itemView.findViewById(R.id.tv_sku_item);
//        mLlView =  itemView.findViewById(R.id.ll_sku_item);
//    }

    public SkuItemVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        mTextView =  itemView.findViewById(R.id.tv_sku_item);
        mLlView =  itemView.findViewById(R.id.ll_sku_item);
        mIvView =  itemView.findViewById(R.id.iv_sku_item);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mLocalSkuEntity = (LocalSkuEntity) data;
        mTextView.setText(mLocalSkuEntity.getSkuLabel());
        mTextView.setSelected(mLocalSkuEntity.isSelected());
        mLlView.setSelected(mLocalSkuEntity.isSelected());
        mLlView.setEnabled(mLocalSkuEntity.isEnable());
        mTextView.setEnabled(mLocalSkuEntity.isEnable());
        Log.e("onBindData",mLocalSkuEntity.getSkuLabel()+mLocalSkuEntity.getImage());

        if(!TextUtils.isEmpty(mLocalSkuEntity.getImage())){
            mIvView.setVisibility(View.VISIBLE);
//            mBeeGlide.load(ImageLoadConfig.create(mLocalSkuEntity.getImage()), mIvView);
            Glide.with(VMallApplication.sApplication).load(VMallUtils.decodeString(mLocalSkuEntity.getImage())).into(mIvView);
        }else{
            mIvView.setVisibility(View.GONE);
        }
    }


    public static int getLayoutRes() {
        return R.layout.item_sku;
    }
}
