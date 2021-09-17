package com.ysarch.vmall.page.main;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.uibase.StatusBarView;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.scrollview.CustomScrollView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.account.AccountActivity;
import com.ysarch.vmall.page.address.AddressListActivity;
import com.ysarch.vmall.page.coupon.CouponActivity;
import com.ysarch.vmall.page.main.presenter.MainMinePresenter;
import com.ysarch.vmall.page.msg.MsgActivity;
import com.ysarch.vmall.page.orders.OrderHistoryActivity;
import com.ysarch.vmall.page.setting.SettingActivity;
import com.ysarch.vmall.page.wallet.WalletActivity;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分类
 * Created by fysong on 24/08/2020
 **/
public class MainMineFragment extends BaseFragment<MainMinePresenter> implements IEventListener {

    @BindView(R.id.tv_login_mine_main)
    TextView mTVLogin;
    @BindView(R.id.tv_register_mine_main)
    TextView mTVRegister;
    @BindView(R.id.riv_avatar_mine_main)
    RoundedImageView mRIVAvatar;
    @BindView(R.id.tv_nickname_mine_main)
    TextView mTVNickname;
    @BindView(R.id.tv_uid_mine_main)
    TextView mTVUid;
//    @BindView(R.id.iv_frag_mine_main)
//    ImageView mIVFrag;
    @BindView(R.id.csv_mine_main)
    CustomScrollView mScrollView;
    @BindView(R.id.sv_status_bar_mine_main)
    StatusBarView mStatusBarView;
    @BindView(R.id.ctl_title_bar_mine_main)
    ConstraintLayout mCTLTitleBar;
    @BindView(R.id.v_divide_env_mine_main)
    View mVEnvDivide;
    @BindView(R.id.tv_dot_unpay_orders)
    TextView mTvDotUnpayOrders;
    @BindView(R.id.tv_dot_audit_waiting_orders)
    TextView mTvDotAuditWaitingOrders;
    @BindView(R.id.tv_dot_deliver_waiting_orders)
    TextView mTvDotDeliverWaitingOrders;
    @BindView(R.id.tv_dot_delivered_orders)
    TextView mTvDotDeliveredOrders;
    @BindView(R.id.ctv_wallet_num_mine_main)
    TextView mCtvWallet;


    private boolean bOverhead = false;

