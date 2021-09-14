package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 17/09/2020
 **/
public class CouponItemVH extends AbsViewHolder {
    @BindView(R.id.tv_price_prefix_coupon_selectable)
    TextView mTVPricePrefix;
    @BindView(R.id.tv_price_coupon_selectable)
    TextView mTVPrice;
    @BindView(R.id.tv_name_coupon_selectable)
    TextView mTVCouponName;
    @BindView(R.id.tv_time_coupon_selectable)
    TextView mTVTime;
    @BindView(R.id.tv_time_prefix_coupon_selectable)
    TextView mTVTimePrefix;
    @BindView(R.id.iv_coupon_used)
    ImageView mIVUsed;
    private CouponBean mCouponBean;

    public CouponItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCouponBean = (CouponBean) data;
        if(mCouponBean.getCouponType()== Constants.STATUS_COUPON_USED){
            mTVPricePrefix.setTextColor(0xff999999);
            mTVPrice.setTextColor(0xff999999);
            mTVCouponName.setTextColor(0xff999999);
            mTVTime.setTextColor(0xff999999);
            mTVTimePrefix.setTextColor(0xff999999);
            mIVUsed.setVisibility(View.VISIBLE);
        }else if(mCouponBean.getCouponType() == Constants.STATUS_COUPON_OVER_TIME){
            mTVPricePrefix.setTextColor(0xfffca4ab);
            mTVPrice.setTextColor(0xfffca4ab);
            mTVCouponName.setTextColor(0xffb3b3b3);
            mTVTime.setTextColor(0xffb3b3b3);
            mTVTimePrefix.setTextColor(0xffb3b3b3);
        }
        mTVPricePrefix.setText("$");
        mTVPrice.setText(VMallUtils.convertString(mCouponBean.getAmount()));
        mTVCouponName.setText(mCouponBean.getName());
        mTVTime.setText(VMallUtils.GTMToLocal(mCouponBean.getEndTime()));
    }


    public static int getLayoutRes(){
        return R.layout.item_coupon;
    }
}
