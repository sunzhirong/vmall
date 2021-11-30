package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;
import com.ysarch.uibase.dialog.SimpleDialogWithTwoBtn;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.component.KeyboardView;
import com.ysarch.vmall.component.PwdEditText;
import com.ysarch.vmall.page.recharge.RechargeAmountActivity;
import com.ysarch.vmall.page.setting.PasswordSettingActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.VMallUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayDialog extends DialogFragment {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.ll_pay_confirm)
    LinearLayout mLlPayConfirm;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.pwd_et)
    PwdEditText mPwdEt;
    @BindView(R.id.keyboardview)
    KeyboardView mKeyboardview;
    @BindView(R.id.ll_pay_password)
    LinearLayout mLlPayPassword;
    @BindView(R.id.ll_pay)
    LinearLayout mLlPay;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.iv_wallet)
    ImageView mIvWallet;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_price)
    TextView mTvPrice;

    private Animation slide_left_to_left;
    private Animation slide_right_to_left;
    private Animation slide_left_to_right;
    private Animation slide_left_to_left_in;

    private PayCallback mPayCallback;
    private double mPrice;

    private boolean isPwdError = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_pay);
        ButterKnife.bind(this, dialog);

        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        window.setBackgroundDrawable(new ColorDrawable(0xff000000));//让dialog充满屏幕！！！！
        window.getDecorView().setPadding(0, 0, 0, 0);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(lp);

        initView(dialog);

        return dialog;
    }

    private void initView(final Dialog dialog) {
        mKeyboardview.setEditText(mPwdEt);
        mPwdEt.setOnTextChangeListener(pwd -> {
            if (pwd.length() == 6) {
                if (mPayCallback != null) {
                    dismissAllowingStateLoss();
                    mPayCallback.onConfirm(pwd);
                }
            }
        });
        dialog.setOnKeyListener((anInterface, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                checkGiveUpPay();
//                if(mLlPayConfirm.getVisibility()==View.VISIBLE) {
//                    checkGiveUpPay();
//                }else {
//                    clickBack();
//                }
                return true;
            }
            return false;
        });

        slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left);
        slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_to_left);
        slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_right);
        slide_left_to_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left_in);

        mIvWallet.setSelected(true);

        mTvBalance.setText(VMallUtils.convertPriceString(UserInfoManager.getUser().getWallet()));
        mTvPrice.setText(VMallUtils.convertTo2String(mPrice));

    }

    @Override
    public void onResume() {
        super.onResume();
        if(isPwdError){
            mLlPayConfirm.setVisibility(View.GONE);
            mLlPayPassword.setVisibility(View.VISIBLE);
        }
        TCAgent.onPageEnd(getContext(),"确认付款弹窗");

    }

    @Override
    public void onStop() {
        super.onStop();
        TCAgent.onPageEnd(getContext(),"确认付款弹窗");
    }

    public void setPwdError(boolean pwdError) {
        isPwdError = pwdError;
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        isPwdError = false;
    }

    @OnClick({R.id.iv_close, R.id.tv_confirm, R.id.iv_back, R.id.tv_forget_pay_pwd})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.iv_close:
                checkGiveUpPay();
                break;
            case R.id.tv_confirm:
                if (UserInfoManager.judeIsLogin(getActivity())) {
                    if (UserInfoManager.getUser().getWallet() > mPrice) {
                        if (UserInfoManager.getUser().isHasPayPassword()) {
                            mLlPayConfirm.startAnimation(slide_left_to_left);
                            mLlPayConfirm.setVisibility(View.GONE);
                            mLlPayPassword.startAnimation(slide_right_to_left);
                            mLlPayPassword.setVisibility(View.VISIBLE);
                        } else {
                            if (mPayCallback != null) {
                                dismissAllowingStateLoss();
                                mPayCallback.onConfirm("");
                            }

                        }
                    } else {
                        showRechargeDialog();
                    }
                }
                break;
            case R.id.iv_back:
                clickBack();
                break;
            case R.id.tv_forget_pay_pwd:
//                if (mPayCallback != null) {
//                    mPayCallback.onForgetPayPassword();
//                }
                NavHelper.startActivity(getActivity(), PasswordSettingActivity.class);
                break;
        }
    }

    private void clickBack() {
        mLlPayPassword.setVisibility(View.GONE);
        mLlPayPassword.startAnimation(slide_left_to_right);
        mLlPayConfirm.startAnimation(slide_left_to_left_in);
        mLlPayConfirm.setVisibility(View.VISIBLE);
    }

    private void checkGiveUpPay() {
        new SimpleDialogWithTwoBtn.Builder(getContext())
                .setRightLabel(getString(R.string.label_confirm))
                .setLeftLabel(getString(R.string.label_cancel))
                .setWarning(getString(R.string.label_give_up_pay))
                .setAutoDismissWhileClick(true)
                .setCancelable(true)
                .setPageName("是否放弃本次交易弹窗")
                .setOnSubmitListener(new SimpleDialogWithTwoBtn.OnSubmitListener() {
                    @Override
                    public void onLeftBtnClick() {

                    }

                    @Override
                    public void onRightBtnClick() {
                        dismissAllowingStateLoss();
                        if (mPayCallback != null) {
                            mPayCallback.onCancel();
                        }
                    }
                }).build().show();
    }

    private void showRechargeDialog() {
        new SimpleDialogWithTwoBtn.Builder(getContext())
                .setRightLabel(getString(R.string.label_confirm))
                .setLeftLabel(getString(R.string.label_cancel))
                .setWarning(getString(R.string.tip_balance_not_enought))
                .setAutoDismissWhileClick(true)
                .setCancelable(true)
                .setPageName("钱包余额不足弹窗")
                .setOnSubmitListener(new SimpleDialogWithTwoBtn.OnSubmitListener() {
                    @Override
                    public void onLeftBtnClick() {

                    }

                    @Override
                    public void onRightBtnClick() {
//                        NavHelper.startActivity(getActivity(), RechargeActivity.class);
                        NavHelper.startActivity(getActivity(), RechargeAmountActivity.class);
                    }
                }).build().show();
    }


    public void setPrice(double price) {
        mPrice = price;
    }

    public void setPayCallback(PayCallback payCallback) {
        mPayCallback = payCallback;
    }


    public interface PayCallback {
        void onConfirm(String password);

        void onCancel();

//        void onForgetPayPassword();
    }
}
