package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.WalletLogBean;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 2020/10/18
 **/
public class WalletLogItemVH extends AbsViewHolder {
    @BindView(R.id.tv_amount_wallet_log_item)
    TextView mTVAmount;
    @BindView(R.id.tv_date_wallet_log_item)
    TextView mTVDate;
    @BindView(R.id.tv_dec_wallet_log_item)
    TextView mTVDec;
    private WalletLogBean mWalletLogBean;

    public WalletLogItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_wallet_log;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mWalletLogBean = (WalletLogBean) data;
        if(mWalletLogBean.getAmount()>0){
            mTVAmount.setText("+"+VMallUtils.convertPriceString(mWalletLogBean.getAmount()));
        }else {
            mTVAmount.setText(VMallUtils.convertPriceString(mWalletLogBean.getAmount()));
        }
        mTVDate.setText(TimeUtils.formatDateFromUTC(mWalletLogBean.getCreateTime()));
        mTVDec.setText(mWalletLogBean.getDesc());
    }
}
