package com.ysarch.vmall.page.main.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.domain.bean.MemberBean;
import com.ysarch.vmall.domain.bean.UserInfoResult;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.page.main.MainMineFragment;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;

/**
 * 分类
 * Created by fysong on 24/08/2020
 **/
public class MainMinePresenter extends BasePresenter<MainMineFragment> {
    public void requestUserInfo() {
        AccountLoader.getInstance().requestUserInfo()
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<MemberBean>(getV()) {
                    @Override
                    public void onSuccess(MemberBean userInfoResult) {
                        UserInfoManager.updateUserInfo(userInfoResult);
                        getV().onUserInfoSucc();
                    }
                });
    }
}
