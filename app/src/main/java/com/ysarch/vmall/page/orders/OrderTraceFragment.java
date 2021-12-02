package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.listview.TraceListAdapter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderTraceBean;
import com.ysarch.vmall.domain.bean.WmsWarehouseInfoBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.orders.presenter.OrderTracePresenter;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderTraceFragment extends BaseFragment<OrderTracePresenter> {
    @BindView(R.id.lvTrace)
    ListView mLvTrace;
    @BindView(R.id.tv_user_info_order_detail)
    TextView mTvUserInfoOrderDetail;
    @BindView(R.id.tv_address_order_detail)
    TextView mTvAddressOrderDetail;

    @BindView(R.id.tv_order_sn)
    TextView mTvOrderSn;

    private List<OrderTraceBean> traceList = new ArrayList<>(10);
    private TraceListAdapter adapter;
    private OrderBean mOrderBean;
    private long mOrderId;

    //todo 等ui出来完成具体细节
    @Override
    public void initData(Bundle savedInstanceState) {
        adapter = new TraceListAdapter(context, traceList);
        mLvTrace.setAdapter(adapter);


        if (getArguments() != null) {
            mOrderBean = (OrderBean) getArguments().getSerializable(BundleKey.ARG_ORDER);
            if (mOrderBean != null) {
                mOrderId = mOrderBean.getId();
            } else {
                mOrderId = getArguments().getLong(BundleKey.ARG_ORDER_ID, -1);
            }
        }
        if (mOrderId == -1) {
            showTs(getString(R.string.tips_data_error));
            getActivity().finish();
        }

        if (mOrderBean != null) {
            getPresenter().getOrderTrace(mOrderId);

            if(mOrderBean.getShippingMethod()== Constants.SHIPPING_METHOD_DELIVERY) {
                mTvUserInfoOrderDetail.setText(VMallUtils.decodeString(mOrderBean.getReceiverName()) + "  "
                        + VMallUtils.decodeString(mOrderBean.getReceiverPhone()));
                mTvAddressOrderDetail.setText(VMallUtils.decodeString(mOrderBean.getReceiverDetailAddress()));
            }else {
                WmsWarehouseInfoBean wmsWarehouseInfo = mOrderBean.getWmsWarehouseInfo();
                if(wmsWarehouseInfo==null){return;}
                mTvUserInfoOrderDetail.setText(VMallUtils.decodeString(wmsWarehouseInfo.getName()) + "  "
                        + VMallUtils.decodeString(wmsWarehouseInfo.getPhone()));
                switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
                    case Constants.ID_LAN_KM:
                        mTvAddressOrderDetail.setText(wmsWarehouseInfo.getKhAddress());
                        break;
                    case Constants.ID_LAN_ZH:
                        mTvAddressOrderDetail.setText(wmsWarehouseInfo.getAddress());
                        break;
                    case Constants.ID_LAN_EN:
                        mTvAddressOrderDetail.setText(wmsWarehouseInfo.getEnAddress());
                        break;
                    default:
                        mTvAddressOrderDetail.setText(wmsWarehouseInfo.getAddress());
                        break;

                }
            }
            mTvOrderSn.setText(String.format(ResUtils.getString(R.string.format_order_sn), mOrderBean.getOrderSn()));

        }





    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_trace;
    }

    @Override
    public OrderTracePresenter newPresenter() {
        return new OrderTracePresenter();
    }

    public void onDataSucc(List<OrderTraceBean> data) {
        if (data != null && data.size() != 0) {
            traceList.addAll(data);
            adapter.notifyDataSetChanged();
        }

    }

    public void onDataFail() {

    }

    @Override
    protected String getPageName() {
        return "物流信息页";
    }
}