    @Override
    public void initData(Bundle savedInstanceState) {

//        if (VMallBuildConfig.isDebugBuild()) {
//            mCTVEnv.setVisibility(View.VISIBLE);
//            mVEnvDivide.setVisibility(View.VISIBLE);
//            switch (VMallBuildConfig.CUR_ENV) {
//                case VMallBuildConfig.ENV_DEV:
//                    mCTVEnv.setText(VMallBuildConfig.ENV_DEV_DESC);
//                    break;
//                case VMallBuildConfig.ENV_DEV_HK:
//                    mCTVEnv.setText(VMallBuildConfig.ENV_DEV_HK_DESC);
//                    break;
//                case VMallBuildConfig.ENV_DEV_SZ:
//                    mCTVEnv.setText(VMallBuildConfig.ENV_DEV_SZ_DESC);
//                    break;
//                case VMallBuildConfig.ENV_PROD:
//                    mCTVEnv.setText(VMallBuildConfig.ENV_PROD_DESC);
//                    break;
//                default:
//                    break;
//
//            }
//            mCTVEnv.setOnClickListener(v -> switchEnv());
//        }

//        mIVFrag.setImageResource(AppContext.getsInstance().getLanguageEntity().getIconRes());
        resetUserInfoUI();
//        if (UserInfoManager.isLogin()) {
//            getPresenter().requestUserInfo(UserInfoManager.getUser().getAccount());
//        }
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_USER_ACCOUNT_CHANGE, this);
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_USER_INFO_CHANGE, this);
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_MSG_NEW, this);
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_MSG_NEW_NONE, this);
        mScrollView.setOnScrollListener(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                Log.e("MainMineFragment", "onScrollChanged: " + l + ", " + t + ", " + oldl + ", " + oldt);
                Log.e("MainMineFragment", "onScrollChanged: scrollY " + mScrollView.getScrollY());

                if (t > 0) {
                    if (!bOverhead) {
                        bOverhead = true;
                        mStatusBarView.setBackgroundColor(0xffF94956);
                        mCTLTitleBar.setBackgroundColor(0xffF94956);
                    }
                } else {
                    if (bOverhead) {
                        bOverhead = false;
                        mStatusBarView.setBackgroundColor(0x00F94956);
                        mCTLTitleBar.setBackgroundColor(0x00F94956);
                    }
                }

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_main;
    }

    @Override
    public MainMinePresenter newPresenter() {
        return new MainMinePresenter();
    }


    @OnClick(R.id.tv_register_mine_main)
    void onRegisterClick() {
        NavHelper.startActivity(getActivity(), AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_REGISTER));
    }

    @OnClick({R.id.ctv_more_order_mine_main, R.id.tv_login_mine_main,
            R.id.ctl_setting_mine_main, R.id.ctv_coupon_mine_main, R.id.ctv_wallet_num_mine_main,
            R.id.ctv_address_mine_main, R.id.ctv_unpay_orders_mine_main, R.id.ctv_audit_waiting_orders_mine_main,
            R.id.ctv_deliver_waiting_orders_mine_main, R.id.ctv_delivered_orders_mine_main,
            R.id.iv_bell_mine_main,R.id.ctv_help_mine_main})
    void onViewClick(View view) {
        if (view.getId() == R.id.ctl_setting_mine_main) {
            NavHelper.startActivity(getActivity(), SettingActivity.class);
        } else if (UserInfoManager.judeIsLogin(getActivity())) {
            switch (view.getId()) {
                case R.id.tv_login_mine_main:
                    NavHelper.startActivity(getActivity(), AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
                    break;
                case R.id.ctv_more_order_mine_main:
                    NavHelper.startActivity(getActivity(), OrderHistoryActivity.class);
                    break;
                case R.id.ctv_coupon_mine_main:
                    NavHelper.startActivity(getActivity(), CouponActivity.class);
                    break;
                case R.id.ctv_wallet_num_mine_main:
                    NavHelper.startActivity(getActivity(), WalletActivity.class);
                    break;
                case R.id.ctv_address_mine_main:
                    NavHelper.startActivity(getActivity(), AddressListActivity.class);
                    break;
                case R.id.ctv_unpay_orders_mine_main:
                    NavHelper.startActivity(getActivity(), OrderHistoryActivity.class,
                            OrderHistoryActivity.getBundle(Constants.STATUS_ORDER_UNPAY));
                    break;
                case R.id.ctv_audit_waiting_orders_mine_main:
                    NavHelper.startActivity(getActivity(), OrderHistoryActivity.class,
                            OrderHistoryActivity.getBundle(Constants.STATUS_ORDER_AUDIT_WAITING));
                    break;
                case R.id.ctv_deliver_waiting_orders_mine_main:
                    NavHelper.startActivity(getActivity(), OrderHistoryActivity.class,
                            OrderHistoryActivity.getBundle(Constants.STATUS_ORDER_DELIVER_WAITING));
                    break;
                case R.id.ctv_delivered_orders_mine_main:
                    NavHelper.startActivity(getActivity(), OrderHistoryActivity.class,
                            OrderHistoryActivity.getBundle(Constants.STATUS_ORDER_DELIVERED));
                    break;
                case R.id.iv_bell_mine_main:
                    if (UserInfoManager.judeIsLogin(getActivity())) {
                        NavHelper.startActivity(getActivity(), MsgActivity.class);
                    }
                    break;
                case R.id.ctv_help_mine_main:
                    int lanId = AppContext.getsInstance().getLanguageEntity().getLanId();
                    switch (lanId){
                        case Constants.ID_LAN_KM:
                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/help/km/index.html"));
                            break;
                        case Constants.ID_LAN_ZH:
                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/help/zh/index.html"));
                            break;
                        default:
                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(" http://portal.sabayshop.club/help/en/index.html"));
                            break;
                    }

                    break;
            }
        }

    }

    @Override
    public void onNotify(Message message) {
        switch (message.what) {
            case NotificationDef.EVENT_USER_ACCOUNT_CHANGE:
            case NotificationDef.EVENT_USER_INFO_CHANGE:
                resetUserInfoUI();
                break;
            case NotificationDef.EVENT_MSG_NEW:
            case NotificationDef.EVENT_MSG_NEW_NONE:
                if (!isHidden())
                    checkMsg();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregisterNotification(this);
    }

    private void resetUserInfoUI() {
        if (UserInfoManager.isLogin()) {
            mTVNickname.setVisibility(View.VISIBLE);
            mTVUid.setVisibility(View.VISIBLE);
            mTVLogin.setVisibility(View.GONE);
            mTVRegister.setVisibility(View.GONE);
            mCtvWallet.setVisibility(View.VISIBLE);
            mCtvWallet.setText(VMallUtils.convertPriceString(UserInfoManager.getUser().getWallet()));

            if (!TextUtils.isEmpty(UserInfoManager.getUser().getAccount())) {
//                mTVAccount.setText(UserInfoManager.getUser().getAccount());
            }

            if (!TextUtils.isEmpty(UserInfoManager.getUser().getAccount())) {
//                mTVNickname.setText(String.format(ResUtils.getString(R.string.format_nickname_mine,
//                        UserInfoManager.getUser().getNickname())));
                mTVNickname.setText(UserInfoManager.getUser().getNickname());
            }

            mTVUid.setText(String.format(ResUtils.getString(R.string.format_uid_mine,
                    UserInfoManager.getUser().getId())));

            BeeGlide.with(this)
                    .load(ImageLoadConfig.create(UserInfoManager.getUser().getIcon())
                            .placeHolder(R.drawable.ic_avatar_placeholder), mRIVAvatar);
        } else {
            mTVNickname.setVisibility(View.GONE);
//            mTVAccount.setVisibility(View.GONE);
            mTVUid.setVisibility(View.GONE);
//            mVArrowAccount.setVisibility(View.GONE);
            mTVLogin.setVisibility(View.VISIBLE);
            mTVRegister.setVisibility(View.VISIBLE);
            mRIVAvatar.setImageResource(R.drawable.ic_avatar_placeholder);
            mCtvWallet.setVisibility(View.INVISIBLE);
        }
    }

    public void onUserInfoSucc() {
        resetUserInfoUI();
        resetDot();
    }

    private void resetDot() {
//        private int orderReadyCount;
//        private int orderReviewCount;
//        private int orderWaitPayCount;
//        private int orderWaitReceiveCount;
//        UserInfoManager.getUser().getOrderWaitPayCount()
//        mTvDotUnpayOrders.setVisibility();
        setDot(mTvDotUnpayOrders,UserInfoManager.getUser().getOrderWaitPayCount());
        setDot(mTvDotAuditWaitingOrders,UserInfoManager.getUser().getOrderReviewCount());
        setDot(mTvDotDeliverWaitingOrders,UserInfoManager.getUser().getOrderReadyCount());
        setDot(mTvDotDeliveredOrders,UserInfoManager.getUser().getOrderWaitReceiveCount());
    }

    private void setDot(TextView tvDot, int value) {
        if(value == 0){
            tvDot.setVisibility(View.GONE);
        }else {
            tvDot.setVisibility(View.VISIBLE);
            if(value<10){
                tvDot.setBackground(getResources().getDrawable(R.drawable.shape_oval_fill_primary));
                tvDot.setWidth(SizeUtils.dp2px(13));
                tvDot.setHeight(SizeUtils.dp2px(13));
            }else {
                tvDot.setBackground(getResources().getDrawable(R.drawable.shape_round_rect_r6_5_fill_primary));
                tvDot.setWidth(SizeUtils.dp2px(20));
                tvDot.setHeight(SizeUtils.dp2px(13));
            }
            tvDot.setText(String.valueOf(value));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            checkMsg();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            checkMsg();
        }
        if(UserInfoManager.isLogin()) {
            getPresenter().requestUserInfo();
        }
    }

    private void checkMsg() {
//        mMsgDot.setVisibility(AppContext.getsInstance().getHasNewMsg() ? View.VISIBLE : View.GONE);
    }


//    private void switchEnv() {
//        List<String> sexList = new ArrayList<>();
//        sexList.add("dev_sz:" + ApiUrl.DEV_HOST_SZ);
//        sexList.add("dev_hk:" + ApiUrl.DEV_HK_HOST);
//        sexList.add("dev:" + ApiUrl.DEV_HOST);
//        sexList.add("release:" + ApiUrl.RELEASE_HOST);
//        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> {
//            if (options1 + 1 != VMallBuildConfig.CUR_ENV) {
//                //返回的分别是三个级别的选中位置
////                mCTVEnv.setText(sexList.get(options1));
//                CacheHelper.putInt(CacheKeys.KEY_ENV_VALUE, options1 + 1);
//                UserInfoManager.logout();
//
//                SimpleDialogWithTwoBtn dialog = new SimpleDialogWithTwoBtn.Builder(getContext())
//                        .setCancelable(true)
//                        .setLeftLabel("取消")
//                        .setRightLabel("确定")
//                        .setWarning("需重启app，数据环境才生效")
//                        .setOnSubmitListener(new SimpleDialogWithTwoBtn.OnSubmitListener() {
//                            @Override
//                            public void onLeftBtnClick() {
//
//                            }
//
//                            @Override
//                            public void onRightBtnClick() {
//                                CustomActivityManager.getInstance().finishAll();
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                            }
//                        })
//                        .build();
//                dialog.show();
//            }
//
//        }).setTitleText("数据环境切换")
//                .setTitleColor(0xff343434).setTitleSize(15)
//                .setCancelColor(getResources().getColor(R.color.picker_cancel))
//                .setSubmitColor(getResources().getColor(R.color.picker_confirm))
//                .setSubCalSize(15)
//                .build();
//        pvOptions.setPicker(sexList);
//        pvOptions.setSelectOptions(VMallBuildConfig.CUR_ENV - 1);
//        pvOptions.show();
//    }

}
