package com.ysarch.vmall.page.orders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CartPromotionGoodsAdapter;
import com.ysarch.vmall.common.adapter.viewholder.CartPromotionGoodsVH;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.component.dialog.CouponDialog;
import com.ysarch.vmall.component.dialog.PayDialog;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.bean.EnumBean;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.bean.GenerateOrderResult;
import com.ysarch.vmall.domain.bean.UpdateCartBean;
import com.ysarch.vmall.domain.bean.WholeGenerateOrderResult;
import com.ysarch.vmall.domain.bean.WmsWarehouseInfoBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.helper.DialogHelper;
import com.ysarch.vmall.page.address.AddressEditActivity;
import com.ysarch.vmall.page.address.AddressListActivity;
import com.ysarch.vmall.page.orders.presenter.OrderConfirmPresenter;
import com.ysarch.vmall.page.recharge.RechargeSuccessActivity;
import com.ysarch.vmall.utils.LanguageUtils;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 28/09/2020
 **/
public class OrderConfirmFragment extends BaseFragment<OrderConfirmPresenter> {
    @BindView(R.id.tv_submit_order_confirm)
    TextView mTVSubmit;
    @BindView(R.id.tv_pay_amount_order_confirm)
    TextView mTVPayAmount;
    @BindView(R.id.tv_freight_order_confirm)
    TextView mTVFreight;
    @BindView(R.id.tv_total_price_order_confirm)
    TextView mTVTotalAmount;
    @BindView(R.id.tv_coupon_info_order_confirm)
    TextView mTVCoupon;
    @BindView(R.id.rcy_goods_order_confirm)
    RecyclerView mRcyGoods;
    @BindView(R.id.tv_user_info_order_confirm)
    TextView mTVUserInfo;
    @BindView(R.id.tv_address_order_confirm)
    TextView mTVAddress;
    @BindView(R.id.iv_right_arrow_coupon_order_confirm)
    ImageView mIVArrowCoupon;


    @BindView(R.id.et_remark_order_confirm)
    EditText mETRemark;
    @BindView(R.id.ctv_mode_delivery_order_confirm)
    CompatTextView mCTVModeDelivery;
    @BindView(R.id.ly_land_mode_delivery_order_confirm)
    LinearLayout mLyDeliveryLand;
    @BindView(R.id.ly_sea_mode_delivery_order_confirm)
    LinearLayout mLyDeliverySea;
    @BindView(R.id.ly_fright_mode_delivery_order_confirm)
    LinearLayout mLyDeliveryFright;
    @BindView(R.id.tv_address_warehouse_order_confirm)
    TextView mTVWarehouseAddress;
    @BindView(R.id.tv_user_info_order_confirm_self)
    TextView mTvWarehouseUserInfo;

    @BindView(R.id.iv_dis_mode_self)
    ImageView mIvDisModeSelf;
    @BindView(R.id.iv_dis_mode_delivery)
    ImageView mIvDisModeDelivery;
    @BindView(R.id.ll_receive_address)
    LinearLayout mLlReceiveAddress;
    @BindView(R.id.ll_pick_address)
    LinearLayout mLlPickAddress;
    @BindView(R.id.tv_goods_price)
    TextView mTvGoodsPrice;
    @BindView(R.id.tv_coupon_use)
    TextView mTvCouponUse;
    @BindView(R.id.tv_coupon_price)
    TextView mTvCouponPrice;
    @BindView(R.id.tv_server_price)
    TextView mTvServerPrice;
    @BindView(R.id.iv_coupon)
    ImageView mIvCoupon;
    @BindView(R.id.tv_receive_address)
    TextView mTvReceiveAddress;
    @BindView(R.id.tv_no_use)
    TextView mTvNoUse;

    @BindView(R.id.tv_freight_price)
    TextView mTvFreightPrice;
    @BindView(R.id.rl_freight)
    RelativeLayout mRlFreight;
    @BindView(R.id.rl_coupon)
    RelativeLayout mRlCoupon;


    private GenerateOrderConfirmResult mGenerateOrderConfirmResult;
    private AddressItemBean mAddressItemBean;
    private CartPromotionGoodsAdapter mAdapter;
    private List<GenerateOrderConfirmResult.CartPromotionItemListBean> mCartPromotionItemList;
    private CouponBean mSelCoupon;
    private List<CouponBean> mCouponBeans = new ArrayList<>();

