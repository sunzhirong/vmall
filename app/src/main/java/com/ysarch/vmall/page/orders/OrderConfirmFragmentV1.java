package com.ysarch.vmall.page.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CartPromotionGoodsAdapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.component.dialog.CouponDialog;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.bean.GenerateOrderResult;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.page.address.AddressListActivity;
import com.ysarch.vmall.page.orders.presenter.OrderConfirmPresenter;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 28/09/2020
 **/
public class OrderConfirmFragmentV1 extends BaseFragment<OrderConfirmPresenter> {
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


    @BindView(R.id.ctv_pay_online_order_confirm)
    CompatTextView mCTVPayOnline;
    @BindView(R.id.ctv_pay_on_delivery_order_confirm)
    CompatTextView mCTVPayOnDelivery;
    @BindView(R.id.iv_right_arrow_coupon_order_confirm)
    ImageView mIVArrowCoupon;


    private GenerateOrderConfirmResult mGenerateOrderConfirmResult;
    private AddressItemBean mAddressItemBean;
    private CartPromotionGoodsAdapter mAdapter;
    private List<GenerateOrderConfirmResult.CartPromotionItemListBean> mCartPromotionItemList;
    private CouponBean mSelCoupon;
    private List<CouponBean> mCouponBeans = new ArrayList<>();

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        mCTVPayOnline.setSelected(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mGenerateOrderConfirmResult = (GenerateOrderConfirmResult) getArguments().getSerializable(BundleKey.ARG_ORDER_GENERATE_RESULT);
        }

        if (mGenerateOrderConfirmResult != null) {
            mAddressItemBean = mGenerateOrderConfirmResult.getAddressItemBean();
            updateAddressInfo();

            mCartPromotionItemList = mGenerateOrderConfirmResult.getCartPromotionItemList();
            if (CollectionUtils.isNotEmpty(mCartPromotionItemList)) {
                initAdapter();
                mAdapter.requestData(mCartPromotionItemList);
            }

            if (mGenerateOrderConfirmResult.getCalcAmount() != null) {
                mTVTotalAmount.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getTotalAmount()));
                mTVFreight.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getFreightAmount()));
                mTVPayAmount.setText(VMallUtils.convertPriceString(mGenerateOrderConfirmResult.getCalcAmount().getPayAmount()));
            }

            // 优惠券选择
            mCouponBeans.clear();
            if (CollectionUtils.isNotEmpty(mGenerateOrderConfirmResult.getCouponHistoryDetailList())) {
                for (int i = 0; i < mGenerateOrderConfirmResult.getCouponHistoryDetailList().size(); i++) {
                    mCouponBeans.add(mGenerateOrderConfirmResult.getCouponHistoryDetailList().get(i).getCoupon());
                }
                mTVCoupon.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
                        mCouponBeans.size()));
                mTVCoupon.setTextColor(ResUtils.getColor(R.color.colorPrimary));
                mIVArrowCoupon.setVisibility(View.VISIBLE);
            } else {
                mTVCoupon.setText(ResUtils.getString(R.string.label_coupon_empty_to_use));
                mTVCoupon.setTextColor(0xff343434);
                mIVArrowCoupon.setVisibility(View.GONE);
            }


