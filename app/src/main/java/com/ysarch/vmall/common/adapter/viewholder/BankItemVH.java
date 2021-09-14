package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.BankItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 17/09/2020
 **/
public class BankItemVH extends AbsViewHolder {

    @BindView(R.id.tv_bank)
    TextView mTvBank;
    private BankItemBean bean;

    public BankItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        bean = (BankItemBean) data;
        mTvBank.setText(bean.getBankName()+" "+bean.getBankNo()+" "+bean.getBankAccount());
        mTvBank.setSelected(bean.isSelect());
    }


    public static int getLayoutRes() {
        return R.layout.item_recharge_bank;
    }
}
