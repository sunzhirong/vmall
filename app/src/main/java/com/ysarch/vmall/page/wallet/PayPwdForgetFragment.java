package com.ysarch.vmall.page.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.common.WinSoftKeyboardManager;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.page.wallet.presenter.PayPwdModifyPresenter;
import com.ysarch.vmall.utils.StringUtil;
import com.yslibrary.utils.CountDownUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by fysong on 2020/10/15
 **/
public class PayPwdForgetFragment extends BaseFragment<PayPwdModifyPresenter> {


    @BindView(R.id.tv_account_pay_password_modify)
    TextView mTVPhone;
    @BindView(R.id.et_auth_code_pay_password_modify)
    EditText mETAuthCode;
    @BindView(R.id.et_password_pay_password_modify)
    EditText mETPassword;
    @BindView(R.id.et_password2_pay_password_modify)
    EditText mETPassword2;
    @BindView(R.id.tv_auth_code_pay_password_modify)
    TextView mTVAuthCode;
    @BindView(R.id.tv_confirm_pay_password_modify)
    TextView mTVSubmit;
    private CountDownUtils mCountDownUtils;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserInfoManager.isLogin()){
            mTVPhone.setText(UserInfoManager.getUser().getAccount());
        } else {
            getActivity().finish();
        }
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        observeEditTextViews();
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        WinSoftKeyboardManager winSoftKeyboardManager = new WinSoftKeyboardManager((ViewGroup) mRootView);
        winSoftKeyboardManager.registEditTexts();


    }

    private void observeEditTextViews() {
        Observable<CharSequence> obPhone = RxTextView.textChanges(mTVPhone);
        Observable<CharSequence> obAuthCode = RxTextView.textChanges(mETAuthCode);
        Observable<CharSequence> obPassword = RxTextView.textChanges(mETPassword);
        Observable<CharSequence> obPassword2 = RxTextView.textChanges(mETPassword2);

        Observable.combineLatest(obPhone, obAuthCode, obPassword, obPassword2,
                (charSequence, charSequence2, charSequence3, charSequence4) -> {
                    String phone = charSequence.toString().trim();
                    String authCode = charSequence2.toString().trim();
                    String password = charSequence3.toString().trim();
                    String password2 = charSequence4.toString().trim();
                    return !TextUtils.isEmpty(phone)
                            && !TextUtils.isEmpty(authCode)
                            && !TextUtils.isEmpty(password) && password.length() == 6
                            && !TextUtils.isEmpty(password2) && password2.length() == 6;
                }).compose(bindToLifecycle())
                .subscribe(aBoolean -> mTVSubmit.setEnabled(aBoolean));
    }

    @OnClick({R.id.tv_confirm_pay_password_modify, R.id.tv_auth_code_pay_password_modify,
            R.id.iv_password_eye_pay_password_modify, R.id.iv_password_eye2_pay_password_modify})
    void onEntranceClick(View view) {
        switch (view.getId()) {
            case R.id.tv_auth_code_pay_password_modify:
                if (StringUtil.isTrimEmpty(mTVPhone.getText().toString())) {
                    showTs(getString(R.string.tip_input_phone));
                    return;
                }
                getPresenter().getSmsCode(mTVPhone.getText().toString().trim());
                break;
            case R.id.tv_confirm_pay_password_modify:
                String password = mETPassword.getText().toString().trim();
                String password2 = mETPassword2.getText().toString().trim();
                if (!password.equals(password2)) {
                    showTs(getString(R.string.tip_password_un_same));
                    return;
                }

                getPresenter().modifyPayPassword(mTVPhone.getText().toString().trim(),
                        mETAuthCode.getText().toString().trim(),
                        password2);
                break;
            case R.id.iv_password_eye_pay_password_modify:
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    mETPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mETPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mETPassword.postInvalidate();
                CharSequence text = mETPassword.getText();
                if (!TextUtils.isEmpty(text)) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                break;

            case R.id.iv_password_eye2_pay_password_modify:
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    mETPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mETPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mETPassword2.postInvalidate();
                CharSequence text2 = mETPassword2.getText();
                if (!TextUtils.isEmpty(text2)) {
                    Spannable spanText = (Spannable) text2;
                    Selection.setSelection(spanText, text2.length());
                }
                break;
            default:
                break;
        }
    }

    public void startCountDown() {
        mTVAuthCode.setEnabled(false);
        if (mCountDownUtils == null) {
            mCountDownUtils = new CountDownUtils(getContext(), 60000, 1000, mTVAuthCode,
                    getString(R.string.label_auth_code));
        }
        mCountDownUtils.start();
    }

    /**
     * 停止倒数技术
     */
    public void stopCountDown() {
//        if (mCountDownUtils == null) {
//            mCountDownUtils = new CountDownUtils(getContext(), 60000, 1000,
//                    mTVAuthCode, getString(R.string.label_auth_code));
//        }

        if (mCountDownUtils != null)
            mCountDownUtils.doFinish(getString(R.string.label_auth_code));
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_pay_pwd_modify;
    }

    @Override
    public PayPwdModifyPresenter newPresenter() {
        return new PayPwdModifyPresenter();
    }

    public void onAuthCodeSucc() {
        startCountDown();
    }

    public void onPWDModifySucc() {
        showTs(getString(R.string.label_setting_succ));
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