    private EnumBean mDeliveryModeSelected;
//    private EnumBean mWarehouseSelected;
    private int mTransportMode;
    private List<UpdateCartBean> mCartList;

    private boolean isUpdateCoupon = true;
    private GenerateOrderResult.OrderBean mOrderBean;
    private String mPassword;

//    private boolean aotuChooseSelfAddress = false;//没有仓库数据时，请求后台并且填入默认仓库
    private PayDialog mPayDialog;
    private WmsWarehouseInfoBean mWarehouseSelected;

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
//        mCTVPayOnline.setSelected(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mGenerateOrderConfirmResult = (GenerateOrderConfirmResult) getArguments().getSerializable(BundleKey.ARG_ORDER_GENERATE_RESULT);
        }

        if (mGenerateOrderConfirmResult != null) {
//            String warehouseData = CacheHelper.getString(CacheKeys.KEY_ORDER_WAREHOUSE, "");
//            try{
//                mWarehouseSelected = new Gson().fromJson(warehouseData, EnumBean.class);
//                mTVWarehouseAddress.setText(mWarehouseSelected.getDesc());
//                mTVWarehouseAddress.setVisibility(View.VISIBLE);
//            }catch (Exception e){
//                aotuChooseSelfAddress = true;
//                getPresenter().requestWarehouseData();
//            }
            updateCartUI();

        } else {
            showTs(getString(R.string.tips_data_error));
            getActivity().finish();
        }
    }

    private void updateCartUI() {
        if(mCartPromotionItemList==null){
            mCartPromotionItemList = new ArrayList<>();
        }else {
            mCartPromotionItemList.clear();
        }
        mAddressItemBean = mGenerateOrderConfirmResult.getAddressItemBean();
        mWarehouseSelected = mGenerateOrderConfirmResult.getWmsWarehouseInfo();
        updateAddressInfo();

//        mCartPromotionItemList = mGenerateOrderConfirmResult.getCartPromotionItemList();
        List<GenerateOrderConfirmResult.SameSellerCartPromotionBean> sameList = mGenerateOrderConfirmResult.getSameSellerCartPromotionItemList();
        for (GenerateOrderConfirmResult.SameSellerCartPromotionBean sameBean : sameList){
            mCartPromotionItemList.addAll(sameBean.getCartPromotionItemList());
        }
        if (CollectionUtils.isNotEmpty(mCartPromotionItemList)) {
            initAdapter();
            mAdapter.requestData(mCartPromotionItemList);
        }

        if (mGenerateOrderConfirmResult.getCalcAmount() != null) {
            mTVTotalAmount.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getTotalAmount()));
            mTVFreight.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getFreightAmount()));
//            mTVPayAmount.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getPayAmount()));
            mTVPayAmount.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getPayAmount()));

            if(mGenerateOrderConfirmResult.getCalcAmount().getCnFreight()==0){
                mRlFreight.setVisibility(View.GONE);
            }else {
                mRlFreight.setVisibility(View.VISIBLE);
                mTvFreightPrice.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getCnFreight()));
            }

        }



        // 优惠券选择
        mCouponBeans.clear();
        if (CollectionUtils.isNotEmpty(mGenerateOrderConfirmResult.getCouponHistoryDetailList())) {
            for (int i = 0; i < mGenerateOrderConfirmResult.getCouponHistoryDetailList().size(); i++) {
                CouponBean coupon = mGenerateOrderConfirmResult.getCouponHistoryDetailList().get(i).getCoupon();
                coupon.setCouponId(coupon.getId());
                coupon.setId(mGenerateOrderConfirmResult.getCouponHistoryDetailList().get(i).getId());
                mCouponBeans.add(coupon);
            }
            mTVCoupon.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
                    mCouponBeans.size()));
            mTVCoupon.setTextColor(ResUtils.getColor(R.color.colorPrimary));
            mIVArrowCoupon.setVisibility(View.VISIBLE);

