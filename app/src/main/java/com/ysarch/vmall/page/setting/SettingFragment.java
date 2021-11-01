package com.ysarch.vmall.page.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysarch.uibase.dialog.SimpleDialogWithTwoBtn;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.component.SettingItem;
import com.ysarch.vmall.page.account.AccountActivity;
import com.ysarch.vmall.page.account.presenter.LoginPresenter;
import com.ysarch.vmall.page.setting.language.LanguageSettingActivity;
import com.ysarch.vmall.page.wallet.PayPwdModifyActivity;
import com.ysarch.vmall.page.wallet.RechargeActivity;
import com.ysarch.vmall.utils.CleanDataUtils;
import com.ysarch.vmall.utils.NavHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 16/09/2020
 **/
public class SettingFragment extends Fragment {

    @BindView(R.id.tv_logout_setting)
    TextView mTVLogout;
    @BindView(R.id.iv_frag_lan_setting)
    ImageView mIVFrag;
    @BindView(R.id.iv_ava)
    ImageView mIvAva;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_enter)
    ImageView mIvEnter;
    @BindView(R.id.rl_ava)
    RelativeLayout mRlAva;
    @BindView(R.id.siv_language)
    SettingItem mSivLanguage;
    @BindView(R.id.siv_login_pwd)
    SettingItem mSivLoginPwd;
    @BindView(R.id.siv_pay_pwd)
    SettingItem mSivPayPwd;
    @BindView(R.id.siv_cache)
    SettingItem mSivCache;
    @BindView(R.id.siv_version)
    SettingItem mSivVersion;
    @BindView(R.id.ctv_pay_pwd_setting)
    CompatTextView mCtvPayPwdSetting;
    @BindView(R.id.v_pay_pwd_arrow_setting)
    ImageView mVPayPwdArrowSetting;
    @BindView(R.id.ctl_pay_pwd_setting)
    ConstraintLayout mCtlPayPwdSetting;
    @BindView(R.id.ctv_lan_setting)
    CompatTextView mCtvLanSetting;
    @BindView(R.id.v_lan_arrow_setting)
    ImageView mVLanArrowSetting;
    @BindView(R.id.ctl_lan_setting)
    ConstraintLayout mCtlLanSetting;
    @BindView(R.id.v_divide_lan_setting)
    View mVDivideLanSetting;
    @BindView(R.id.ctv_about_us_setting)
    CompatTextView mCtvAboutUsSetting;
    @BindView(R.id.v_divide_about_us_setting)
    View mVDivideAboutUsSetting;
    @BindView(R.id.iv_language)
    ImageView mIvLanguage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (UserInfoManager.isLogin()) {
            mTVLogout.setVisibility(View.VISIBLE);
        } else {
            mTVLogout.setVisibility(View.GONE);
        }

        mIVFrag.setImageResource(AppContext.getsInstance().getLanguageEntity().getIconRes());


        mSivCache.setRightText(CleanDataUtils.getTotalCacheSize(getContext()));
        mSivVersion.setRightText("V " + BuildConfig.VERSION_NAME);
        if(UserInfoManager.isLogin()) {
            mTvName.setText(UserInfoManager.getUser().getNickname());
        }
        mIvLanguage.setImageResource(AppContext.getsInstance().getLanguageEntity().getCircleRes());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserInfoManager.isLogin()) {
            BeeGlide.with(this)
                    .load(ImageLoadConfig.create(UserInfoManager.getUser().getIcon())
                            .placeHolder(R.drawable.ic_avatar_placeholder), mIvAva);
        }
    }

    @OnClick({R.id.tv_logout_setting, R.id.ctl_lan_setting, R.id.ctv_about_us_setting,
            R.id.ctl_pay_pwd_setting,R.id.rl_ava, R.id.siv_language, R.id.siv_login_pwd,
            R.id.siv_pay_pwd, R.id.siv_cache})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout_setting:
                UserInfoManager.logout();
                getActivity().finish();
                break;
            case R.id.ctl_lan_setting:
                NavHelper.startActivity(getActivity(), LanguageSettingActivity.class);
                break;
            case R.id.ctv_about_us_setting:
                // TODO: 16/09/2020  跳转关于我们
                break;
            case R.id.ctl_pay_pwd_setting:
                if(UserInfoManager.isLogin()) {
                    NavHelper.startActivity(getActivity(), PayPwdModifyActivity.class);
                }
                break;
            case R.id.rl_ava:
//                if(UserInfoManager.isLogin()) {
//                    NavHelper.startActivity(this, OwnerSettingActivity.class);
//                }
                break;
            case R.id.siv_language:
                NavHelper.startActivity(getActivity(), LanguageSettingActivity.class);
                break;
            case R.id.siv_login_pwd:
                if(UserInfoManager.isLogin()) {
                    NavHelper.startActivity(getActivity(), AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_FORGET_PW));
                }
                break;
            case R.id.siv_pay_pwd:
                if(UserInfoManager.isLogin()) {
                    NavHelper.startActivity(getActivity(), PayPwdModifyActivity.class);
                }
                break;
            case R.id.siv_cache:
                new SimpleDialogWithTwoBtn.Builder(getContext())
                        .setRightLabel(getString(R.string.label_confirm))
                        .setLeftLabel(getString(R.string.label_cancel))
                        .setWarning(getString(R.string.label_clear_cache_tips))
                        .setAutoDismissWhileClick(true)
                        .setCancelable(true)
                        .setOnSubmitListener(new SimpleDialogWithTwoBtn.OnSubmitListener() {
                            @Override
                            public void onLeftBtnClick() {

                            }

                            @Override
                            public void onRightBtnClick() {
                                CleanDataUtils.clearAllCache(getContext());
                                mSivCache.setRightText(CleanDataUtils.getTotalCacheSize(getContext()));
                            }
                        }).build().show();
                break;

        }
    }


}
