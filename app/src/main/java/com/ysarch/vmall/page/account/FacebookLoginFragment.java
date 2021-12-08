package com.ysarch.vmall.page.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.account.presenter.FaceBookLoginPresenter;
import com.ysarch.vmall.page.account.presenter.LoginPresenter;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.event.hailer.FunctionHasParamNoResult;
import com.yslibrary.event.hailer.FunctionNoParamHasResult;
import com.yslibrary.event.hailer.FunctionsManager;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class FacebookLoginFragment extends AbsAccountFragment<FaceBookLoginPresenter>{
    public static String TAG = "FacebookLoginFragment";

    @BindView(R.id.login_button)
    LoginButton mLoginButton;
    @BindView(R.id.ctv_protocol_check)
    CompatTextView mCTVChecker;
    @BindView(R.id.ly_protocol)
    LinearLayout mLyProtocol;

//    private FunctionHasParamNoResult mProtocolListener;
//    private FunctionNoParamHasResult mProtocolChecker;
    private CallbackManager callbackManager;

    private long visitTime;

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
//                Log.d("onError", arg0.toString());
                getPresenter().loginLog(visitTime,arg0.getMessage(),false);
            }

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("onSuccess", "登录成功！" +JSON.toJSONString(loginResult));
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

//                        String id = object.optString("id");
                        String name = object.optString("name");
//                        String gender = object.optString("gender");
                        String email = object.optString("email");
//                        String locale = object.optString("locale");
                        //获取用户头像
                        JSONObject object_pic = object.optJSONObject("picture");
                        JSONObject object_data = object_pic.optJSONObject("data");
                        String headUrl = object_data.optString("url");
                        getPresenter().facebookLogin(name, loginResult.getAccessToken().getToken(),loginResult.getAccessToken().getUserId(),headUrl,email,visitTime);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,link,gender,birthday,picture,locale,updated_time,timezone,age_range,first_name,last_name");

                request.setParameters(parameters);
                request.executeAsync();


            }
        });

        mCTVChecker.setSelected(true);



    }
    @OnClick(R.id.tv_facebook_login)
    void onClick(View view) {
        if(!mCTVChecker.isSelected()) {
            showTs(getString(R.string.text_please_check_agreement));
        }else {
            visitTime = System.currentTimeMillis();
            LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "email"));
        }
    }
    @OnClick(R.id.ctv_protocol_check)
    void onProtocolClick(View view) {
        view.setSelected(!view.isSelected());
    }

    @OnClick(R.id.tv_protocol_service)
    void onProtocolServiceClick(View view) {
        NavHelper.startActivity(this, CommonWebActivity .class, CommonWebActivity.getBundle("http://47.112.237.160:8989/PrivacyPolicy.html"));
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

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        FunctionsManager.getInstance().removeFunction(HailerFunctionDef.CHECK_PROTOCOL);
//    }


    @Override
    protected String getPageName() {
        return "facebook登录页";
    }
}
