package com.ysarch.vmall.page.setting;

import android.text.TextUtils;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.component.oss.OSSHelper;
import com.ysarch.vmall.domain.bean.MemberBean;
import com.ysarch.vmall.domain.bean.OssBean;
import com.ysarch.vmall.domain.bean.UserInfoBean;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.ConfigLoader;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

public class OwnerSettingPresenter extends BasePresenter<OwnerSettingFragment> {

    public void uploadPic(String proofPicPath) {
      if (OSSHelper.getInstance().isInited) {
                OSSHelper.getInstance().uploadImage(proofPicPath, new OSSHelper.Callback() {
                    @Override
                    public void onUploadSucc(String url) {
                        getV().dismissLoadingDialog();
                        UserInfoBean user = UserInfoManager.getUser();
                        updateMemberInfo(VMallUtils.updateBirthday(user.getBirthday()),user.getGender(),url,user.getNickname());
//                        getV().onUploadSucc(url);
                    }

                    @Override
                    public void onUploadFail() {
                        getV().dismissLoadingDialog();
                        getV().showTs(ResUtils.getString(R.string.tip_img_upload_fail));
                    }
                });
            } else {
                requestOssData(proofPicPath);
            }
    }

    public void requestOssData(String proofPicPath) {
        ConfigLoader.getInstance().requestOss()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<OssBean>() {
                    @Override
                    public void onSuccess(OssBean ossBean) {
                        OSSHelper.getInstance().initOss(ossBean);
                        uploadPic(proofPicPath);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().dismissLoadingDialog();
                        getV().showTs(error.getMessage());
                    }
                });
    }


    public void updateMemberInfo( String birthday, int gender, String icon, String nickname) {
        AccountLoader.getInstance().updateMemberInfo(birthday,gender,icon,nickname)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object obj) {
//                        UserInfoManager.updateUserInfo(userInfoResult);
//                        getV().onUserInfoSucc();
                        AccountLoader.getInstance().requestUserInfo()
                                .compose(showLoadingDialog())
                                .compose(getV().bindToLifecycle())
                                .subscribe(new ApiSubscriber<MemberBean>(getV()) {
                                    @Override
                                    public void onSuccess(MemberBean userInfoResult) {
                                        UserInfoManager.updateUserInfo(userInfoResult);
                                        getV().onUserInfoSucc();
                                    }
                                });
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}
