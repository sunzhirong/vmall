package com.ysarch.vmall.page.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.account.presenter.FaceBookLoginPresenter;
import com.ysarch.vmall.page.account.presenter.LoginPresenter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class FacebookLoginFragment extends AbsAccountFragment<FaceBookLoginPresenter>{
    public static String TAG = "FacebookLoginFragment";

    @BindView(R.id.login_button)
    LoginButton mLoginButton;
    private CallbackManager callbackManager;

    public static FacebookLoginFragment newInstance() {
        FacebookLoginFragment fragment = new FacebookLoginFragment();
        return fragment;
    }

    @Override
    protected void clearData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onCancel() {
//                CookieSyncManager.createInstance(MainActivity.this);
//                CookieManager cookieManager = CookieManager.getInstance();
//                cookieManager.removeAllCookie();
//                CookieSyncManager.getInstance().sync();
            }

            @Override
            public void onError(FacebookException arg0) {
                Log.d("onError", arg0.toString());
            }

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("onSuccess", "登录成功！");
//                boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
                Profile profile = Profile.getCurrentProfile();
                String name = profile.getName();

                getPresenter().facebookLogin(loginResult.getAccessToken().getUserId(), loginResult.getAccessToken().getToken(),name);
            }
        });



    }
    @OnClick(R.id.tv_facebook_login)
    void onClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "email"));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_facebook;
    }

    @Override
    public FaceBookLoginPresenter newPresenter() {
        return new FaceBookLoginPresenter();
    }


    public void onLoginSuccess() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

    }
}
