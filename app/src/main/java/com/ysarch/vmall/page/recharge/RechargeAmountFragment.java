package com.ysarch.vmall.page.recharge;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.PayChannelAdapter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.PayChannelBean;
import com.ysarch.vmall.page.wallet.RechargeActivity;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.NavHelper;
import com.yslibrary.utils.ToastUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class RechargeAmountFragment extends BaseFragment<RechargeAmountPresenter> {
    @BindView(R.id.tv_recharge_amount_prefix)
    TextView mTvRechargeAmountPrefix;
    @BindView(R.id.tv_recharge_amount)
    TextView mTvRechargeAmount;
    @BindView(R.id.tv_submit_recharge)
    TextView mTvSubmitRecharge;
    @BindView(R.id.tv_20)
    TextView mTv20;
    @BindView(R.id.tv_30)
    TextView mTv30;
    @BindView(R.id.tv_50)
    TextView mTv50;
    @BindView(R.id.tv_100)
    TextView mTv100;
    @BindView(R.id.tv_200)
    TextView mTv200;
    @BindView(R.id.et_self)
    EditText mEtSelf;
    @BindView(R.id.rv_channel)
    RecyclerView mRvChannel;
    private TextView selectTv;
    private PayChannelAdapter mPayChannelAdapter;
    private PayChannelBean mPayChannelBean;
    private List<PayChannelBean> mPayChannelBeans;

    private int selectBankId = -1;

    @Override
    public void initData(Bundle savedInstanceState) {

        getPresenter().requestBanks();
        getPresenter().payChannelList();

        mRvChannel.setLayoutManager(new LinearLayoutManager(context));
        mPayChannelAdapter = new PayChannelAdapter(context);
        mPayChannelAdapter.setOnItemClickListener((position, data) ->{
            mPayChannelBean = (PayChannelBean) data;
            selectBankId = mPayChannelBean.getRelateBank();
            mPayChannelAdapter.refreshData(mPayChannelBeans,position);
        });
        mRvChannel.setAdapter(mPayChannelAdapter);

        mEtSelf.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //第一位输入小数点变成0.
                if (mEtSelf.getText().toString().trim() != null && !mEtSelf.getText().toString().trim().equals("")) {
                    if (mEtSelf.getText().toString().trim().substring(0, 1).equals(".")) {
                        mEtSelf.setText("0" + mEtSelf.getText().toString().trim());
                        mEtSelf.setSelection(mEtSelf.getText().toString().length());
                    }
                }
                // 判断小数点后只能输入两位
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mEtSelf.setText(s);
                        mEtSelf.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mEtSelf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(mEtSelf.getText().toString())) {
                    mTvRechargeAmount.setText("$" + mEtSelf.getText().toString());
                } else {
                    mTvRechargeAmount.setText("");
                }
            }
        });

        mEtSelf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectAmount(mEtSelf);
                }
            }
        });

        selectAmount(mTv30);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recharge_amount;
    }

    @Override
    public RechargeAmountPresenter newPresenter() {
        return new RechargeAmountPresenter();
    }

    @OnClick({ R.id.tv_submit_recharge,
            R.id.tv_20, R.id.tv_30, R.id.tv_50, R.id.tv_100, R.id.tv_200, R.id.et_self})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.tv_submit_recharge:
                String amount = mTvRechargeAmount.getText().toString();
                if(selectBankId == -1){
                    ToastUtils.showShortToast(context, R.string.please_select_bank);
                    return;
                }
                amount = amount.replace("$", "");
                if (!TextUtils.isEmpty(amount)&&Double.parseDouble(amount)>0) {
//                    amount = amount.substring(1);
//                    Log.e("niko bank", JSON.toJSONString(AppContext.getsInstance().getBankItemBeans()));
                    NavHelper.startActivity(getActivity(), RechargeActivity.class, RechargeActivity.getBundle(selectBankId,Double.parseDouble(amount)));
                } else {
                    ToastUtils.showShortToast(context, R.string.please_input_recharge_amount);
                }
                break;
            case R.id.tv_20:
                selectAmount(mTv20);
                break;
            case R.id.tv_30:
                selectAmount(mTv30);
                break;
            case R.id.tv_50:
                selectAmount(mTv50);
                break;
            case R.id.tv_100:
                selectAmount(mTv100);
                break;
            case R.id.tv_200:
                selectAmount(mTv200);
                break;
            case R.id.et_self:
                selectAmount(mEtSelf);
                break;
        }
    }

    private void selectAmount(TextView select) {
        if (selectTv != null) {
            selectTv.setSelected(false);
        }
        select.setSelected(true);
        selectTv = select;
        mTvRechargeAmount.setText(select.getText());
    }


    public void onBankData(List<BankItemBean> bankItemBeans) {

    }

    public void onPayChannelData(List<PayChannelBean> payChannelBeans) {
        if(payChannelBeans!=null&&payChannelBeans.size()>0) {
            Log.e("niko bank", JSON.toJSONString(AppContext.getsInstance().getBankItemBeans()));
            mPayChannelBeans = payChannelBeans;
            mPayChannelAdapter.refreshData(payChannelBeans, 0);
            selectBankId = payChannelBeans.get(0).getRelateBank();
        }
    }
}
