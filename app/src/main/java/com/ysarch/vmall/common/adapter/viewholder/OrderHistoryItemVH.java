package com.ysarch.vmall.common.adapter.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.FRecyclerView;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.HistoryOrderGoodsAdapter;
import com.ysarch.vmall.common.adapter.OrderGoodsAdapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.component.dialog.DeleteOrderDialog;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderItemListBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.LPUtils;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fysong on 16/09/2020
 **/
public class OrderHistoryItemVH extends AbsViewHolder {

    private  View mTvDot;
    private  View mLlOtp;
    private  TextView mTvTotalPrice;
    private  TextView mTvPaidPrice;
    private  TextView mTvUnpaidPrice;
    private  TextView mTvGoodsPrice;
    private  TextView mTvFreightPrice;
    private FRecyclerView mFRecyclerView;
    private HistoryOrderGoodsAdapter mOrderGoodsAdapter;
    private OrderBean mOrderBean;
    private TextView mTVOrderSn;
    private TextView mTVStatusLabel;
    private TextView mTVBlackBtn;
    private TextView mTVRedBtn;
    private TextView mTvUnpaidPriceLabel;
    private Callback mCallback;

    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public OrderHistoryItemVH(View itemView, Context context, BeeGlide beeGlide) {
        super(itemView);
        mFRecyclerView = (FRecyclerView) itemView;
        mOrderGoodsAdapter = new HistoryOrderGoodsAdapter(context, beeGlide);
        mOrderGoodsAdapter.setOnItemClickListener(getOnItemClickListener());
        mFRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mFRecyclerView.setAdapter(mOrderGoodsAdapter);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View header = layoutInflater.inflate(R.layout.item_order_history_header, mFRecyclerView, false);
        mFRecyclerView.addHeaderView(header);
        header.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onHistoryItemClick(mPosition, mOrderBean);
            }
        });

        mTVOrderSn = header.findViewById(R.id.tv_sn_order_item);
        mTVStatusLabel = header.findViewById(R.id.tv_status_order_item);

        View footer = layoutInflater.inflate(R.layout.item_order_history_end, mFRecyclerView, false);
        mFRecyclerView.addFooterView(footer);
        footer.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onHistoryItemClick(mPosition, mOrderBean);
            }
        });

        mTVBlackBtn = footer.findViewById(R.id.tv_black_opt_order_item);
        mTVRedBtn = footer.findViewById(R.id.tv_red_opt_order_item);
//        mTVTips = footer.findViewById(R.id.tv_tips_order_history);
        mLlOtp = footer.findViewById(R.id.ly_extra_opt_order_history);

        mTvGoodsPrice = footer.findViewById(R.id.tv_item_goods_price);
        mTvFreightPrice = footer.findViewById(R.id.tv_item_freight_price);
        mTvTotalPrice = footer.findViewById(R.id.tv_item_total_price);
        mTvPaidPrice = footer.findViewById(R.id.tv_item_paid_price);
        mTvUnpaidPrice = footer.findViewById(R.id.tv_item_unpaid_price);
        mTvUnpaidPriceLabel = footer.findViewById(R.id.tv_item_unpaid_price_label);
        mTvDot = footer.findViewById(R.id.tv_dot);

        mTVRedBtn.setOnClickListener(v -> {
            if (mOrderBean == null || mCallback == null) return;
            if (mOrderBean.getStatus() == Constants.STATUS_ORDER_DELIVERED&&mOrderBean.isNeedConfirmReceive())
                mCallback.onConfirmClick(mPosition, mOrderBean);
            else
                mCallback.onPayClick(mPosition, mOrderBean);
        });

        mTVBlackBtn.setOnClickListener(v -> {
            if (mOrderBean == null || mCallback == null) return;

            switch (mOrderBean.getStatus()) {
                case Constants.STATUS_ORDER_UNPAY:
                    mCallback.onCancelClick(mPosition, mOrderBean);
                    break;
                case Constants.STATUS_ORDER_CLOSED:
                    new DeleteOrderDialog.Builder(context).setDeleteCallback(new DeleteOrderDialog.DeleteCallback() {
                        @Override
                        public void onConfirm() {
                            mCallback.onDeleteClick(mPosition, mOrderBean);
                        }
                    }).build().show();
                    break;
            }
        });
    }

    public static View createItemView(Context context) {
        FRecyclerView fRecyclerView = new FRecyclerView(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(LPUtils.FILL, LPUtils.WRAP);
        fRecyclerView.setLayoutParams(layoutParams);
        return fRecyclerView;
    }

    public ItemDataWrapper.OnItemClickListener getOnItemClickListener() {
        if (mOnItemClickListener == null) {
            mOnItemClickListener = (position, data) -> {
                if (mCallback != null) {
                    mCallback.onHistoryItemClick(mPosition, mOrderBean);
                }
            };
        }
        return mOnItemClickListener;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
//        mOrderGoodsAdapter.requestData(4);
        mOrderBean = (OrderBean) data;
        mCallback = (Callback) callback;

        mTVOrderSn.setText(mOrderBean.getOrderSn());
        mTvGoodsPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_price),VMallUtils.convertPriceString(mOrderBean.getPayAmount())));
        mTvFreightPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_freight),VMallUtils.convertPriceString(mOrderBean.getPredictFreightAmount())));
        mTvTotalPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_total),VMallUtils.convertPriceString(mOrderBean.getPayAmount())));
