package com.ysarch.vmall.component.ease;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.context.VMallBuildConfig;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.UserInfoBean;
import com.ysarch.vmall.page.chat.ChatActivity;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * 环信客服初始化管理
 * Created by fysong on 26/10/2020
 **/
public class EaseHelper implements IEventListener {

    private static final String TAG = "EaseHelper";
    private static final String EASE_IM_SERVER = "kefuchannelimid_093639";
    private static final String EASE_APP_KEY = "1448201019025802#kefuchannelapp86733";
    private static final String EASE_TENANT_ID = "86733";


    private Application mApplication;

    public static EaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init(Application application) {
        mApplication = application;
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(EASE_APP_KEY);//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId(EASE_TENANT_ID);//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
        options.setConsoleLog(!VMallBuildConfig.isReleaseEnv());

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(application, options)) {
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(application);

        ChatClient.getInstance().chatManager().addMessageListener(new ChatManager.MessageListener() {
            @Override
            public void onMessage(List<Message> list) {
                //收到普通消息
                Log.d(TAG, "chatManager#onMessage");
            }

            @Override
            public void onCmdMessage(List<Message> list) {
                //收到命令消息，命令消息不存数据库，一般用来作为系统通知，例如留言评论更新，
                //会话被客服接入，被转接，被关闭提醒
                Log.d(TAG, "chatManager#onCmdMessage");
            }

            @Override
            public void onMessageStatusUpdate() {
                //消息的状态修改，一般可以用来刷新列表，显示最新的状态
                Log.d(TAG, "chatManager#onMessageStatusUpdate");
            }

            @Override
            public void onMessageSent() {
                //发送消息后，会调用，可以在此刷新列表，显示最新的消息
                Log.d(TAG, "chatManager#onMessageSent");
            }
        });

        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_USER_ACCOUNT_CHANGE, this);
    }

    /**
     * 环信账号注册并登录
     *
     * @param callback
     */
    public void loginIfRegistered(@NonNull EaseAccountCallback callback) {
        if (UserInfoManager.isLogin()) {

            if (ChatClient.getInstance().isLoggedInBefore()) {
                if (callback != null) {
                    callback.onLoginSucc();
                }
            } else {
                //未登录，需要登录后，再进入会话界面
                UserInfoBean userInfoBean = UserInfoManager.getUser();
                String p = "u_" + userInfoBean.getId();
                ChatClient.getInstance().register(p, p, new Callback() {
                    @Override
                    public void onSuccess() {
                        login(p, p, callback);
                    }

                    @Override
                    public void onError(int code, String errorMsg) {
                        switch (code) {
                            case Error.USER_ALREADY_EXIST:
                            case Error.GENERAL_ERROR:
                                login(p, p, callback);
                                break;
                            case Error.NETWORK_ERROR:
                            case Error.USER_AUTHENTICATION_FAILED:
                            case Error.USER_ILLEGAL_ARGUMENT:
                                //用户名非法
                                if (callback != null) {
                                    callback.onRegisterError(code, errorMsg);
                                }
                                break;
                        }
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        if (callback != null) {
                            callback.onWaiting(progress / 2);
                        }
                    }
                });
            }
//
////ErrorCode:
//            Error.NETWORK_ERROR 网络不可用
//            Error.USER_ALREADY_EXIST  用户已存在
//            Error.USER_AUTHENTICATION_FAILED 无开放注册权限（后台管理界面设置[开放|授权]）
//            Error.USER_ILLEGAL_ARGUMENT 用户名非法
        }
    }


    /**
     * 环信账户登录
     *
     * @param username
     * @param password
     * @param callback
     */
    private void login(String username, String password, EaseAccountCallback callback) {
        ChatClient.getInstance().login(username, password, new Callback() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onLoginSucc();
                }
            }

            @Override
            public void onError(int code, String errorMsg) {
                if (callback != null) {
                    callback.onLoginError(code, errorMsg);
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onWaiting(50 + progress / 2);
                }
            }
        });
    }


    /**
     * 环信账号登出
     */
    public void logout() {
        if (ChatClient.getInstance().isLoggedInBefore()) {
            ChatClient.getInstance().logout(true, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(int code, String error) {

                }

                @Override
                public void onProgress(int progress, String status) {

                }
            });
        }
    }


    public void navKefu(Activity activity, IView view) {
        Intent intent = new IntentBuilder(activity)
                .setTargetClass(ChatActivity.class)
                .setServiceIMNumber(EASE_IM_SERVER)
                .setShowUserNick(false)
                .build();
        navKefu(activity, view, intent);
    }
    /**
     * 订单详情跳转客服
     * @param activity
     * @param view
     * @param orderBean
     */
    public void navKefu(Activity activity, IView view, OrderBean orderBean) {
        Intent intent = new IntentBuilder(activity)
                .setTargetClass(ChatActivity.class)
                .setServiceIMNumber(EASE_IM_SERVER)
                .setShowUserNick(false)
                .setBundle(ChatActivity.getBundle(orderBean))
                .build();
        navKefu(activity, view, intent);
    }


    /**
     * 商品详情跳转客服
     * @param activity
     * @param view
     */
    public void navKefu(Activity activity, IView view, GoodsDetailItemBean goodsItemBean) {
        Intent intent = new IntentBuilder(activity)
                .setTargetClass(ChatActivity.class)
                .setServiceIMNumber(EASE_IM_SERVER)
                .setShowUserNick(false)
                .setBundle(ChatActivity.getBundle(goodsItemBean))
                .build();
        navKefu(activity, view, intent);
    }

    /**
     * 跳转客服页面
     *
     * @param activity
     * @param view
     */
    public void navKefu(Activity activity, IView view, Intent intent) {
        if (ChatClient.getInstance().isLoggedInBefore()) {
//            Intent intent = new IntentBuilder(activity)
//                    .setServiceIMNumber(EASE_IM_SERVER) //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
//                    .build();

            activity.startActivity(intent);
        } else {
            view.showLoadingDialog();
            loginIfRegistered(new EaseAccountCallback() {
                @Override
                public void onRegisterError(int code, String errorMsg) {
                    view.dismissLoadingDialog();
                    view.showTs(errorMsg);
                }

                @Override
                public void onLoginSucc() {
                    view.dismissLoadingDialog();
                    navKefu(activity, view, intent);
                }

                @Override
                public void onWaiting(int progress) {
                }

                @Override
                public void onLoginError(int code, String errorMsg) {
                    view.dismissLoadingDialog();
                    view.showTs(errorMsg);
                }
            });
        }
    }

    @Override
    public void onNotify(android.os.Message message) {
        if (message.what == NotificationDef.EVENT_USER_ACCOUNT_CHANGE) {
            if (!UserInfoManager.isLogin() && ChatClient.getInstance().isLoggedInBefore()) {
                logout();
            }
        }
    }

    public interface EaseAccountCallback {
        void onRegisterError(int code, String errorMsg);

        void onLoginSucc();

        void onWaiting(int progress);

        void onLoginError(int code, String errorMsg);
    }

    static class SingletonHolder {
        static EaseHelper INSTANCE = new EaseHelper();
    }
}
