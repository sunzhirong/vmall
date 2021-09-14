package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.widget.ListView;

import com.ysarch.uibase.base.BaseActivity;
import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.uibase.listview.TraceListAdapter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderTraceBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.orders.presenter.OrderTracePresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单物流
 */
public class OrderTraceActivity extends BaseTitleActivity {


    public static Bundle getBundle(long orderId) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.ARG_ORDER_ID, orderId);
        return bundle;
    }

    public static Bundle getBundle(OrderBean orderBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ARG_ORDER, orderBean);
        return bundle;
    }

    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_trace_info);
    }

    @Override
    protected Fragment createFragment() {
        OrderTraceFragment fragment = new OrderTraceFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }


}
