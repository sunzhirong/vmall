package com.ysarch.vmall.common.adapter.viewholder;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.RechargeItemBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 2020/10/18
 **/
public class RechargeItemVH extends AbsViewHolder {

    @BindView(R.id.tv_amount_recharge_item)
    TextView mTVAmount;
    @BindView(R.id.tv_status_recharge_item)
    TextView mTVStatus;
    @BindView(R.id.tv_date_recharge_item)
    TextView mTVDate;
    @BindView(R.id.tv_recharge_no_recharge_item)
    TextView mTVNo;

    private RechargeItemBean mRechargeItemBean;
    public RechargeItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mRechargeItemBean = (RechargeItemBean) data;
        mTVAmount.setText(VMallUtils.convertPriceString(mRechargeItemBean.getAmount()));
        mTVNo.setText(mRechargeItemBean.getRechargeNo());
        mTVDate.setText(TimeUtils.formatDateFromUTC(mRechargeItemBean.getCreateTime()));

        if(mRechargeItemBean.getStatus() == Constants.STATUS_RECHARGE_REJECT &&
                !TextUtils.isEmpty(mRechargeItemBean.getRejectReason())){
            mTVStatus.setText(getStatusLabel(mRechargeItemBean.getStatus()) + "（" + mRechargeItemBean.getRejectReason() + "）");
        } else {
            mTVStatus.setText(getStatusLabel(mRechargeItemBean.getStatus()) );
        }
    }

    private SparseArray<String> mSparseArray = new SparseArray();

    public String getStatusLabel(int status){
        String label = mSparseArray.get(status);
        if(TextUtils.isEmpty(label)){
            switch (status){
                case Constants.STATUS_RECHARGE_AUDIT_WAITING:
                    label = ResUtils.getString(R.string.label_recharge_status_audit_waiting);
                    break;
                case Constants.STATUS_RECHARGE_SUCC:
                    label = ResUtils.getString(R.string.label_recharge_status_succ);
                    break;
                case Constants.STATUS_RECHARGE_REJECT:
                    label = ResUtils.getString(R.string.label_recharge_status_reject);
                    break;
            }

            mSparseArray.put(status, label);
        }
        return label;
    }


    public static int getLayoutRes() {
        return R.layout.item_recharge;
    }
}
