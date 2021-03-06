package com.ysarch.vmall.page.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.page.address.AddressEditActivity;
import com.ysarch.vmall.page.address.AddressListFragment;
import com.ysarch.vmall.utils.NavHelper;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FaceBookLoginActivity  extends AppCompatActivity {
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facebook);

        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onCancel() {
                CookieSyncManager.createInstance(FaceBookLoginActivity.this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                CookieSyncManager.getInstance().sync();
            }

            @Override
            public void onError(FacebookException arg0) {
                Log.d("onError", arg0.toString());
            }

            @Override
            public void onSuccess(LoginResult arg0) {
                Log.d("onSuccess", "???????????????");

            }
        });




    }

    @OnClick(R.id.tv_facebook_login)
    void onClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(FaceBookLoginActivity.this, Arrays.asList("public_profile", "email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
