package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.RechargeBankAdapter;
import com.ysarch.vmall.domain.bean.BankItemBean;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeBankSelectDialog extends Dialog {

    @BindView(R.id.rv_bank)
    RecyclerView mRvBank;
    private List<BankItemBean> bankItemBeans;
    private BankItemBean selectBean;

    private Callback mCallback;
    private RechargeBankAdapter mRechargeBankAdapter;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setSelectBean(BankItemBean selectBean) {
        this.selectBean = selectBean;
    }

    public void setBankItemBeans(List<BankItemBean> bankItemBeans) {
        this.bankItemBeans = bankItemBeans;
        int selectPos = -1;
        for(int i = 0;i<bankItemBeans.size();i++){
            if(bankItemBeans.get(i).equals(selectBean)){
                selectPos = i;
                break;
            }
        }
        mRechargeBankAdapter.refreshData(bankItemBeans,selectPos);
    }

    private RechargeBankSelectDialog(Context context, int style) {
        super(context, style);
        init();
    }


    public void init() {
        View view = View.inflate(getContext(), R.layout.dialog_recharge_bank_select, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(true);


        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);


        if(mRechargeBankAdapter==null) {
            mRechargeBankAdapter = new RechargeBankAdapter(getContext());
            mRvBank.setLayoutManager(new LinearLayoutManager(getContext()));
            mRvBank.setAdapter(mRechargeBankAdapter);
            mRechargeBankAdapter.setOnItemClickListener(new ItemDataWrapper.OnItemClickListener() {
                @Override
                public void onItemClick(int position, Object data) {
                    selectBean = ((BankItemBean) data);
                    mRechargeBankAdapter.refreshData(bankItemBeans,position);
                }
            });
        }
    }


    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if (mCallback != null&&selectBean!=null) {
                    mCallback.onSelect(selectBean);
                }
                dismiss();
                break;
        }
    }


    public interface Callback {
        void onSelect(BankItemBean bean);
    }


    public static class Builder {
        private Context mContext;
        private int mStyle = R.style.ScrollDialog;
        private Callback mCallback;
        private List<BankItemBean> bankItemBeans;
        private BankItemBean selectBean;




        public Builder setCallback(Callback callback) {
            mCallback = callback;
            return this;
        }


        public Builder setBankItemBeans(List<BankItemBean> bankItemBeans) {
            this.bankItemBeans = bankItemBeans;
            return this;
        }

        public Builder setSelectBean(BankItemBean selectBean) {
            this.selectBean = selectBean;
            return this;
        }



        public Builder(Context context) {
            mContext = context;
        }


        public RechargeBankSelectDialog build() {
            RechargeBankSelectDialog dialog = new RechargeBankSelectDialog(mContext, mStyle);
            dialog.setCallback(mCallback);
            dialog.setSelectBean(selectBean);
            dialog.setBankItemBeans(bankItemBeans);
            return dialog;
        }
    }
}
