package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.itemDecoration.LinearVerDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CouponSelectableListAdapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 01/10/2020
 **/
public class CouponDialog extends Dialog {

    @BindView(R.id.rcy_coupon_dialog)
    RecyclerView mRecyclerView;
    private CouponSelectableListAdapter mAdapter;
    private List<CouponBean> mCouponBeans = new ArrayList<>();
    private CouponBean mSelCoupon;
    private BeeGlide mBeeGlide;
    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    private CouponDialog(Context context, int style) {
        super(context, style);
        init();
    }

    public void setSelCoupon(CouponBean selCoupon) {
        mSelCoupon = selCoupon;
        mAdapter.setSelCouponBean(mSelCoupon);
    }

    public void init() {
        mBeeGlide = BeeGlide.with(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_coupon, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(true);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        initAdapter();
    }


    public void setCouponBeans(List<CouponBean> couponBeans) {
        mCouponBeans = couponBeans;
        mAdapter.refreshData(mCouponBeans);
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new CouponSelectableListAdapter(getContext());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            int gapV = SizeUtils.dp2px(8);
            int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
            mRecyclerView.addItemDecoration(new LinearVerDecoration(marginH, gapV, gapV, gapV));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setCallback(new Callback() {
                @Override
                public void onCouponOpt(CouponBean couponBean) {
                    dismiss();
                    if(mCallback!=null){
                        mCallback.onCouponOpt(couponBean);
                    }
                }
            });
        }
    }


    @OnClick({R.id.iv_close,R.id.rl_select_none})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.rl_select_none:
                if(mCallback != null){
                    mCallback.onCouponOpt(null);
                }
                dismiss();
                break;
        }
    }


    public interface Callback {
        void onCouponOpt(CouponBean couponBean);
    }


    public static class Builder {
        private Context mContext;
        private int mStyle = R.style.ScrollDialog;
        private List<CouponBean> mCouponBeans = new ArrayList<>();
        private CouponBean mSelCoupon;
        private Callback mCallback;

        public Builder setCallback(Callback callback) {
            mCallback = callback;
            return this;
        }

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setCouponBeans(List<CouponBean> couponBeans) {
            mCouponBeans = couponBeans;
            return this;
        }

        public Builder setSelCoupon(CouponBean selCoupon) {
            mSelCoupon = selCoupon;
            return this;
        }

        public CouponDialog build() {
            CouponDialog dialog = new CouponDialog(mContext, mStyle);
            dialog.setSelCoupon(mSelCoupon);
            dialog.setCouponBeans(mCouponBeans);
            dialog.setCallback(mCallback);
            return dialog;
        }
    }
}