//            //new
//            mTvCouponUse.setVisibility(View.VISIBLE);
//            if(mCouponBeans==null||mCouponBeans.size()==0){
//                mTvCouponUse.setText(R.string.no_coupon_available);
//            }else {
//                mTvCouponUse.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
//                        mCouponBeans.size()));
//                mIvCoupon.setVisibility(View.VISIBLE);
//            }
            mTvCouponUse.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
                    mCouponBeans.size()));
            mIvCoupon.setVisibility(View.VISIBLE);
            mTvNoUse.setVisibility(View.GONE);
            mRlCoupon.setVisibility(View.VISIBLE);

        } else {
            mTVCoupon.setText(ResUtils.getString(R.string.label_coupon_empty_to_use));
            mTVCoupon.setTextColor(0xff343434);
            mIVArrowCoupon.setVisibility(View.GONE);


            //new
            mTvCouponUse.setVisibility(View.GONE);
            mTvCouponUse.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
                    mCouponBeans.size()));
            mIvCoupon.setVisibility(View.GONE);
            mTvNoUse.setVisibility(View.VISIBLE);
            mRlCoupon.setVisibility(View.GONE);

        }


        mDeliveryModeSelected = AppContext.getsInstance().getModeDelivery().get(0);
        mCTVModeDelivery.setText(mDeliveryModeSelected.getDesc());
        onTransportModeSelected(Constants.TYPE_TRANSPORT_LAND);



        //配送方式
//        int anInt = CacheHelper.getInt(CacheKeys.KEY_ORDER_DIS_MODE, 0);
//        if (anInt == 0) {
        if (mGenerateOrderConfirmResult.getShippingMethod() == Constants.SHIPPING_METHOD_DELIVERY&&mAddressItemBean!=null) {
            mLlPickAddress.setVisibility(View.GONE);
            mIvDisModeSelf.setSelected(false);
            mLlReceiveAddress.setVisibility(View.VISIBLE);
            mIvDisModeDelivery.setSelected(true);


        } else {
            mLlPickAddress.setVisibility(View.VISIBLE);
            mIvDisModeSelf.setSelected(true);
            mLlReceiveAddress.setVisibility(View.GONE);
            mIvDisModeDelivery.setSelected(false);

        }

        if (mCouponBeans.size() != 0) {
            Collections.sort(mCouponBeans, new Comparator<CouponBean>() {
                @Override
                public int compare(CouponBean o1, CouponBean o2) {
                    return o1.getAmount() > o2.getAmount() ? -1 : 1;
                }
            });
        } else {
            mSelCoupon = null;
        }
        if (isUpdateCoupon&&mCouponBeans.size()!=0) {
            //默认选中最大的优惠券
            mSelCoupon = mCouponBeans.get(0);
        }


        updateCouponUI();
        mTvGoodsPrice.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getTotalAmount()));
        mTvServerPrice.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getServiceFeeAmount()));
    }

    private void updateCouponUI() {
        if (mSelCoupon != null) {
            mTvCouponPrice.setVisibility(View.VISIBLE);
            mTvCouponPrice.setText(String.format(ResUtils.getString(R.string.format_coupon_drop_off)
                    , VMallUtils.convertPriceString(mSelCoupon.getAmount())));
        } else {
            mTvCouponPrice.setVisibility(View.GONE);
            mTVCoupon.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
                    mCouponBeans.size()));
        }
    }

    /**
     * 更新地址信息
     */
    private void updateAddressInfo() {
        if (mAddressItemBean != null) {
//            mTVAddAddress.setVisibility(View.GONE);
            mTVUserInfo.setText(VMallUtils.decodeString(mAddressItemBean.getName()) + "  "
                    + VMallUtils.decodeString(mAddressItemBean.getPhoneNumber()));
//            mTVAddress.setText(VMallUtils.decodeString(mAddressItemBean.getDetailAddress()));

            if(!TextUtils.isEmpty(mAddressItemBean.getDetailAddress())) {
                mTVAddress.setText(mAddressItemBean.getDetailAddress() + "," + VMallUtils.getAddress(mAddressItemBean.getProvince(), mAddressItemBean.getCity(), mAddressItemBean.getRegion()));
            }else {
                mTVAddress.setText(VMallUtils.getAddress(mAddressItemBean.getProvince(), mAddressItemBean.getCity(), mAddressItemBean.getRegion()));
            }



//            mTVSubmit.setEnabled(true);
        } else {
//            mTVSubmit.setEnabled(false);
//            mTVAddAddress.setVisibility(View.VISIBLE);
        }


        if(mWarehouseSelected!=null){
            switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
                case Constants.ID_LAN_KM:
                    mTVWarehouseAddress.setText(mWarehouseSelected.getKhAddress());
                    break;
                case Constants.ID_LAN_ZH:
                    mTVWarehouseAddress.setText(mWarehouseSelected.getAddress());
                    break;
                case Constants.ID_LAN_EN:
                    mTVWarehouseAddress.setText(mWarehouseSelected.getEnAddress());
                    break;
                default:
                    mTVWarehouseAddress.setText(mWarehouseSelected.getAddress());
                    break;

            }
            mTvWarehouseUserInfo.setText(VMallUtils.decodeString(mWarehouseSelected.getName()) + "  "
                    + VMallUtils.decodeString(mWarehouseSelected.getPhone()));
        }
    }

    @OnClick({R.id.tv_submit_order_confirm,
            R.id.rl_address,
//            R.id.tv_add_address_order_confirm,
            R.id.tv_coupon_info_order_confirm, R.id.iv_right_arrow_coupon_order_confirm,
            R.id.tv_mode_delivery_prefix_order_confirm, R.id.ctv_mode_delivery_order_confirm,
            R.id.ly_land_mode_delivery_order_confirm, R.id.ly_sea_mode_delivery_order_confirm, R.id.ly_fright_mode_delivery_order_confirm,
            R.id.ly_warehouse_order_confirm,
            R.id.ll_dis_mode_delivery, R.id.ll_dis_mode_self, R.id.rl_coupon})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit_order_confirm:
                if (CollectionUtils.isEmpty(mCartPromotionItemList)) {
                    showTs(ResUtils.getString(R.string.tips_data_error));
                    getActivity().finish();
                    return;
                }

