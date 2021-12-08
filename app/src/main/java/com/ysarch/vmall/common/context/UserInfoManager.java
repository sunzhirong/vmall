package com.ysarch.vmall.common.context;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.MemberBean;
import com.ysarch.vmall.domain.bean.UserInfoBean;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.account.AccountActivity;
import com.ysarch.vmall.page.account.FaceBookLoginActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.yslibrary.event.EventCenter;


/**
 * Created by fysong on 15/08/2020.
 */
public class UserInfoManager {
    private static final String USER_KEY = "sp_user_info";

    private static UserInfoBean sUserInfoBean;

    /**
     * 退出登录
     */
    public static void logout() {
        sUserInfoBean = null;
        CacheHelper.removeKey(CacheKeys.KEY_APP_USER);
        EventCenter.getInstance().notify(NotificationDef.EVENT_USER_ACCOUNT_CHANGE);
    }

//    public static void onLoginSuccess(UserInfoBean userInfoBean) {
//        setUser(userInfoBean);
//        CacheHelper.putString(CacheKeys.KEY_APP_ACCOUNT, userInfoBean.getAccount());
//        CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(userInfoBean));
//
//        Log.e("user1",new Gson().toJson(userInfoBean));
//
//        EventCenter.getInstance().notify(NotificationDef.EVENT_USER_ACCOUNT_CHANGE);
//    }


//    public static void updateToken(String token, String tokenHeader) {
//        updateToken(null, token, tokenHeader);
//    }


    public static void updateUserInfo(MemberBean memberBean) {
        if (getUser() != null) {
            if (!TextUtils.isEmpty(memberBean.getPhone()))
                sUserInfoBean.setAccount(memberBean.getPhone());
            sUserInfoBean.setIcon(memberBean.getIcon());
            sUserInfoBean.setMemberLevelId(memberBean.getMemberLevelId());
            sUserInfoBean.setStatus(memberBean.getStatus());
            sUserInfoBean.setNickname(memberBean.getNickname());
            sUserInfoBean.setId( memberBean.getId());
            sUserInfoBean.setWallet(memberBean.getWallet());
            sUserInfoBean.setHasPayPassword(memberBean.isHasPayPassword());

            sUserInfoBean.setOrderReadyCount(memberBean.getOrderReadyCount());
            sUserInfoBean.setOrderReviewCount(memberBean.getOrderReviewCount());
            sUserInfoBean.setOrderWaitPayCount(memberBean.getOrderWaitPayCount());
            sUserInfoBean.setOrderWaitReceiveCount(memberBean.getOrderWaitReceiveCount());
            sUserInfoBean.setBirthday(memberBean.getBirthday());
            sUserInfoBean.setGender(memberBean.getGender());
            sUserInfoBean.setCartItemCount(memberBean.getCartItemCount());

            CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
            Log.e("user2",new Gson().toJson(sUserInfoBean));
            EventCenter.getInstance().notify(NotificationDef.EVENT_USER_INFO_CHANGE);
        }
    }
    public static int getCartItemCount(){
        if(sUserInfoBean!=null) {
           return sUserInfoBean.getCartItemCount();
        }
        return 0;
    }
    public static void updateCartItemCount(int cartItemCount){
        if(sUserInfoBean!=null) {
            sUserInfoBean.setCartItemCount(cartItemCount);
            CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
        }

    }


//    public static void loginSucc(String token, String tokenHeader,UserInfoResult userInfoResult){
//        if (getUser() == null) {
//            sUserInfoBean = new UserInfoBean();
//        }
//        sUserInfoBean.setToken(token);
//        sUserInfoBean.setTokenHeader(tokenHeader);
//
//        if (!TextUtils.isEmpty(userInfoResult.getPhone()))
//            sUserInfoBean.setAccount(userInfoResult.getPhone());
//        sUserInfoBean.setIcon(userInfoResult.getIcon());
//        sUserInfoBean.setMemberLevelId(userInfoResult.getMemberLevelId());
//        sUserInfoBean.setStatus(userInfoResult.getStatus());
//        sUserInfoBean.setNickname(userInfoResult.getUsername());
//        sUserInfoBean.setId((long) userInfoResult.getId());
//
//        CacheHelper.putString(CacheKeys.KEY_APP_ACCOUNT, userInfoResult.getPhone());
//        CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
//        EventCenter.getInstance().notify(NotificationDef.EVENT_USER_ACCOUNT_CHANGE);
//    }


//    public static void updateWallet(float amount) {
//        if (getUser() == null) {
//          return;
//        }
//        sUserInfoBean.setWallet(amount);
//
//        CacheHelper.putString(CacheKeys.KEY_APP_ACCOUNT, sUserInfoBean.getAccount());
//        CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
//        Log.e("user3",new Gson().toJson(sUserInfoBean));
//        EventCenter.getInstance().notify(NotificationDef.EVENT_USER_INFO_CHANGE);
//    }