//        if(mOrderBean.getStatus() == Constants.STATUS_ORDER_UNPAY){
//            mTvTotalPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_total),VMallUtils.convertPriceString(mOrderBean.getAlreadyPayAmount())));
//        }else {
//            mTvTotalPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_total),VMallUtils.convertPriceString(mOrderBean.getOrderAmount())));
//        }
//        mTvTotalPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_total),VMallUtils.convertPriceString(mOrderBean.getPayAmount())));
        mTvPaidPrice.setText(String.format(ResUtils.getString(R.string.format_item_goods_paid),VMallUtils.convertPriceString(mOrderBean.getAlreadyPayAmount())));
        mTvUnpaidPrice.setText(VMallUtils.convertPriceString(mOrderBean.getRestAmount()));



        switch (mOrderBean.getStatus()) {
            case Constants.STATUS_ORDER_UNPAY:
                mTVRedBtn.setVisibility(View.VISIBLE);
                mTVBlackBtn.setVisibility(View.VISIBLE);
                mTVRedBtn.setText(ResUtils.getString(R.string.label_order_to_pay));
                mTVBlackBtn.setText(ResUtils.getString(R.string.label_order_cancel));
                mTVStatusLabel.setText(ResUtils.getString(R.string.label_order_unpay));
                mLlOtp.setVisibility(View.VISIBLE);


                setUnpaidVisible(true);

                break;
            case Constants.STATUS_ORDER_AUDIT_WAITING:
                mTVRedBtn.setVisibility(View.GONE);
                mTVBlackBtn.setVisibility(View.GONE);
                mTVStatusLabel.setText(ResUtils.getString(R.string.label_order_audit_waiting));
                mLlOtp.setVisibility(View.GONE);

                setUnpaidVisible(false);

                break;
            case Constants.STATUS_ORDER_DELIVER_WAITING:
                mTVRedBtn.setVisibility(View.GONE);
                mTVBlackBtn.setVisibility(View.GONE);
                mTVStatusLabel.setText(ResUtils.getString(R.string.label_order_deliver_waiting));
                mLlOtp.setVisibility(View.GONE);

                setUnpaidVisible(false);

                break;
            case Constants.STATUS_ORDER_DELIVERED:
                mTVBlackBtn.setVisibility(View.GONE);
                mTVStatusLabel.setText(ResUtils.getString(R.string.label_order_delivered));
                mLlOtp.setVisibility(View.VISIBLE);
                if(mOrderBean.isNeedConfirmReceive()){
                    mTVRedBtn.setVisibility(View.VISIBLE);
                    mTVRedBtn.setText(ResUtils.getString(R.string.label_order_confirm_received));
                    setUnpaidVisible(false);

                }else {
                    if(mOrderBean.isNeedPayFreigth()){
                        mTVRedBtn.setVisibility(View.VISIBLE);
                        mTVRedBtn.setText(ResUtils.getString(R.string.label_order_to_pay));
                        setUnpaidVisible(true);

                    }else {
                        mLlOtp.setVisibility(View.GONE);
                        setUnpaidVisible(false);

                    }
                }

                break;
            //已完成
            case Constants.STATUS_ORDER_COMPLETE:
                mTVRedBtn.setVisibility(View.GONE);
                mTVBlackBtn.setVisibility(View.GONE);
                mTVStatusLabel.setText(ResUtils.getString(R.string.label_order_complete));
                mLlOtp.setVisibility(View.GONE);
                setUnpaidVisible(false);

                break;
            case Constants.STATUS_ORDER_CLOSED:
                mTVRedBtn.setVisibility(View.GONE);
                mTVBlackBtn.setVisibility(View.VISIBLE);
                mTVBlackBtn.setText(ResUtils.getString(R.string.label_order_delete));
                mTVStatusLabel.setText(ResUtils.getString(R.string.label_order_close));
                mLlOtp.setVisibility(View.VISIBLE);
                setUnpaidVisible(false);


                break;
        }

        if (CollectionUtils.isNotEmpty(mOrderBean.getOrderItemList())) {
            mOrderGoodsAdapter.requestData(mOrderBean.getOrderItemList());
        }
    }

    private void setUnpaidVisible(boolean visible) {
        mTvUnpaidPrice.setVisibility(visible?View.VISIBLE:View.GONE);
        mTvDot.setVisibility(visible?View.VISIBLE:View.GONE);
        mTvUnpaidPriceLabel.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    public interface Callback {
        void onDeleteClick(int position, OrderBean orderBean);

        void onCancelClick(int position, OrderBean orderBean);

        void onPayClick(int position, OrderBean orderBean);

        void onHistoryItemClick(int position, OrderBean orderBean);

        void onConfirmClick(int position, OrderBean orderBean);

    }

}