//

                int payType = 0;   //mCTVPayOnline.isSelected() ? 1 : 0;


                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < mCartPromotionItemList.size(); i++) {
                    ids.add(mCartPromotionItemList.get(i).getId());
                }

                String mRemark = mETRemark.getText().toString();
//                List<Integer> ids, long couponId,
//                long addressId,
//                int payType, int deliveryType, int shippingType, int warehouse,
//                int useIntegration, String remark


                boolean isSelf = mIvDisModeSelf.isSelected();

                if (!isSelf&&mAddressItemBean == null) {
                    showTs(ResUtils.getString(R.string.tip_pls_select_address));
                    return;
                }

                long addressId;
                if(mAddressItemBean==null){
                    addressId = 0;
                }else {
                    addressId = mAddressItemBean.getId();
                }


                if (mSelCoupon != null)
                    getPresenter().generateOrder(ids, mSelCoupon.getCouponId(),mSelCoupon.getId(), addressId, payType,
                            mDeliveryModeSelected.getId(), isSelf?1:2,mTransportMode, isSelf ? mWarehouseSelected.getId():-1 ,
                            0, mRemark);
                else
                    getPresenter().generateOrder(ids, 0, 0,addressId, payType,
                            mDeliveryModeSelected.getId(), isSelf?1:2,mTransportMode, isSelf ? mWarehouseSelected.getId():-1,
                            0, mRemark);

                break;
            case R.id.rl_address:
//            case R.id.tv_add_address_order_confirm:
                NavHelper.startActivity(getActivity(), AddressListActivity.class,
                        AddressListActivity.getBundle(mAddressItemBean), RequestCode.CODE_SELECTED_ADDRESS);
                break;
            case R.id.rl_coupon:
                if (CollectionUtils.isNotEmpty(mCouponBeans)) {
                    CouponDialog dialog = new CouponDialog.Builder(getContext())
                            .setCouponBeans(mCouponBeans)
                            .setSelCoupon(mSelCoupon)
                            .setCallback(new CouponDialog.Callback() {
                                @Override
                                public void onCouponOpt(CouponBean couponBean) {
                                    mSelCoupon = couponBean;
                                    isUpdateCoupon = false;
                                    if (couponBean != null) {
                                        getPresenter().updateConfirmOrder(mCartList, (int) mSelCoupon.getId());
                                    } else {
                                        getPresenter().updateConfirmOrder(mCartList, 0);
                                    }
                                }
                            })
                            .build();
                    dialog.show();
                }
                break;
            case R.id.ly_warehouse_order_confirm:
//                getPresenter().requestWarehouseData();
                break;
            case R.id.tv_mode_delivery_prefix_order_confirm:
            case R.id.ctv_mode_delivery_order_confirm:
                DialogHelper.showDeliveryType(getContext(), AppContext.getsInstance().getModeDelivery(), mDeliveryModeSelected,
                        (options1, options2, options3, v) -> {
                            mDeliveryModeSelected = AppContext.getsInstance().getModeDelivery().get(options1);
                            mCTVModeDelivery.setText(mDeliveryModeSelected.getDesc());
                        });
                break;
            case R.id.ly_land_mode_delivery_order_confirm:
                onTransportModeSelected(Constants.TYPE_TRANSPORT_LAND);
                break;
            case R.id.ly_sea_mode_delivery_order_confirm:
                onTransportModeSelected(Constants.TYPE_TRANSPORT_SEA);
                break;
            case R.id.ly_fright_mode_delivery_order_confirm:
                onTransportModeSelected(Constants.TYPE_TRANSPORT_FRIGHT);
                break;

            case R.id.ll_dis_mode_self:
                //到店自取