//            for (int i = 0; i < 6; i++) {
//                CouponBean couponBean = new CouponBean();
//                couponBean.setId(i);
//                couponBean.setAmount(23.2f * i);
//                couponBean.setName("测试 1" + i);
//                couponBean.setEndTime("2020-10-02");
//                mCouponBeans.add(couponBean);
//            }
//
//            mTVCoupon.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
//                    mCouponBeans.size()));
        }
    }

    /**
     * 更新地址信息
     */
    private void updateAddressInfo() {
        if (mAddressItemBean != null) {
            mTVUserInfo.setText(VMallUtils.decodeString(mAddressItemBean.getName()) + "  "
                    + VMallUtils.decodeString(mAddressItemBean.getPhoneNumber()));
            mTVAddress.setText(VMallUtils.decodeString(mAddressItemBean.getDetailAddress()));
            mTVSubmit.setEnabled(true);
        } else {
            mTVSubmit.setEnabled(false);
        }
    }

    @OnClick({R.id.tv_submit_order_confirm, R.id.ctl_address_order_confirm,
            R.id.ctv_pay_online_order_confirm, R.id.ctv_pay_on_delivery_order_confirm,
            R.id.tv_coupon_info_order_confirm, R.id.iv_right_arrow_coupon_order_confirm})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit_order_confirm:
                if (CollectionUtils.isEmpty(mCartPromotionItemList)) {
                    showTs(ResUtils.getString(R.string.tips_data_error));
                    getActivity().finish();
                    return;
                }

                if (mAddressItemBean == null) {
                    showTs(ResUtils.getString(R.string.tip_pls_select_address));
                    return;
                }
                int payType = mCTVPayOnline.isSelected() ? 1 : 0;

                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < mCartPromotionItemList.size(); i++) {
                    ids.add(mCartPromotionItemList.get(i).getId());
                }


//                if (mSelCoupon != null)
//                    getPresenter().generateOrder(ids, mSelCoupon.getId(), mAddressItemBean.getId(), payType, 0);
//                else
//                    getPresenter().generateOrder(ids, 0, mAddressItemBean.getId(), payType, 0);

                break;
            case R.id.ctl_address_order_confirm:
                NavHelper.startActivity(getActivity(), AddressListActivity.class,
                        AddressListActivity.getBundle(mAddressItemBean), RequestCode.CODE_SELECTED_ADDRESS);
                break;
            case R.id.ctv_pay_online_order_confirm:
                if (!view.isSelected()) {
                    view.setSelected(true);
                    mCTVPayOnDelivery.setSelected(false);
                }
                break;
            case R.id.ctv_pay_on_delivery_order_confirm:
                if (!view.isSelected()) {
                    view.setSelected(true);
                    mCTVPayOnline.setSelected(false);
                }

            case R.id.tv_coupon_info_order_confirm:
            case R.id.iv_right_arrow_coupon_order_confirm:
                if(CollectionUtils.isNotEmpty(mCouponBeans)){
                    CouponDialog dialog = new CouponDialog.Builder(getContext())
                            .setCouponBeans(mCouponBeans)
                            .setSelCoupon(mSelCoupon)
                            .setCallback(new CouponDialog.Callback() {
                                @Override
                                public void onCouponOpt(CouponBean couponBean) {
                                    mSelCoupon = couponBean;
                                    if (couponBean != null)
                                        mTVCoupon.setText(couponBean.getName());
                                    else
                                        mTVCoupon.setText(String.format(ResUtils.getString(R.string.format_tip_num_coupon_available),
                                                mCouponBeans.size()));
                                }
                            })
                            .build();
                    dialog.show();
                }
                break;
        }
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new CartPromotionGoodsAdapter(getContext(), BeeGlide.with(getContext()));
            mRcyGoods.setLayoutManager(new LinearLayoutManager(getContext()));
            mRcyGoods.setAdapter(mAdapter);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.CODE_SELECTED_ADDRESS) {
            if (data != null && data.getExtras() != null) {
                AddressItemBean addressItemBean = (AddressItemBean) data.getExtras().getSerializable(BundleKey.ARG_ADDRESS_BEAN);
                if (addressItemBean != null && (mAddressItemBean == null
                        || mAddressItemBean.getId() != addressItemBean.getId())) {
                    mAddressItemBean = addressItemBean;
                    updateAddressInfo();
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
        NavHelper.startActivity(getActivity(), OrderDetailActivity.class,
                OrderDetailActivity.getBundle(result.getData().getOrder().getId()));
        getActivity().finish();
    }
}
