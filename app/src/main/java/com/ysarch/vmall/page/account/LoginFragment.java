package com.ysarch.vmall.page.account;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ysarch.uibase.common.WinSoftKeyboardManager;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.domain.bean.UserInfoBean;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.account.presenter.LoginPresenter;
import com.ysarch.vmall.utils.StringUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.event.hailer.FunctionsManager;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 登陆页面
 */
public class LoginFragment extends AbsAccountFragment<LoginPresenter> {
    public static String TAG = "LoginFragment";
    @BindView(R.id.et_account_login)
    EditText mETAccount;
    @BindView(R.id.et_password_login)
    EditText mETPassword;
    @BindView(R.id.iv_password_eye_login)
    ImageView mIVPWEye;
    @BindView(R.id.tv_confirm_login)
    TextView mTVLogin;
    @BindView(R.id.btn_register_login)
    Button mBtnFastRegister;


    //    Button mBtnLoginQuick;
//    @BindView(R.id.tv_phone_quick)
//    TextView mTvPhoneQuick;
//    @BindView(R.id.tv_login_quick)
//    TextView mTvLoginQuick;
//    @BindView(R.id.cl_login_layout)
//    ConstraintLayout mLoginLayout;
//    @BindView(R.id.cl_quick_login_layout)
//    ConstraintLayout mQuickLoginLayout;
//
    private int mStatus = 0;
    private String mDeviceId, mSafeCode, phoneStr;

    private UserInfoBean mStashUserInfo;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void bindEvent() {
        super.bindEvent();
        observeEditTextViews();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        new WinSoftKeyboardManager((ViewGroup) mRootView).registEditTexts();
//
        mDeviceId = AppContext.getsInstance().getDeviceId();
        phoneStr = CacheHelper.getString(CacheKeys.KEY_APP_ACCOUNT);
        String userInfoStr = CacheHelper.getString(CacheKeys.KEY_APP_USER, null);
        if (!TextUtils.isEmpty(userInfoStr)) {
            try {
                mStashUserInfo = new Gson().fromJson(userInfoStr, UserInfoBean.class);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        FunctionsManager.getInstance().invokeFunction(HailerFunctionDef.SWITCH_PROTOCOL_VISIBILITY, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_v1;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void observeEditTextViews() {
        Observable<CharSequence> obPhone = RxTextView.textChanges(mETAccount);
        Observable<CharSequence> obPassword = RxTextView.textChanges(mETPassword);
        Observable.combineLatest(obPhone, obPassword, (charSequence, charSequence2) -> {
            String phone = charSequence.toString();
            String password = charSequence2.toString();
            return !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password);
        }).compose(bindToLifecycle())
                .subscribe(aBoolean -> mTVLogin.setEnabled(aBoolean));
    }

    //
    @OnClick({R.id.btn_register_login, R.id.tv_forgot_psw_login, R.id.tv_confirm_login,
            R.id.iv_password_eye_login})
    void onEntranceClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_login:
                ((AccountActivity) getActivity()).gotoFragment(AccountActivity.TYPE_REGISTER);
                break;
            case R.id.tv_forgot_psw_login:
                ((AccountActivity) getActivity()).gotoFragment(AccountActivity.TYPE_FORGET_PW);
                break;
            case R.id.tv_confirm_login:
                Boolean protocol = FunctionsManager.getInstance().invokeFunction(HailerFunctionDef.CHECK_PROTOCOL, Boolean.class);
                if (protocol != null && protocol.booleanValue()) {
                    String phone2 = mETAccount.getText().toString().trim();
                    if (StringUtil.isTrimEmpty(phone2)) {
                        showTs(getString(R.string.tip_input_phone));
                        return;
                    }

                    if(!VMallUtils.checkPhoneLegal(phone2)){
                        showTs(getString(R.string.tip_input_phone_correctly));
                        return;
                    }

                    String pwd = mETPassword.getText().toString().trim();
                    if(!VMallUtils.checkPwdLegal(pwd)){
                        showTs(getString(R.string.tip_input_legal_password));
                        return;
                    }

                    getPresenter().doLoginPassWord(phone2, pwd);
//                    getPresenter().facebookLogin("118670537263282", "EAAOSNEqOaIkBAFSSt1JvlVpPSF0mURAXPGKhvEpSq2gKrwnCLE7eZApxUNRYbi9F6vpC2mOJryeeg3jpCQNqKYhqZCIFKgBDShvFdBAHN9pvDCsLj8QNO9VZCZCiYPwYzBhnxdPSKHSZBVuxVJMykslT6GxEiUpPshMWJqJF7J7h5Omyj8sKaKhOvRZBrqSeKRaQxneAcecoRZBqXhI7udt4QZCRgXe6KN9UOUr7gESq4XcJNS0RmSei");
                } else {
                    showTs(getString(R.string.text_please_check_agreement));
                }
                break;
            case R.id.iv_password_eye_login:
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

            default:
                break;
        }
    }

    @Override
    public LoginPresenter newPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void clearData() {

    }

    public void onLoginSuccess() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

    }
}