//                CacheHelper.putInt(CacheKeys.KEY_ORDER_DIS_MODE, 0);
                mIvDisModeDelivery.setSelected(false);
                mIvDisModeSelf.setSelected(true);
                mTvReceiveAddress.setText(getString(R.string.label_order_pick_address));
                mLlReceiveAddress.setVisibility(View.GONE);
                mLlPickAddress.setVisibility(View.VISIBLE);
                break;

            case R.id.ll_dis_mode_delivery:
                //快递配送
                if(mAddressItemBean==null){
                    //直接添加新地址
                    NavHelper.startActivity(this, AddressEditActivity.class,
                            AddressEditActivity.getConfirmOrderOptBundle(), RequestCode.CODE_EDIT_ADDRESS);
//                    NavHelper.startActivity(getActivity(), AddressListActivity.class,
//                            AddressListActivity.getBundle(mAddressItemBean), RequestCode.CODE_SELECTED_ADDRESS);
                }else{
//                    mWarehouseSelected=null;
                    mIvDisModeDelivery.setSelected(true);
                    mIvDisModeSelf.setSelected(false);
                    mLlReceiveAddress.setVisibility(View.VISIBLE);
                    mLlPickAddress.setVisibility(View.GONE);
                    mTvReceiveAddress.setText(getString(R.string.label_order_receive_address));
                }



//                CacheHelper.putInt(CacheKeys.KEY_ORDER_DIS_MODE, 1);
                break;
        }
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mRcyGoods.setFocusable(false);
            mAdapter = new CartPromotionGoodsAdapter(getContext(), BeeGlide.with(getContext()));
            mRcyGoods.setLayoutManager(new LinearLayoutManager(getContext()));
