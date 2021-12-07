package com.ysarch.vmall.page.account.presenter;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.UploadLogLoader;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.account.FacebookLoginFragment;
import com.ysarch.vmall.page.account.LoginFragment;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.UploadUtils;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by fysong on 13/09/2020
 **/
public class FaceBookLoginPresenter extends BasePresenter<FacebookLoginFragment> {



    public void facebookLogin( String name, String token, String outId,String headUrl,String email,long visitTime) {
        getV().showLoadingDialog();
        AccountLoader.getInstance().facebookLogin(outId, token,name,headUrl,email)
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResult>(getV()) {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        UserInfoManager.updateToken("", loginResult.getToken(), loginResult.getTokenHead(),
                                loginResult.getMember(),loginResult.getMember().isHasPayPassword());
                        loginLog(visitTime,"",true);
                        getV().onLoginSuccess();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        if(error.getType()!=NetError.OtherError)
                            loginLog(visitTime,error.getMessage(),false);
                    }
                });
    }

    public void loginLog(long visitTime,String failReason,boolean result){
        Map<String,Object> map = new HashMap<>();
        map.put("visitTime",visitTime/1000);
        map.put("failReason",failReason);
        map.put("operationResult",result);
        map.put("serverErrorCode",0);
        map.put("deviceBaseInfo", UploadUtils.getUploadRequest());
        UploadLogLoader.getInstance().loginLog(map)
                .enqueue(new Callback<ResponseBody>() {
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }
}
