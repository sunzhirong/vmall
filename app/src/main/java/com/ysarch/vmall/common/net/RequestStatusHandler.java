package com.ysarch.vmall.common.net;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.ysarch.vmall.R;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.context.CustomActivityManager;
import com.ysarch.vmall.common.context.ThreadManager;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.page.account.AccountActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.StringUtils;
import com.yslibrary.utils.ToastUtils;

import org.json.JSONObject;

import cn.droidlover.xdroidmvp.net.RequestHandler;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class RequestStatusHandler implements RequestHandler {

    public static RequestStatusHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        return request;
    }

    @Override
    public Response onAfterRequest(Response response, Interceptor.Chain chain) {
        if (response != null) {
            try {
                // 注意获取body内容时，不可以直接读取String或者Stream，会导致流被关闭而上层业务无法获取body体
                if (response.body() != null) {
                    BufferedSource source = response.body().source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();

                    JSONObject jsonObject = new JSONObject(buffer.clone().readString(StringUtils.UTF8));
                    int businessCode = jsonObject.getInt("code");
                    if (NetCode.needToLogin(businessCode)) {
                        ThreadManager.getInstance().mainThread(new Runnable() {
                            @Override
                            public void run() {
                                UserInfoManager.logout();
                                Activity activity = CustomActivityManager.getInstance().getCurrentActivity();
                                if (activity != null) {
                                    Toast.makeText(activity,
                                            ResUtils.getString(R.string.tip_need_login),
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(activity, AccountActivity.class);
                                    intent.putExtras(AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                    activity.startActivityForResult(intent, RequestCode.CODE_AUTO_LOGIN);
                                }
                            }
                        });
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private static class SingletonHolder {
        static RequestStatusHandler INSTANCE = new RequestStatusHandler();
    }
}
