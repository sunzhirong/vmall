package com.ysarch.vmall.page.orders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.OrderGoodsAdapter;
import com.ysarch.vmall.common.adapter.viewholder.CartPromotionGoodsVH;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.component.dialog.CancelOrderDialog;
import com.ysarch.vmall.component.dialog.DeleteOrderDialog;
import com.ysarch.vmall.component.dialog.PayDialog;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.EnumBean;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderItemListBean;
import com.ysarch.vmall.domain.bean.WmsWarehouseInfoBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.orders.presenter.OrderDetailPresenter;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.OrderCountDownUtils;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SystemUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 30/09/2020
 **/
public class OrderDetailFragment extends BaseFragment<OrderDetailPresenter> {

    @BindView(R.id.tv_red_opt_order_detail)
    TextView mTVRedOpt;
    @BindView(R.id.tv_black_opt_order_detail)
    TextView mTVBlackOpt;
    @BindView(R.id.tv_pay_amount_order_detail)
    TextView mTVPayAmount;
    @BindView(R.id.tv_total_amount_order_detail)
    TextView mTVTotalAmount;
    @BindView(R.id.tv_coupon_amount_order_detail)
    TextView mTVCoupon;
    @BindView(R.id.rcy_goods_order_detail)
    RecyclerView mRcyGoods;
    @BindView(R.id.tv_user_info_order_detail)
    TextView mTVUserInfo;
    @BindView(R.id.tv_address_order_detail)
    TextView mTVAddress;
    @BindView(R.id.tv_status_order_detail)
    TextView mTVStatusLabel;
    @BindView(R.id.tv_freight_order_detail)
    TextView mTVFreight;
    @BindView(R.id.tv_cn_express_amount_order_detail)
    TextView mTVExpressCN;
    @BindView(R.id.tv_oversea_express_amount_order_detail)
    TextView mTVExpressOversea;
    @BindView(R.id.ctl_freight_order_detail)
    ConstraintLayout mCtlFreight;
    @BindView(R.id.tv_freight_pay_status_order_detail)
    TextView mTVFreightPayStatus;
    @BindView(R.id.tv_mode_delivery_order_detail)
    TextView mTVDeliveryMode;
    @BindView(R.id.tv_mode_transport_order_detail)
    TextView mTVTransportMode;
    @BindView(R.id.tv_tips_order_detail)
    TextView mTVTips;
    @BindView(R.id.iv_order_status)
    ImageView mIvOrderStatus;
    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;
    @BindView(R.id.tv_total_num)
    TextView mTvTotalNum;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.iv_locate)
    ImageView mIvLocate;
    @BindView(R.id.tv_order_sn)
    TextView mTvOrderSn;
    @BindView(R.id.tv_order_create_time)
    TextView mTvOrderCreateTime;
    @BindView(R.id.tv_order_remarks)
    TextView mTvOrderRemarks;
    @BindView(R.id.tv_order_dis_mode)
    TextView mTvOrderDisMode;
    @BindView(R.id.tv_clip)
    TextView mTvClip;
    @BindView(R.id.tv_total_amount_prefix_order_detail)
    TextView mTvTotalAmountPrefixOrderDetail;
    @BindView(R.id.tv_total_coupon)
    TextView mTvTotalCoupon;
    @BindView(R.id.tv_total_coupon_detail)
    TextView mTvTotalCouponDetail;
    @BindView(R.id.tv_coupon_amount_prefix_order_detail)
    TextView mTvCouponAmountPrefixOrderDetail;
    @BindView(R.id.tv_pay_amount_prefix_order_detail)
    TextView mTvPayAmountPrefixOrderDetail;

    @BindView(R.id.tv_china_freight)
    TextView mTvChinaFreight;
    @BindView(R.id.tv_china_freight_detail)
    TextView mTvChinaFreightDetail;
    @BindView(R.id.tv_international_freight_detail)
    TextView mTvInternationalFreightDetail;
    @BindView(R.id.tv_delivery_fee)
    TextView mTvDeliveryFee;
    @BindView(R.id.tv_delivery_fee_detail)
    TextView mTvDeliveryFeeDetail;
    @BindView(R.id.tv_freight_coupon)
    TextView mTvFreightCoupon;
    @BindView(R.id.tv_freight_coupon_detail)
    TextView mTvFreightCouponDetail;
    @BindView(R.id.tv_freight_prepaid_detail)
    TextView mTvFreightPrepaidDetail;
    @BindView(R.id.tv_freight_total)
    TextView mTvFreightTotal;
    @BindView(R.id.tv_freight_price)
    TextView mTvFreightPrice;
    @BindView(R.id.tv_freight_amount_prefix_order_detail)
    TextView mTvFreightAmountPrefixOrderDetail;
    @BindView(R.id.v_divide_freight_order_detail)
    View mVDivideFreightOrderDetail;
    @BindView(R.id.tv_cn_express_amount_prefix_order_detail)
    TextView mTvCnExpressAmountPrefixOrderDetail;
    @BindView(R.id.tv_oversea_express_amount_prefix_order_detail)
    TextView mTvOverseaExpressAmountPrefixOrderDetail;
    @BindView(R.id.tv_mode_delivery_prefix_order_detail)
    TextView mTvModeDeliveryPrefixOrderDetail;
    @BindView(R.id.tv_mode_transport_prefix_order_detail)
    TextView mTvModeTransportPrefixOrderDetail;
    @BindView(R.id.tv_bottome_total_price)
    TextView mTvBottomeTotalPrice;
    @BindView(R.id.tv_package_num)
    TextView mTvPackageNum;
    @BindView(R.id.tv_package_num_detail)
    TextView mTvPackageNumDetail;
    @BindView(R.id.cl_package)
    View mClPackage;
    @BindView(R.id.tv_package_weight)
    TextView mTvPackageWeight;
    @BindView(R.id.tv_package_weight_detail)
    TextView mTvPackageWeightDetail;
    @BindView(R.id.iv_order_trace)
    ImageView mIvOrderTrace;
    @BindView(R.id.tv_order_pick_code)
    TextView mTvOrderPickCode;
    @BindView(R.id.tv_tb_freight)
    TextView mTvTbFreight;
    @BindView(R.id.tv_tb_freight_price)
    TextView mTvTbFreightPrice;

    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;


    private long mOrderId = -1;

    private OrderGoodsAdapter mAdapter;
    private List<OrderItemListBean> mOrderItemListBeans;
    private EnumBean mWarehouseBean;
