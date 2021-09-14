package com.ysarch.vmall.common.adapter.viewholder;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.local.MultiSkuEntity;
import com.ysarch.vmall.utils.ResUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 12/10/2020
 **/
public class MultiSkuVH extends AbsViewHolder {
    @BindView(R.id.tv_sku_add_item)
    TextView mTVSku;
    @BindView(R.id.iv_plus_sku_add_item)
    ImageView mIVPlus;
    @BindView(R.id.iv_minus_sku_add_item)
    ImageView mIVMinus;
    @BindView(R.id.tv_num_sku_add_item)
    TextView mTVNum;
    @BindView(R.id.tv_storage_sku_add_item)
    TextView mTVStorage;
    @BindView(R.id.tv_price_sku_add_item)
    TextView mTVPrice;
    @BindView(R.id.v_gap_props_multi_sku_dialog)
    View mVGap;

    private MultiSkuEntity mMultiSkuEntity;
    private Callback mCallback;
    private String mStoragePrefix;


    public MultiSkuVH(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_sku_multi;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mMultiSkuEntity = (MultiSkuEntity) data;
        mCallback = (Callback) callback;
        checkQuantity();
        if(mMultiSkuEntity.getSkuBeanV2().getFloatPrice() != 0){
            mTVPrice.setText("$" + mMultiSkuEntity.getSkuBeanV2().getPrice());
//            mTVPrice.setVisibility(View.VISIBLE);
            mVGap.setVisibility(View.VISIBLE);
        } else {
            mTVPrice.setText("");
//            mTVPrice.setVisibility(View.GONE);
            mVGap.setVisibility(View.GONE);
        }
//        mTVPrice.setText("$" + mMultiSkuEntity.getSkuBeanV2().getPrice());
        mTVStorage.setText(getStoragePrefix() + mMultiSkuEntity.getSkuBeanV2().getQuantity());
        mTVNum.setText(mMultiSkuEntity.getAmount() + "");
        mTVSku.setText(mMultiSkuEntity.getLocalSkuEntity().getSkuLabel());
        mTVNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("MultiSkuVH", "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("MultiSkuVH", "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MultiSkuVH", "afterTextChanged");
            }
        });
    }

    public String getStoragePrefix() {
        if (TextUtils.isEmpty(mStoragePrefix)) {
            mStoragePrefix = ResUtils.getString(R.string.label_storage);
        }
        return mStoragePrefix;
    }

    @OnClick({R.id.iv_minus_sku_add_item, R.id.iv_plus_sku_add_item})
    void onViewClick(View view) {
        if (view.getId() == R.id.iv_minus_sku_add_item) {
            if (mMultiSkuEntity.getAmount() > 0) {
                mMultiSkuEntity.setAmount(mMultiSkuEntity.getAmount() - 1);
                mTVNum.setText(mMultiSkuEntity.getAmount() + "");
                checkQuantity();
                if (mCallback != null) {
                    mCallback.onSkuNumChanged(mPosition, mMultiSkuEntity);
                }
            }
        } else {
            if (mMultiSkuEntity.getAmount() < mMultiSkuEntity.getSkuBeanV2().getQuantityInt()) {
                mMultiSkuEntity.setAmount(mMultiSkuEntity.getAmount() + 1);
                mTVNum.setText(mMultiSkuEntity.getAmount() + "");
                checkQuantity();
                if (mCallback != null) {
                    mCallback.onSkuNumChanged(mPosition, mMultiSkuEntity);
                }
            }
        }
    }

    private void checkQuantity() {
        mIVPlus.setEnabled(mMultiSkuEntity.getAmount() < mMultiSkuEntity.getSkuBeanV2().getQuantityInt());
        mIVMinus.setEnabled(mMultiSkuEntity.getAmount() > 0);
    }

    public interface Callback {
        void onSkuNumChanged(int position, MultiSkuEntity multiSkuEntity);
    }
}
