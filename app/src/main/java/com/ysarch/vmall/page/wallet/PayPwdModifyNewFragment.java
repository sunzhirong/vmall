package com.ysarch.vmall.page.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.component.KeyboardView;
import com.ysarch.vmall.component.PwdEditText;
import com.ysarch.vmall.page.wallet.presenter.PayPwdModifyNewPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 2020/10/15
 **/
public class PayPwdModifyNewFragment extends BaseFragment<PayPwdModifyNewPresenter> {

    @BindView(R.id.tv_confirm_tips)
    TextView mTvConfirmTips;
    @BindView(R.id.tv_pwd_tips)
    TextView mTvPwdTips;
    @BindView(R.id.pwd_et)
    PwdEditText mPwdEt;
    @BindView(R.id.keyboardview)
    KeyboardView mKeyboardview;

    private boolean isConfirm;
    private boolean isVerify;
    private String password;
    private String oldPassword;

    @Override
    public void initData(Bundle savedInstanceState) {
        mKeyboardview.setEditText(mPwdEt);
        if(UserInfoManager.getUser().isHasPayPassword()){
            mTvPwdTips.setText(R.string.label_input_password);
        }
        mPwdEt.setOnTextChangeListener(new PwdEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                if(UserInfoManager.getUser().isHasPayPassword()&&!isVerify){
                    verifyPwd(pwd);
                }else {
                    modifyPwd(pwd);
                }
            }
        });
    }

    private void modifyPwd(String pwd) {
        if (pwd.length() == 6 && !isConfirm) {//第一次输入完成
            isConfirm = true;
            password = pwd;
            mPwdEt.clearText();
            mTvConfirmTips.setVisibility(View.VISIBLE);
        } else if (pwd.length() == 6) {
            if (password.equals(pwd)) {
                if(UserInfoManager.getUser().isHasPayPassword()){
                    getPresenter().updatePayPwd(password,oldPassword);
                }else {
                    getPresenter().updatePayPwd(password,"");
//                    getPresenter().modifyPayPassword(UserInfoManager.getUser().getAccount(),"",password);
                }
            } else {
                showTs(getString(R.string.label_modify_pwd_error));
                isConfirm = false;
                password = "";
                mPwdEt.clearText();
                mTvConfirmTips.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void verifyPwd(String pwd) {
        if(pwd.length() == 6){
            getPresenter().verifyPayPassword(pwd);
            oldPassword = pwd;
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_pay_pwd_modify_new;
    }

    @Override
    public PayPwdModifyNewPresenter newPresenter() {
        return new PayPwdModifyNewPresenter();
    }


    public void onPWDModifySucc() {
        showTs(getString(R.string.label_setting_succ));
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        getActivity().finish();
    }

    public void onPWDVerifySucc() {
        password = "";
        mPwdEt.clearText();
        mTvPwdTips.setText(R.string.label_pwd_modify);
        isVerify = true;
    }

    public void onPWDVerifyFailed() {
        password = "";
        oldPassword = "";
        mPwdEt.clearText();
    }

    public void onPWDUpdateSucc() {
        showTs(getString(R.string.label_setting_succ));
        getActivity().finish();
    }

    public void onPWDUpdateFailed() {
        password = "";
        mPwdEt.clearText();
        mTvPwdTips.setText(R.string.label_pwd_modify);
        isVerify = true;
        isConfirm = false;
    }

    public void onPWDModifyFailed() {
        onPWDUpdateFailed();
    }
}
