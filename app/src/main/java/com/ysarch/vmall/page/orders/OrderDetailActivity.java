package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;

import androidx.fragment.app.Fragment;

/**
 * Created by fysong on 30/09/2020
 **/
public class OrderDetailActivity extends BaseTitleActivity {

    private OrderDetailFragment fragment;

    public static Bundle getBundle(long orderId) {
        return getBundle(orderId,false);
    }
    public static Bundle getBundle(long orderId, boolean autoPay) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.ARG_ORDER_ID, orderId);
        bundle.putBoolean(BundleKey.ARG_ORDER_AUTO_PAY, autoPay);
        return bundle;
    }

    public static Bundle getBundle(OrderBean orderBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ARG_ORDER, orderBean);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_order_detail);
    }

    @Override
    protected Fragment createFragment() {
        fragment = new OrderDetailFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }


    @Override
    protected View initBarRight() {
        CompatTextView textView = new CompatTextView(this);
        textView.setTextColor(0xff9a9a9a);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        textView.setText(getString(R.string.label_customer_service));
        textView.setDrawableTop(ResUtils.getDrawable(R.drawable.ic_service_contact), SizeUtils.dp2px(18),
                SizeUtils.dp2px(18));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }


    @Override
    protected void onTitlebarRightClick(View view) {
        super.onTitlebarRightClick(view);
        if(UserInfoManager.judeIsLogin(OrderDetailActivity.this) &&
                fragment != null && fragment.mOrderBean != null){
//            EaseHelper.getInstance().navKefu(this, fragment, fragment.mOrderBean);
            NavHelper.startActivity(this, CommonWebActivity.class, CommonWebActivity.getBundle("https://m.me/105800634679932?ref=sabayshop"));
        }
    }
}
