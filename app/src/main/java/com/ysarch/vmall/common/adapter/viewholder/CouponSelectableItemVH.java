package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponSelectableItemVH extends AbsViewHolder {
    @BindView(R.id.tv_price_prefix_coupon_selectable)
    TextView mTVPricePrefix;
    @BindView(R.id.tv_price_coupon_selectable)
    TextView mTVPrice;
    @BindView(R.id.tv_name_coupon_selectable)
    TextView mTVCouponName;
    @BindView(R.id.tv_time_coupon_selectable)
    TextView mTVTime;
    @BindView(R.id.iv_radio_coupon_selectable)
    ImageView mIVRadio;
    private CouponBean mCouponBean;

    public CouponSelectableItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCouponBean = (CouponBean) data;
        mTVPricePrefix.setText("$");
        mTVPrice.setText(VMallUtils.convertString(mCouponBean.getAmount()));
        mTVCouponName.setText(mCouponBean.getName());
        mTVTime.setText(VMallUtils.GTMToLocal(mCouponBean.getEndTime()));
        mIVRadio.setSelected(mCouponBean.isLocalSelected());
    }


    public static int getLayoutRes(){
        return R.layout.item_coupon_selectable;
    }

}
