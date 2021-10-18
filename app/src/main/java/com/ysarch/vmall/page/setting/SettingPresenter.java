package com.ysarch.vmall.page.setting;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.component.oss.OSSHelper;
import com.ysarch.vmall.domain.bean.MemberBean;
import com.ysarch.vmall.domain.bean.OssBean;
import com.ysarch.vmall.domain.bean.UpdateBean;
import com.ysarch.vmall.domain.bean.UserInfoBean;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.ConfigLoader;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

public class SettingPresenter extends BasePresenter<SettingFragment> {
    public void checkUpdate(){
        AccountLoader.getInstance().checkUpdate(BuildConfig.VERSION_CODE)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<UpdateBean>(getV()) {
                    @Override
                    public void onSuccess(UpdateBean updateBean) {
                        getV().onCheckUpdateSucc(updateBean);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCheckUpdateFail();
                    }
                });
    }
}
