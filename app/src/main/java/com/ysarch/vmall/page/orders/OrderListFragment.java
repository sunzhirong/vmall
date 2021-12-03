package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.os.Message;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.LinearVerDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.OrderHistoryAdapter;
import com.ysarch.vmall.common.adapter.viewholder.OrderHistoryItemVH;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.component.dialog.CancelOrderDialog;
import com.ysarch.vmall.component.dialog.PayDialog;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.orders.presenter.OrderListPresenter;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fysong on 15/09/2020
 **/
public class OrderListFragment extends CommonPureListFragment<OrderListPresenter> implements IEventListener {
    private int mOrderStatus;
    private OrderHistoryAdapter mAdapter;
    private OrderBean mOrderBean;
    private long mOrderId = -1;
    private String mPassword;
    private PayDialog mPayDialog;


    public static Bundle getBundle(int status) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_ORDER_STATUS, status);
        return bundle;
    }


    @Override
    public void bindEvent() {
        super.bindEvent();
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_ORDER_STATUS_CHANGED,this);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        mAdapter = new OrderHistoryAdapter(getContext());
        mAdapter.setCallback(new OrderHistoryItemVH.Callback() {
            @Override
            public void onDeleteClick(int position, OrderBean orderBean) {
                getPresenter().deleteOrder(position, orderBean);
            }

            @Override
            public void onCancelClick(int position, OrderBean orderBean) {
//                getPresenter().cancelOrder(position, orderBean);
                CancelOrderDialog cancelOrderDialog = new CancelOrderDialog.Builder(context).setCallback(new CancelOrderDialog.Callback() {
                    @Override
                    public void onCancelOpt(int id) {
                        mOrderBean = orderBean;
                        mOrderId = orderBean.getId();
                        getPresenter().cancelOrder(position,orderBean,id);
                    }
                }).build();
                cancelOrderDialog.show();
            }

            @Override
            public void onPayClick(int position, OrderBean orderBean) {
                mOrderBean = orderBean;
                mOrderId = orderBean.getId();
                showPayVerifyDialog();
//                if(mOrderBean.isNeedConfirmReceive()&&mOrderBean.getStatus() == Constants.STATUS_ORDER_DELIVERED){
//                    getPresenter().confirmReceiveOrder(mOrderId);
//                }else {
//                    showPayVerifyDialog();
//                }
//                NavHelper.startActivity(getActivity(), OrderDetailActivity.class, OrderDetailActivity.getBundle(orderBean));
            }

            @Override
            public void onHistoryItemClick(int position, OrderBean orderBean) {
                mOrderBean = orderBean;
                mOrderId = orderBean.getId();
                NavHelper.startActivity(getActivity(), OrderDetailActivity.class, OrderDetailActivity.getBundle(orderBean));
            }

            @Override
            public void onConfirmClick(int position, OrderBean orderBean) {
                //确认收货
//                ToastUtils.showShortToast(context,"确认收货");
                mOrderBean = orderBean;
                mOrderId = orderBean.getId();
                getPresenter().confirmReceiveOrder(mOrderId);
//                NavHelper.startActivity(getActivity(), RechargeActivity.class);
            }
        });
        return mAdapter;
    }

    @Override
    protected void requestData(int page) {
        getPresenter().requestOrderList(mOrderStatus, page, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getRefreshLayout() != null) {
            getRefreshLayout().setRefreshing(true);
            refreshData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void requestDataOnEmpty() {
        getPresenter().requestOrderList(mOrderStatus, 1, true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mOrderStatus = getArguments().getInt(BundleKey.ARG_ORDER_STATUS);

        int gapV = SizeUtils.dp2px(10);
        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        initRecyclerView(new LinearVerDecoration(marginH, gapV, gapV, gapV));

        mRootView.setBackgroundColor(ResUtils.getColor(R.color.bg_page_gray));

        getPresenter().requestOrderList(mOrderStatus, 1, getUserVisibleHint());
    }

    @Override
    public OrderListPresenter newPresenter() {
        return new OrderListPresenter();
    }

    @Override
    protected boolean isPagingData() {
        return true;
    }

    public void onOrderListSucc(int page, List<OrderBean> list) {
        mPage = page;
        if (page == 1) {
            mAdapter.refreshData(list);
        } else {
            mAdapter.appendData(list);
        }
        resetUIStatus(mPage, true);

    }

    /**
     * 取消成功
     */
    public void onCancelSucc(int position, OrderBean orderBean) {
        mAdapter.updateOrder(position, orderBean);
    }

    public void onDeleteSucc(int position, OrderBean orderBean) {
        mAdapter.removeOrder(position, orderBean);
    }

    @Override
    public void onNotify(Message message) {
        if(message.what == NotificationDef.EVENT_ORDER_STATUS_CHANGED && getUserVisibleHint()){
            getRefreshLayout().setRefreshing(true);
            refreshData();
        }
    }

    @Override
    protected boolean hasMore() {
        return getPresenter().hasMore;
    }




    public void onWalletChecked(float integer) {
        mPayDialog = new PayDialog();
        mPayDialog.setPayCallback(new PayDialog.PayCallback() {
            @Override
            public void onConfirm(String password) {
                mPassword = password;
                if(mOrderBean.getStatus()== Constants.STATUS_ORDER_DELIVERED){
                    if(mOrderBean.isNeedPayFreigth()){
                        getPresenter().payFreightByBalance(mOrderId, mOrderBean.getOrderSn(), mPassword);
                    }else {
                        getPresenter().confirmReceiveOrder(mOrderId);
                    }
                }else {
                    getPresenter().payByBalance(mOrderId, mOrderBean.getOrderSn(), mPassword);
                }

            }

            @Override
            public void onCancel() {

            }
//
//            @Override
//            public void onForgetPayPassword() {
//                NavHelper.startActivity(getActivity(), PayPwdModifyActivity.class);
//            }
        });

//        if(mOrderBean.getStatus()!=Constants.STATUS_ORDER_DELIVERED) {
//            mPayDialog.setPrice(mOrderBean.getRestAmount());
//        }else {
//            mPayDialog.setPrice(mOrderBean.getFreightAmount());
//        }

        mPayDialog.setPrice(mOrderBean.getRestAmount());
        mPayDialog.show(getFragmentManager(),"test");
    }

    /**
     * 显示支付窗口
     */
    private void showPayVerifyDialog() {
        getPresenter().checkWallet();


    }

    public void onPaySucc() {
        showTs(getString(R.string.label_pay_succ));
        getActivity().finish();
        NavHelper.startActivity(this,OrderPaidActivity.class);
    }

    public void onConfirmSucc(){
        showTs(getString(R.string.label_confirm_succ));
//        mAdapter.removeOrder(position, orderBean);
    }

    public void onPwdError() {
        mPayDialog.setPwdError(true);

        mPayDialog.show(getFragmentManager(),"test");
    }

    @Override
    protected String getPageName() {
        switch (mOrderStatus){
            case Constants.STATUS_ORDER_ALL:
                return "全部订单页";
            case Constants.STATUS_ORDER_UNPAY:
                return "订单_待付款页";
            case Constants.STATUS_ORDER_DELIVER_WAITING:
                return "订单_备货中页";
            case Constants.STATUS_ORDER_DELIVERED:
                return "订单_待收货页";
            case Constants.STATUS_ORDER_COMPLETE:
                return "订单_已完成页";
            case Constants.STATUS_ORDER_CLOSED:
                return "订单_已关闭页";
            case Constants.STATUS_ORDER_AUDIT_WAITING:
                return "订单_审核中页";
        }
        return super.getPageName();
    }
}
