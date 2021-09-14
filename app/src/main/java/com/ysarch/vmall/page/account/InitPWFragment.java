package com.ysarch.vmall.page.account;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ysarch.uibase.common.WinSoftKeyboardManager;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.account.presenter.RegisterPresenter;
import com.ysarch.vmall.utils.StringUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.event.hailer.FunctionsManager;
import com.yslibrary.utils.CountDownUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.ysarch.vmall.page.account.AccountActivity.TYPE_LOGIN;

/**
 * 注册、重置密码
 */
public class InitPWFragment extends AbsAccountFragment<RegisterPresenter> {
    /**
     * 账号注册
     */
    public static final int TYPE_REGISTER = 1;
    /**
     * 忘记密码
     */
    public static final int TYPE_FORGET_PASSWORD = 2;

    public static String TAG = "InitPWFragment";
    @BindView(R.id.et_account_register)
    EditText mETPhone;
    @BindView(R.id.et_auth_code_register)
    EditText mETAuthCode;
    @BindView(R.id.et_password_register)
    EditText mETPassword;
    @BindView(R.id.tv_auth_code_register)
    TextView mTVAuthCode;
    @BindView(R.id.iv_password_eye_register)
    ImageView mIVPWEye;
    @BindView(R.id.tv_confirm_register)
    TextView mTVSubmit;


    @BindView(R.id.et_nickname_register)
    EditText mETNickname;

    @BindView(R.id.ly_nickname_register)
    LinearLayout mLyNickname;
    @BindView(R.id.v_divide_line_nickname_register)
    View mViewNickname;

    private int mPageType = TYPE_REGISTER;
    private CountDownUtils mCountDownUtils;

    public InitPWFragment() {
    }

    public static InitPWFragment newInstance(@TYPE_PAGE int pageType) {
        InitPWFragment fragment = new InitPWFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_REGISTER_PAGE_TYPE, pageType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPageType = bundle.getInt(BundleKey.ARG_REGISTER_PAGE_TYPE, TYPE_REGISTER);
        }
        new WinSoftKeyboardManager((ViewGroup) mRootView).registEditTexts();

        mTVSubmit.setText(mPageType == TYPE_REGISTER ? getString(R.string.label_register) : getString(R.string.label_submit));


        if (mPageType == TYPE_REGISTER) {
            mLyNickname.setVisibility(View.VISIBLE);
            mViewNickname.setVisibility(View.VISIBLE);
            mETPassword.setHint(R.string.hint_input_password);
        } else {
            mLyNickname.setVisibility(View.GONE);
            mViewNickname.setVisibility(View.GONE);
            mETPassword.setHint(R.string.hint_input_new_password);
        }


        if(UserInfoManager.isLogin()){
            mETPhone.setText(UserInfoManager.getUser().getAccount());
            mETPhone.setEnabled(false);
        }


        observeEditTextViews();
    }


    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();

    }

    private void observeEditTextViews() {
        Observable<CharSequence> obPhone = RxTextView.textChanges(mETPhone);
        Observable<CharSequence> obAuthCode = RxTextView.textChanges(mETAuthCode);
        Observable<CharSequence> obPassword = RxTextView.textChanges(mETPassword);

        if (mPageType == TYPE_REGISTER) {
            Observable<CharSequence> obNickname = RxTextView.textChanges(mETNickname);
            Observable.combineLatest(obPhone, obAuthCode, obPassword, obNickname, (charSequence, charSequence2, charSequence3, charSequence4) -> {
                String phone = charSequence.toString().trim();
                String authCode = charSequence2.toString().trim();
                String password = charSequence3.toString().trim();
                String nickname = charSequence4.toString().trim();
                return !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(authCode)
                        && !TextUtils.isEmpty(nickname);
            }).compose(bindToLifecycle())
                    .subscribe(aBoolean -> mTVSubmit.setEnabled(aBoolean));
        } else {
            Observable.combineLatest(obPhone, obAuthCode, obPassword, (charSequence, charSequence2, charSequence3) -> {
                String phone = charSequence.toString().trim();
                String authCode = charSequence2.toString().trim();
                String password = charSequence3.toString().trim();
                return !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(authCode);
            }).compose(bindToLifecycle())
                    .subscribe(aBoolean -> mTVSubmit.setEnabled(aBoolean));
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.tv_confirm_register, R.id.tv_auth_code_register,
            R.id.iv_password_eye_register})
    void onEntranceClick(View view) {
        switch (view.getId()) {
            case R.id.tv_auth_code_register:

                String phone = mETPhone.getText().toString().trim();
                if (StringUtil.isTrimEmpty(phone)) {
                    showTs(getString(R.string.tip_input_phone));
                    return;
                }

                if(!VMallUtils.checkPhoneLegal(phone)){
                    showTs(getString(R.string.tip_input_phone_correctly));
                    return;
                }

                if(phone.startsWith("0")){
                    getPresenter().getSmsCode(phone.substring(1));
                } else {
                    getPresenter().getSmsCode(phone);
                }

                break;
            case R.id.tv_confirm_register:
                Boolean protocol = FunctionsManager.getInstance().invokeFunction(HailerFunctionDef.CHECK_PROTOCOL, Boolean.class);

                if (protocol != null && protocol.booleanValue()) {

                    String phone2 = mETPhone.getText().toString().trim();
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

                    if (mPageType == TYPE_REGISTER) {

                        String nickname = mETNickname.getText().toString().trim();
                        if(TextUtils.isEmpty(nickname)){
                            showTs(getString(R.string.tip_input_nickname));
                            return;
                        }

                        getPresenter().doRegister(phone2,
                                nickname,
                                mETAuthCode.getText().toString().trim(),
                                pwd);
                    } else if (mPageType == TYPE_FORGET_PASSWORD) {
                        getPresenter().resetPassword(phone2,
                                mETAuthCode.getText().toString().trim(),
                                pwd);
                    }
                } else {
                    showTs(getString(R.string.text_please_check_agreement));
                }
                break;
            case R.id.iv_password_eye_register:
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


    /**
     * 注册成功
     */
    public void onRegisterSuccess() {
        showTs(getString(R.string.tip_pwd_set_succ));
        ((AccountActivity) getActivity()).gotoFragment(TYPE_LOGIN);
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
        if (mCountDownUtils != null)
            mCountDownUtils.doFinish(getString(R.string.label_auth_code));
    }


    @Override
    public RegisterPresenter newPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void clearData() {

    }


    public void onAuthCodeSucc() {
        startCountDown();
    }

    public void resetPasswordSucc() {
        showTs(getString(R.string.tip_pwd_set_succ));
        ((AccountActivity) getActivity()).gotoFragment(TYPE_LOGIN);
    }

    @IntDef({TYPE_REGISTER, TYPE_FORGET_PASSWORD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE_PAGE {

    }
}
