package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.PayChannelBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 17/09/2020
 **/
public class PayChannelItemVH extends AbsViewHolder {


    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.iv_selectable)
    ImageView mIvSelectable;
    @BindView(R.id.iv_bank_icon)
    ImageView mIvBankIcon;
    private BeeGlide mBeeGlide;
    public PayChannelItemVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        PayChannelBean payChannelBean = (PayChannelBean) data;
        mTvBankName.setText(payChannelBean.getPayName());
        mTvDesc.setText(payChannelBean.getRemark());
        mIvSelectable.setSelected(payChannelBean.isSelect());
//        mBeeGlide.load(ImageLoadConfig.create(payChannelBean.getIcon()), mIvBankIcon);
        Glide.with(VMallApplication.sApplication).load(payChannelBean.getIcon()).into(mIvBankIcon);
    }


    public static int getLayoutRes() {
        return R.layout.item_paychannel;
    }

}