//    private boolean mAutoPay = false;
    public OrderBean mOrderBean;
    private OrderCountDownUtils mCountDownUtils;
    private String mPassword;
    private PayDialog mPayDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mOrderBean = (OrderBean) getArguments().getSerializable(BundleKey.ARG_ORDER);
            if (mOrderBean != null) {
                mOrderId = mOrderBean.getId();
            } else {
                mOrderId = getArguments().getLong(BundleKey.ARG_ORDER_ID, -1);
//                mAutoPay = getArguments().getBoolean(BundleKey.ARG_ORDER_AUTO_PAY, false);
            }
        }

        if (mOrderId == -1) {
            showTs(getString(R.string.tips_data_error));
            getActivity().finish();
        }

        if (mOrderBean != null) {
            updateData(false);
        }

        getPresenter().requestOrderDetail(mOrderId);
    }

    /**
     * 更新地址信息
     */
    private void updateAddressInfo() {
        if (mOrderBean != null) {
            if(mOrderBean.getShippingMethod()==Constants.SHIPPING_METHOD_DELIVERY) {
                mTVUserInfo.setText(VMallUtils.decodeString(mOrderBean.getReceiverName()) + "  "
                        + VMallUtils.decodeString(mOrderBean.getReceiverPhone()));
//                mTVAddress.setText(VMallUtils.decodeString(mOrderBean.getReceiverDetailAddress()));

                if(!TextUtils.isEmpty(mOrderBean.getReceiverDetailAddress())) {
                    mTVAddress.setText(mOrderBean.getReceiverDetailAddress() + "," + VMallUtils.getAddress(mOrderBean.getReceiverProvince(), mOrderBean.getReceiverCity(), mOrderBean.getReceiverRegion()));
                }else {
                    mTVAddress.setText(VMallUtils.getAddress(mOrderBean.getReceiverProvince(), mOrderBean.getReceiverCity(), mOrderBean.getReceiverRegion()));
                }
            }else {
                WmsWarehouseInfoBean wmsWarehouseInfo = mOrderBean.getWmsWarehouseInfo();
                if(wmsWarehouseInfo==null){return;}
                mTVUserInfo.setText(VMallUtils.decodeString(wmsWarehouseInfo.getName()) + "  "
                        + VMallUtils.decodeString(wmsWarehouseInfo.getPhone()));
                switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
                    case Constants.ID_LAN_KM:
                        mTVAddress.setText(wmsWarehouseInfo.getKhAddress());
                        break;
                    case Constants.ID_LAN_ZH:
                        mTVAddress.setText(wmsWarehouseInfo.getAddress());
                        break;
                    case Constants.ID_LAN_EN:
                        mTVAddress.setText(wmsWarehouseInfo.getEnAddress());
                        break;
                    default:
                        mTVAddress.setText(wmsWarehouseInfo.getAddress());
                        break;

                }
            }
        }
    }

    private void updateData(boolean startCountDown) {
//        mOrderItemListBeans = mOrderBean.getOrderItemList();
        List<OrderItemListBean> list = new ArrayList<>();
        List<OrderBean.SameSellerCartPromotionBean> sameList = mOrderBean.getSameSellerOrderItemList();
        if(sameList == null){
            mOrderItemListBeans = mOrderBean.getOrderItemList();
        }else {
            for (OrderBean.SameSellerCartPromotionBean bean : sameList){
                List<OrderItemListBean> omsOrderItems = bean.getOmsOrderItems();
                for (int i = 0;i<omsOrderItems.size();i++){
                    OrderItemListBean orderItemListBean = omsOrderItems.get(i);
                    orderItemListBean.setNumber(bean.getProductQuantity());
                    orderItemListBean.setAmount(bean.getAmount());
                    orderItemListBean.setDollorDelivery(bean.getDollorDelivery());
                    orderItemListBean.setType(CartPromotionGoodsVH.TYPE_NORMAL);
                    if(omsOrderItems.size()-1==i){
                        //最后一个
                        orderItemListBean.setType(CartPromotionGoodsVH.TYPE_END);
                    }
                    list.add(orderItemListBean);
                }
            }

            mOrderItemListBeans = list;
        }


        if (CollectionUtils.isNotEmpty(mOrderBean.getOrderItemList())) {
            initAdapter();
            mAdapter.requestData(mOrderItemListBeans);
        }

        mTVTotalAmount.setText(VMallUtils.convertPriceString(mOrderBean.getTotalAmount()));
//        mTVPayAmount.setText(VMallUtils.convertPriceString(mOrderBean.getRestAmount()));
        mTVPayAmount.setText(VMallUtils.convertPriceString(mOrderBean.getPayAmount()));

        // 发货方式
        String deliveryType = AppContext.getsInstance().getModeDelivery().get(0).getDesc();
        for (int i = 0; i < AppContext.getsInstance().getModeDelivery().size(); i++) {
            EnumBean enumBean = AppContext.getsInstance().getModeDelivery().get(i);
            if (enumBean.getId() == mOrderBean.getDeliverlyType()) {
                deliveryType = enumBean.getDesc();
                break;
            }
        }
        mTVDeliveryMode.setText(deliveryType);

        // 运输方式
        String transportType = AppContext.getsInstance().getModeTransport().get(0).getDesc();
        for (int i = 0; i < AppContext.getsInstance().getModeTransport().size(); i++) {
            EnumBean enumBean = AppContext.getsInstance().getModeTransport().get(i);
            if (enumBean.getId() == mOrderBean.getShippingType()) {
                transportType = enumBean.getDesc();
                break;
            }
        }
        mTVTransportMode.setText(transportType);


        //商品总计
        mTvTotalPrice.setText(VMallUtils.convertPriceString(mOrderBean.getTotalAmount()));
        int num = 0;
        for (OrderItemListBean bean : mOrderBean.getOrderItemList()) {
            num += bean.getProductQuantity();
        }
        String numString = String.format(ResUtils.getString(R.string.format_order_total), num);


        String number = String.valueOf(num);
        int index = numString.indexOf(number);
        SpannableStringBuilder builder = new SpannableStringBuilder(numString);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        builder.setSpan(span, index, index + number.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        SpannableStringBuilder builder = new SpannableStringBuilder(numString);
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
//        builder.setSpan(span, 1, 1 + String.valueOf(num).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvTotalNum.setText(builder);

        //订单信息
        mTvOrderSn.setText(String.format(ResUtils.getString(R.string.format_order_sn), mOrderBean.getOrderSn()));
        mTvOrderCreateTime.setText(String.format(ResUtils.getString(R.string.format_order_create_time), VMallUtils.GTMToLocal(mOrderBean.getCreateTime())));
        if(TextUtils.isEmpty(mOrderBean.getRemark())){
            mTvOrderRemarks.setVisibility(View.GONE);
        }else {
            mTvOrderRemarks.setText(String.format(ResUtils.getString(R.string.format_order_remarks), !TextUtils.isEmpty(mOrderBean.getRemark())?mOrderBean.getRemark():""));
        }
        mTvOrderDisMode.setText(String.format(ResUtils.getString(R.string.format_order_dis_mode), mOrderBean.getShippingMethod()==1?getString(R.string.label_pick_by_yourself):getString(R.string.label_express_delivery)));

        //商品信息
        mTvTotalCouponDetail.setVisibility(needShow(mOrderBean.getCouponAmount()));
        mTvTotalCoupon.setVisibility(needShow(mOrderBean.getCouponAmount()));
        mTvTotalCouponDetail.setText("-"+VMallUtils.convertPriceString(mOrderBean.getCouponAmount()));
        mTVCoupon.setText(VMallUtils.convertPriceString(mOrderBean.getServiceAmount()));
        if(!TextUtils.isEmpty(mOrderBean.getPickUpCode())){
            mTvOrderPickCode.setText(String.format(ResUtils.getString(R.string.format_order_pick_code), mOrderBean.getPickUpCode()));
        }else {
            mTvOrderPickCode.setVisibility(View.GONE);
        }

        mTvBottomeTotalPrice.setText(VMallUtils.convertPriceString(mOrderBean.getRestAmount()));
        if(mOrderBean.getRestAmount()<=0){
            mLlBottom.setVisibility(View.GONE);
        }
        //运费相关
        mTvChinaFreightDetail.setText(VMallUtils.convertPriceString(mOrderBean.getCnFreight()));
        mTvTbFreightPrice.setText(VMallUtils.convertPriceString(mOrderBean.getCnFreight()));
        mTvTbFreight.setVisibility(needShow(mOrderBean.getCnFreight()));
        mTvTbFreightPrice.setVisibility(needShow(mOrderBean.getCnFreight()));

        mTvInternationalFreightDetail.setText(VMallUtils.convertPriceString(mOrderBean.getOverseasFreight()));
        mTvDeliveryFeeDetail.setVisibility(needShow(mOrderBean.getLocalFreight()));
        mTvDeliveryFee.setVisibility(needShow(mOrderBean.getLocalFreight()));
        mTvDeliveryFeeDetail.setText(VMallUtils.convertPriceString(mOrderBean.getLocalFreight()));
        mTvFreightCouponDetail.setText(VMallUtils.convertPriceString(mOrderBean.getFreightCouponAmount()));
        mTvFreightCouponDetail.setVisibility(needShow(mOrderBean.getFreightCouponAmount()));
        mTvFreightCoupon.setVisibility(needShow(mOrderBean.getFreightCouponAmount()));
        mTvFreightPrepaidDetail.setText(VMallUtils.convertPriceString(mOrderBean.getPredictFreightAmount()));


        if(mOrderBean.getInternationalPackageVolume()==0&&mOrderBean.getInternationalPackageWeight()==0){
            mClPackage.setVisibility(View.GONE);
        }else {
            if(mOrderBean.getInternationalPackageVolume()==0){
                mTvPackageNumDetail.setVisibility(View.GONE);
                mTvPackageNum.setVisibility(View.GONE);
            }else {
                mTvPackageNumDetail.setText(VMallUtils.convertTo3String(mOrderBean.getInternationalPackageVolume())+"m³");
            }

            if(mOrderBean.getInternationalPackageWeight()==0){
                mTvPackageWeightDetail.setVisibility(View.GONE);
                mTvPackageWeight.setVisibility(View.GONE);
            }else {
                mTvPackageWeightDetail.setText(VMallUtils.convertTo2String(mOrderBean.getInternationalPackageWeight())+"kg");
            }
        }

        if(mOrderBean.getStatus()==Constants.STATUS_ORDER_UNPAY){
            mIvOrderTrace.setVisibility(View.GONE);
        }

        //待收货、已完成状态可以展示运费相关
//        updateFreight();
        //地址信息
        updateAddressInfo();
        //仓库数据
        updateWarehouse();
        //状态信息
        updateStatusUI(startCountDown);
    }

    private int needShow(double number){
        return number==0?View.GONE:View.VISIBLE;

    }



    /**
     * 仓库数据
     */
    private void updateWarehouse() {
        // 仓库数据
        if (CollectionUtils.isEmpty(AppContext.getsInstance().getWarehouseDatas())) {
            getPresenter().requestWarehouseData();
        } else {
            for (int i = 0; i < AppContext.getsInstance().getWarehouseDatas().size(); i++) {
                EnumBean enumBean = AppContext.getsInstance().getWarehouseDatas().get(i);
                if (enumBean.getId() == mOrderBean.getWarehouse()) {
                    mWarehouseBean = enumBean;
                    break;
                }
            }
        }

    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new OrderGoodsAdapter(getContext(), BeeGlide.with(getContext()));
            mRcyGoods.setLayoutManager(new LinearLayoutManager(getContext()));
            mRcyGoods.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((position, data) -> {
                OrderItemListBean bean = (OrderItemListBean) data;
                NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                        GoodsDetailActivity.getBundle(bean.getProductId(), bean.getSource(),Constants.TYPE_ENTRY_ORDER_DETAIL));
            });
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_detail;
    }


    /**
     * public static final int STATUS_ORDER_UNPAY = 0;                         //待付款
     * public static final int STATUS_ORDER_DELIVER_WAITING = 1;               //采购中
     * public static final int STATUS_ORDER_DELIVERED = 2;                     //待收货
     * public static final int STATUS_ORDER_COMPLETE = 3;                      //已完成
     * public static final int STATUS_ORDER_CLOSED = 4;                        //已关闭
     * public static final int STATUS_ORDER_AUDIT_WAITING = 5;                 //待审核
     */

    private void updateStatusUI(boolean startCountDown) {
        switch (mOrderBean.getStatus()) {
            //待支付
            case Constants.STATUS_ORDER_UNPAY:
                mTVRedOpt.setVisibility(View.VISIBLE);
                mTVBlackOpt.setVisibility(View.VISIBLE);
                mTVRedOpt.setText(ResUtils.getString(R.string.label_order_to_pay));
                mTVBlackOpt.setText(ResUtils.getString(R.string.label_cancel));
                mTVStatusLabel.setText(getWholeOrderStatus(ResUtils.getString(R.string.label_order_unpay)));
                //倒计时
                if(mOrderBean.getRestTime()!=0 &&startCountDown) {
                    if (mCountDownUtils == null) {
                        mCountDownUtils = new OrderCountDownUtils(getContext(), mOrderBean.getRestTime()*1000, 1000, mTvCountDown,
                                "");
                        mCountDownUtils.start();
                    }
                }
                mIvOrderStatus.setBackground(getResources().getDrawable(R.drawable.ic_order_status_unpay));
                break;
            //待审核
            case Constants.STATUS_ORDER_AUDIT_WAITING:
                mTVRedOpt.setVisibility(View.GONE);
                mTVBlackOpt.setVisibility(View.GONE);
                mTVStatusLabel.setText(getWholeOrderStatus(ResUtils.getString(R.string.label_order_audit_waiting)));
                mIvOrderStatus.setBackground(getResources().getDrawable(R.drawable.ic_order_status_audit_waiting));

                break;

            //采购中
            case Constants.STATUS_ORDER_DELIVER_WAITING:
                mTVRedOpt.setVisibility(View.GONE);
                mTVBlackOpt.setVisibility(View.GONE);
                mTVStatusLabel.setText(getWholeOrderStatus(ResUtils.getString(R.string.label_order_deliver_waiting)));
                mIvOrderStatus.setBackground(getResources().getDrawable(R.drawable.ic_order_status_in_stock));

                break;
            //待收货
            case Constants.STATUS_ORDER_DELIVERED:
                mTVBlackOpt.setVisibility(View.GONE);
//                mTVStatusLabel.setText(getWholeOrderStatus(ResUtils.getString(R.string.label_order_delivered)));
                mTVStatusLabel.setText(mOrderBean.getCurrentTag());
                if(mOrderBean.isNeedConfirmReceive()){
                    mTVRedOpt.setText(ResUtils.getString(R.string.label_order_confirm_received));
                }else {
                    if(mOrderBean.isNeedPayFreigth()){
                        mTVRedOpt.setText(ResUtils.getString(R.string.label_order_to_pay));
                    }else {
                        mTVRedOpt.setVisibility(View.GONE);
                    }
                }
//                showLogistics();
                mIvOrderStatus.setBackground(getResources().getDrawable(R.drawable.ic_order_status_delivered));

                break;
            //已完成
            case Constants.STATUS_ORDER_COMPLETE:
                mTVRedOpt.setVisibility(View.GONE);
                mTVBlackOpt.setVisibility(View.GONE);
                mTVStatusLabel.setText(getWholeOrderStatus(ResUtils.getString(R.string.label_order_complete)));
//                showLogistics();
                mIvOrderStatus.setBackground(getResources().getDrawable(R.drawable.ic_order_status_complete));

                break;
            //已关闭
            case Constants.STATUS_ORDER_CLOSED:
                mTVRedOpt.setVisibility(View.GONE);
                mTVBlackOpt.setVisibility(View.VISIBLE);
                mTVBlackOpt.setText(ResUtils.getString(R.string.label_order_delete));
                mTVStatusLabel.setText(getWholeOrderStatus(ResUtils.getString(R.string.label_order_close)));
                mIvOrderStatus.setBackground(getResources().getDrawable(R.drawable.ic_order_status_close));

                break;
        }
    }




    private String getWholeOrderStatus(String status) {
//        return ResUtils.getString(R.string.label_order_status) + ": " + status;
        return status;
    }

    @OnClick(R.id.tv_status_order_detail)
    void onCheckOrderTrace(){
        if(mOrderBean.getStatus()!=Constants.STATUS_ORDER_UNPAY) {
            NavHelper.startActivity(getActivity(), OrderTraceActivity.class,OrderTraceActivity.getBundle(mOrderBean));
        }
    }

    @OnClick(R.id.tv_black_opt_order_detail)
    void onBlackOptClick(View view) {
        if (mOrderBean == null) return;
        switch (mOrderBean.getStatus()) {
            case Constants.STATUS_ORDER_UNPAY:
                CancelOrderDialog cancelOrderDialog = new CancelOrderDialog.Builder(context).setCallback(new CancelOrderDialog.Callback() {
                    @Override
                    public void onCancelOpt(int id) {
                        getPresenter().cancelOrder(mOrderBean,id);
                    }
                }).build();
                cancelOrderDialog.show();
                break;
            case Constants.STATUS_ORDER_CLOSED:
                new DeleteOrderDialog.Builder(context).setDeleteCallback(new DeleteOrderDialog.DeleteCallback() {
                    @Override
                    public void onConfirm() {
                        getPresenter().deleteOrder(mOrderBean);
                    }
                }).build().show();
                break;
        }
    }

    @OnClick(R.id.tv_red_opt_order_detail)
    void onRedOptClick(View view) {
        if (mOrderBean == null) return;


        if (UserInfoManager.judeIsLogin(getActivity())) {
            switch (mOrderBean.getStatus()) {
                case Constants.STATUS_ORDER_UNPAY:
                    showPayVerifyDialog();
                    break;
                case Constants.STATUS_ORDER_DELIVERED:
                    if(mOrderBean.isNeedConfirmReceive()){
                        getPresenter().confirmReceiveOrder(mOrderId);
                    }else {
                        showPayVerifyDialog();
                    }
                    break;
            }
        }
    }




    @Override
    public OrderDetailPresenter newPresenter() {
        return new OrderDetailPresenter();
    }

    public void onDeleteSucc(OrderBean orderBean) {
        getActivity().finish();
    }

    public void onCancelSucc(OrderBean orderBean) {
        mTvCountDown.setVisibility(View.GONE);
        updateStatusUI(false);
    }

    public void onDataSucc(OrderBean orderBean) {
        mOrderBean = orderBean;
        updateData(true);

    }

    public void onDataFail() {
    }

    public void onWalletChecked(float integer) {
        mPayDialog = new PayDialog();
        mPayDialog.setPayCallback(new PayDialog.PayCallback() {
            @Override
            public void onConfirm(String password) {
                mPassword = password;

                if(mOrderBean.getStatus()==Constants.STATUS_ORDER_DELIVERED){
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
        getActivity().finish();
    }

    public void onWarehouseData(List<EnumBean> warehouseDatas) {
        if (mOrderBean != null)
            updateWarehouse();
    }

    @OnClick(R.id.tv_clip)
    public void clipSN() {
        if (mOrderBean != null) {
            SystemUtil.copy(context, mOrderBean.getOrderSn());
        }
    }


    public void onPwdError() {
        mPayDialog.setPwdError(true);
        mPayDialog.show(getFragmentManager(),"test");
    }

    @Override
    protected String getPageName() {
        return "订单详情页";
    }
}