//            mAdapter.setOnItemClickListener(new ItemDataWrapper.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position, Object data) {
//                    if (data != null) {
//                        GenerateOrderConfirmResult.CartPromotionItemListBean bean =
//                                (GenerateOrderConfirmResult.CartPromotionItemListBean) data;
//                        NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
//                                GoodsDetailActivity.getBundle(bean.getProductId(), bean.getSource()));
//                    }
//
//                }
//            });
            mAdapter.setCallback(new CartPromotionGoodsVH.Callback() {
                @Override
                public void onItemNumChange(int position, GenerateOrderConfirmResult.CartPromotionItemListBean cartGoodsBean, boolean isAdd) {
                    for (UpdateCartBean bean : mCartList) {
                        if (bean.cartId == cartGoodsBean.getId()) {
                            if (isAdd) {
                                bean.num++;
                            } else {
                                bean.num--;
                            }
                            break;
                        }
                    }
                    isUpdateCoupon = true;
                    if (mSelCoupon == null) {
                        getPresenter().updateConfirmOrder(mCartList, 0);
                    } else {
                        getPresenter().updateConfirmOrder(mCartList, (int) mSelCoupon.getId());
                    }
                }
            });
            mRcyGoods.setAdapter(mAdapter);
        }

        mCartList = new ArrayList<>();
        List<GenerateOrderConfirmResult.CartPromotionItemListBean> cartPromotionItemList = mGenerateOrderConfirmResult.getCartPromotionItemList();
        for (GenerateOrderConfirmResult.CartPromotionItemListBean bean : cartPromotionItemList) {
            UpdateCartBean updateCartBean = new UpdateCartBean();
            updateCartBean.cartId = bean.getId();
            updateCartBean.num = bean.getQuantity();
            mCartList.add(updateCartBean);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.CODE_SELECTED_ADDRESS || requestCode==RequestCode.CODE_EDIT_ADDRESS) {
            if (data != null && data.getExtras() != null) {
                AddressItemBean addressItemBean = (AddressItemBean) data.getExtras().getSerializable(BundleKey.ARG_ADDRESS_BEAN);
                if (addressItemBean != null && (mAddressItemBean == null
                        || mAddressItemBean.getId() != addressItemBean.getId())) {
                    mAddressItemBean = addressItemBean;
                    updateAddressInfo();


//                    mWarehouseSelected=null;
                    mIvDisModeDelivery.setSelected(true);
                    mIvDisModeSelf.setSelected(false);
                    mLlReceiveAddress.setVisibility(View.VISIBLE);
                    mLlPickAddress.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_confirm;
    }

    @Override
    public OrderConfirmPresenter newPresenter() {
        return new OrderConfirmPresenter();
    }

    /**
     * 订单提交chenggong
     *
     * @param result
     */
    public void onOrderConfirmSucc(GenerateOrderResult result) {
        if(result.getData().getOrder().getStatus()==0){
            mOrderBean = result.getData().getOrder();
            getPresenter().checkWallet();
        }else if(result.getData().getOrder().getStatus()==5){
            getActivity().finish();
            NavHelper.startActivity(this, RechargeSuccessActivity.class,RechargeSuccessActivity.getBundle("订单提交待审核成功页"));
        }


    }

    public void onWalletChecked(float integer) {
        mPayDialog = new PayDialog();
        mPayDialog.setPayCallback(new PayDialog.PayCallback() {
            @Override
            public void onConfirm(String password) {
                mPassword = password;
                getPresenter().payByBalance(mOrderBean.getId(), mOrderBean.getOrderSn(), mPassword);

            }

            @Override
            public void onCancel() {
                NavHelper.startActivity(getActivity(), OrderDetailActivity.class,
                        OrderDetailActivity.getBundle(mOrderBean.getId(), true));
                getActivity().finish();
            }

//            @Override
//            public void onForgetPayPassword() {
//                NavHelper.startActivity(getActivity(), PayPwdModifyActivity.class);
//            }
        });

        mPayDialog.setPrice(mOrderBean.getPayAmount());
        mPayDialog.show(getFragmentManager(), "test");
    }

    public void onPaySucc() {
        showTs(getString(R.string.label_pay_succ));
        getActivity().finish();
        NavHelper.startActivity(this, OrderPaidActivity.class);
    }


    public void onOrderUpdateSucc(WholeGenerateOrderResult result) {

        mGenerateOrderConfirmResult = result.getData();
        updateCartUI();


    }

    /**
     * 运输方式选择
     *
     * @param value
     */
    private void onTransportModeSelected(int value) {
        switch (value) {
            case Constants.TYPE_TRANSPORT_LAND:
                mLyDeliveryFright.setSelected(false);
                mLyDeliverySea.setSelected(false);
                mLyDeliveryLand.setSelected(true);
                break;
            case Constants.TYPE_TRANSPORT_SEA:
                mLyDeliveryFright.setSelected(false);
                mLyDeliverySea.setSelected(true);
                mLyDeliveryLand.setSelected(false);
                break;
            case Constants.TYPE_TRANSPORT_FRIGHT:
                mLyDeliveryFright.setSelected(true);
                mLyDeliverySea.setSelected(false);
                mLyDeliveryLand.setSelected(false);
                break;
        }

        mTransportMode = value;
    }

    /**
     * 选择提货仓库
     *
     * @param enumBeans
     */
    public void onWarehouseData(List<EnumBean> enumBeans) {
//        if(aotuChooseSelfAddress){
//            aotuChooseSelfAddress = false;
//            mWarehouseSelected = enumBeans.get(0);
//            mTVWarehouseAddress.setText(mWarehouseSelected.getDesc());
//            mTVWarehouseAddress.setVisibility(View.VISIBLE);
//            CacheHelper.putString(CacheKeys.KEY_ORDER_WAREHOUSE,new Gson().toJson(mWarehouseSelected));
//        }else{
//            DialogHelper.showWarehouseDialog(getContext(), enumBeans, mWarehouseSelected, new OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                    mWarehouseSelected = enumBeans.get(options1);
//                    mTVWarehouseAddress.setText(mWarehouseSelected.getDesc());
//                    mTVWarehouseAddress.setVisibility(View.VISIBLE);
//                    CacheHelper.putString(CacheKeys.KEY_ORDER_WAREHOUSE,new Gson().toJson(mWarehouseSelected));
//                }
//            });
//        }



    }


    public void onPwdError() {
        mPayDialog.setPwdError(true);
        mPayDialog.show(getFragmentManager(),"test");
    }

    @Override
    protected String getPageName() {
        return "提交订单页";
    }
}
