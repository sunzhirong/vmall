package com.ysarch.vmall.page.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.hyphenate.helpdesk.easeui.ui.BaseFragment;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.SystemUtil;
import com.yslibrary.event.OnSingleClickListener;
import com.yslibrary.event.hailer.FunctionHasParamNoResult;
import com.yslibrary.event.hailer.FunctionNoParamHasResult;
import com.yslibrary.event.hailer.FunctionsManager;
import com.yslibrary.utils.FragmentTransUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

public class AccountActivity extends AppCompatActivity {

    /**
     * 登陆
     */
    public static final int TYPE_LOGIN = 0x01;
    /**
     * 注册
     */
    public static final int TYPE_REGISTER = 0x02;
    /**
     * 找回密码
     */
    public static final int TYPE_FORGET_PW = 0x03;
    /**
     * 重置密码
     */
    public static final int TYPE_RESET_PW = 0x04;


    /**
     * facebook登录
     */
    public static final int TYPE_FACEBOOK_LOGIN = 0x05;

    @BindView(R.id.ctv_protocol_check)
    CompatTextView mCTVChecker;
    @BindView(R.id.ly_protocol)
    LinearLayout mLyProtocol;
    private AbsAccountFragment.IAccountFragmentCallback mFragmentCallback;
    private FunctionHasParamNoResult mProtocolListener;
    private FunctionNoParamHasResult mProtocolChecker;
    private int mPageType = 0;

    private Fragment mFragment ;


    /**
     * @param type
     * @return
     */
    public static Bundle getBundle(@PageType int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_ACCOUNT_LAUNCH_TYPE, type);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ViewGroup root = findViewById(R.id.fl_root_account);

        ButterKnife.bind(this);


        injectEvents();

        mCTVChecker.setSelected(true);

        StatusBarCompat.translucentStatusBar(this, true);
        StatusBarCompat.changeToLightStatusBar(this);

        Intent intent = getIntent();
        mPageType = TYPE_LOGIN;


        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPageType = bundle.getInt(BundleKey.ARG_ACCOUNT_LAUNCH_TYPE, TYPE_LOGIN);
        }


        root.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                SystemUtil.hideKeyboard(view);
            }
        });

        mPageType = TYPE_FACEBOOK_LOGIN;
        gotoFragment(mPageType);
    }

    private void injectEvents() {
        mProtocolChecker = new FunctionNoParamHasResult<Boolean>(HailerFunctionDef.CHECK_PROTOCOL) {
            @Override
            public Boolean invokeFunction() {
                return mCTVChecker.isSelected();
            }
        };

        FunctionsManager.getInstance().addFunctionNoParamHasResult(mProtocolChecker);


        mProtocolListener = new FunctionHasParamNoResult<Boolean>(HailerFunctionDef.SWITCH_PROTOCOL_VISIBILITY) {
            @Override
            public void invokeFunction(Boolean visible) {
                mLyProtocol.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        };
        FunctionsManager.getInstance().addFunctionHasParamNoResult(
                mProtocolListener);
    }


    @OnClick({R.id.tv_protocol_bee, R.id.tv_protocol_service, R.id.ctv_protocol_check,
            R.id.iv_close_account})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ctv_protocol_check:
                view.setSelected(!view.isSelected());
                break;
            case R.id.tv_protocol_bee:
//                UserAgreementActivity.toUserAgreementActivity(this);
                break;
            case R.id.tv_protocol_service:
//                UserAgreementActivity.toUserAgreementActivity(this, UserAgreementActivity.screct);
                NavHelper.startActivity(this, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/PrivacyPolicy.html"));
                break;
            case R.id.iv_close_account:
                setResult(Activity.RESULT_CANCELED);
                if (mPageType == TYPE_LOGIN && UserInfoManager.isLogin()) {
                    UserInfoManager.logout();
                }
                finish();
                break;
            default:
                break;
        }
    }

    public void gotoFragment(int page) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        String tag = null;
        switch (page) {
            case TYPE_LOGIN:
                tag = LoginFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = LoginFragment.newInstance();
                }
                break;
            case TYPE_REGISTER:
                tag = InitPWFragment.TAG + InitPWFragment.TYPE_REGISTER;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = InitPWFragment.newInstance(InitPWFragment.TYPE_REGISTER);
                }
                break;
            case TYPE_FORGET_PW:
                tag = InitPWFragment.TAG + InitPWFragment.TYPE_FORGET_PASSWORD;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = InitPWFragment.newInstance(InitPWFragment.TYPE_FORGET_PASSWORD);
                }
                break;
//            case TYPE_RESET_PW:
//                if (!UserInfoManager.isLogin()) {
//                    finish();
//                } else {
//                    tag = ResetPasswordFragment.TAG;
//                    fragment = fragmentManager.findFragmentByTag(tag);
//                    if (fragment == null) {
//                        fragment = ResetPasswordFragment.newInstance();
//                    }
//                }
//
//                break;
            case TYPE_FACEBOOK_LOGIN:
                tag = FacebookLoginFragment.TAG;
                fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = FacebookLoginFragment.newInstance();
                }
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentTransUtil.transFragment(fragmentManager, R.id.fl_account_container,
                    fragment, tag, true);
        }
        mFragment = fragment;
    }

    public AbsAccountFragment.IAccountFragmentCallback getFragmentCallback() {
        if (mFragmentCallback == null) {
            mFragmentCallback = pageType -> {
                gotoFragment(pageType);
            };
        }
        return mFragmentCallback;
    }


    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() <= 2) {
            if (fragments.size() == 2) {

                boolean hasSupportFragment = false;
                for (int i = 0; i < fragments.size(); i++) {
                    //glide会自动添加一个fragment
                    if (fragments.get(i) instanceof SupportRequestManagerFragment) {
                        if (mPageType == TYPE_LOGIN && UserInfoManager.isLogin()) {
                            UserInfoManager.logout();
                        }
                        hasSupportFragment = false;
                        break;
                    }
                }
                if(hasSupportFragment){
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                } else {
                    super.onBackPressed();
                }
            } else {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FunctionsManager.getInstance().removeFunction(HailerFunctionDef.CHECK_PROTOCOL);
    }

    @IntDef({TYPE_LOGIN, TYPE_REGISTER, TYPE_FORGET_PW, TYPE_RESET_PW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageType {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragment != null) {
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