    public static void updateToken(String token, String tokenHeader) {
        sUserInfoBean.setToken(token);
        sUserInfoBean.setTokenHeader(tokenHeader);
        CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
        Log.e("user4",new Gson().toJson(sUserInfoBean));

    }
    /**
     * 更新token
     *
     * @param account
     * @param token
     * @param tokenHeader
     */
    public static void updateToken(String account, String token, String tokenHeader, MemberBean memberBean,boolean hasPayPassword) {
        if (getUser() == null) {
            sUserInfoBean = new UserInfoBean();
        }
        if (!TextUtils.isEmpty(account)) {
            sUserInfoBean.setAccount(account);
        }
        sUserInfoBean.setToken(token);
        sUserInfoBean.setTokenHeader(tokenHeader);
        sUserInfoBean.setId(memberBean.getId());
        sUserInfoBean.setMemberLevelId(memberBean.getMemberLevelId());
        sUserInfoBean.setNickname(memberBean.getUsername());
        sUserInfoBean.setIcon(memberBean.getIcon());
        sUserInfoBean.setStatus(memberBean.getStatus());
        sUserInfoBean.setWallet(memberBean.getWallet());
        sUserInfoBean.setHasPayPassword(hasPayPassword);

        CacheHelper.putString(CacheKeys.KEY_APP_ACCOUNT, account);
        CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
        Log.e("user5",new Gson().toJson(sUserInfoBean));

        EventCenter.getInstance().notify(NotificationDef.EVENT_USER_ACCOUNT_CHANGE);
    }

    public static void updateFacebookProfile(){

    }


    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return getUser() != null && !TextUtils.isEmpty(getUser().getToken());
    }


    /**
     * 获取用户信息
     *
     * @return 可能会返回null
     */
    public static UserInfoBean getUser() {
        if (sUserInfoBean == null || TextUtils.isEmpty(sUserInfoBean.getToken())) {
            String userInfoStr = CacheHelper.getString(CacheKeys.KEY_APP_USER, null);
            if (!TextUtils.isEmpty(userInfoStr))
                sUserInfoBean = new Gson().fromJson(userInfoStr, UserInfoBean.class);
        }
        return sUserInfoBean;
    }

    /**
     * 存储用户信息
     *
     * @param userInfoBean
     */
    private static void setUser(UserInfoBean userInfoBean) {
        if (userInfoBean == null) return;

        Gson gson = new Gson();
        if (userInfoBean.getToken() != null) {
            UserInfoManager.sUserInfoBean = userInfoBean;
            return;
        }

        UserInfoBean infoBean = getUser();
        if (infoBean != null && infoBean.getToken() != null) {
            userInfoBean.setToken(infoBean.getToken());
        }
        UserInfoManager.sUserInfoBean = userInfoBean;
    }


    public static void update(){
        if(sUserInfoBean != null){
            CacheHelper.putString(CacheKeys.KEY_APP_ACCOUNT, sUserInfoBean.getAccount());
            CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(sUserInfoBean));
            Log.e("user6",new Gson().toJson(sUserInfoBean));

        }
    }

//    public static void updateAndSaveUserInfo(UserInfoBean userInfoBean) {
//        setUser(userInfoBean);
//        CacheHelper.putString(CacheKeys.KEY_APP_ACCOUNT, userInfoBean.getAccount());
//        CacheHelper.putString(CacheKeys.KEY_APP_USER, new Gson().toJson(userInfoBean));
//        Log.e("user7",new Gson().toJson(userInfoBean));
//
//        EventCenter.getInstance().notify(NotificationDef.EVENT_USER_INFO_CHANGE);
//    }

    public static boolean judeIsLogin(Activity context) {
        if (!UserInfoManager.isLogin()) {
            toLogin(context);
            return false;
        } else {
            return true;
        }
    }


    private static void toLogin(Activity context) {
        NavHelper.startActivity(context, AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
    }

}
