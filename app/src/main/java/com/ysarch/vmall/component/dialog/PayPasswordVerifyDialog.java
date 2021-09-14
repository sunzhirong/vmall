package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jungly.gridpasswordview.GridPasswordView;
import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 2020-06-15
 **/
public class PayPasswordVerifyDialog extends Dialog {

    @BindView(R.id.gpv_code_pay_pwd_verify)
    GridPasswordView mGPVCode;
    @BindView(R.id.tv_confirm_pay_pwd_verify)
    TextView mTVConfirm;
    private boolean bCancelable;
    private PayCallback mPayCallback;

    public PayPasswordVerifyDialog(@NonNull Context context) {
        this(context, R.style.CenterDialog);
    }

    public PayPasswordVerifyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        bCancelable = flag;
    }

    private String mConfirmLabel;


    public PayPasswordVerifyDialog setConfirmLabel(String confirmLabel) {
        mConfirmLabel = confirmLabel;
        if(mTVConfirm != null){
            mTVConfirm.setText(mConfirmLabel);
        }
        return this;
    }

    public void setPayCallback(PayCallback payCallback) {
        mPayCallback = payCallback;
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_pay_password_verify, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        view.setOnClickListener(v -> {
            if (bCancelable) {
                dismiss();
            }
        });

        mGPVCode.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                mTVConfirm.setEnabled(psw.length() == 6);
            }

            @Override
            public void onInputFinish(String psw) {

            }
        });


    }


    @OnClick({R.id.tv_forget_pay_pwd_verify, R.id.v_close_pay_pwd_verify, R.id.ly_pay_pwd_verify,
            R.id.tv_confirm_pay_pwd_verify})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pay_pwd_verify:
                if (mPayCallback != null) {
                    mPayCallback.onForgetPayPassword();
                }
                break;
            case R.id.v_close_pay_pwd_verify:
                dismiss();
                break;
            case R.id.tv_confirm_pay_pwd_verify:
                dismiss();
                if (mPayCallback != null && mGPVCode.getPassWord().length() == 6) {
                    mPayCallback.onConfirm(mGPVCode.getPassWord());
                }
                break;
            default:
                break;
        }
    }


    public interface PayCallback {
        void onConfirm(String password);

        void onForgetPayPassword();
    }

    public static class Builder {

        private Context mContext;
        private PayCallback mPayCallback;
        private boolean bCancelable;
        private String mConfirmLabel;


        public Builder setConfirmLabel(String confirmLabel) {
            mConfirmLabel = confirmLabel;
            return this;
        }

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setCancelable(boolean cancelable) {
            bCancelable = cancelable;
            return this;
        }

        public Builder setPayCallback(PayCallback payCallback) {
            mPayCallback = payCallback;
            return this;
        }


        public PayPasswordVerifyDialog build() {
            PayPasswordVerifyDialog dialog = new PayPasswordVerifyDialog(mContext);
            dialog.setCancelable(bCancelable);
            dialog.setPayCallback(mPayCallback);
            dialog.setConfirmLabel(mConfirmLabel);
            return dialog;
        }
    }
}
