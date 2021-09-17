package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressItemVH extends AbsViewHolder {

    @BindView(R.id.tv_name_phone_address_item)
    TextView mTVNamePhone;
    @BindView(R.id.tv_mark_default_address_item)
    TextView mTVDefault;
    @BindView(R.id.tv_address_detail_address_item)
    TextView mTVDetail;
    @BindView(R.id.ctv_set_default_address_item)
    CompatTextView mCTVSetDefault;
    @BindView(R.id.iv_delete_address_item)
    ImageView mIVDelete;

    private AddressItemBean mAddressItemBean;
    private Callback mCallback;

    private int mMode;
    private long mSelAddressId = -1;

    public AddressItemVH(View itemView, int mode, long selectedId) {
        super(itemView);
        mSelAddressId = selectedId;
        mMode = mode;
        ButterKnife.bind(this, itemView);

        if (mMode == Constants.MODE_ADDRESS_SELECT) {
            mIVDelete.setVisibility(View.GONE);
        }
    }

    public AddressItemVH(View itemView) {
        this(itemView, Constants.MODE_ADDRESS_EDIT, -1);
    }

    public static int getLayoutRes() {
        return R.layout.item_address;
    }

    @OnClick({R.id.iv_delete_address_item, R.id.ctv_set_default_address_item,
            R.id.iv_edit_address_item})
    void onViewClick(View view) {
        if (mCallback == null)
            return;
        switch (view.getId()) {
            case R.id.iv_delete_address_item:
                mCallback.onDeleteAddress(mPosition, mAddressItemBean);
                break;
            case R.id.ctv_set_default_address_item:
                mCallback.onSetDefaultClick(mPosition, mAddressItemBean);
                break;
            case R.id.iv_edit_address_item:
                mCallback.onEditClick(mPosition, mAddressItemBean);
                break;
        }
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCallback = (Callback) callback;
        mAddressItemBean = (AddressItemBean) data;

        mTVNamePhone.setText(VMallUtils.decodeString(mAddressItemBean.getName()) + " " + mAddressItemBean.getPhoneNumber());

//        mTVDetail.setText(VMallUtils.decodeString(mAddressItemBean.getDetailAddress()));
        mTVDetail.setText(VMallUtils.getAddress(mAddressItemBean.getProvince(),mAddressItemBean.getCity(),mAddressItemBean.getRegion()));

        if (mAddressItemBean.getDefaultStatus() == 1) {
            mTVDefault.setVisibility(View.VISIBLE);
            mCTVSetDefault.setSelected(true);
        } else {
            mTVDefault.setVisibility(View.GONE);
            mCTVSetDefault.setSelected(false);
        }

        if (mMode == Constants.MODE_ADDRESS_SELECT) {
            if (mSelAddressId == mAddressItemBean.getId()) {
                int color = ResUtils.getColor(R.color.colorPrimary);
                mTVNamePhone.setTextColor(color);
                mTVDetail.setTextColor(color);
            } else {
                mTVNamePhone.setTextColor(0xff343434);
                mTVDetail.setTextColor(0xff343434);
            }
        }
    }


    public interface Callback {
        void onDeleteAddress(int position, AddressItemBean addressItemBean);

        void onEditClick(int position, AddressItemBean addressItemBean);

        void onSetDefaultClick(int position, AddressItemBean addressItemBean);
    }
}
