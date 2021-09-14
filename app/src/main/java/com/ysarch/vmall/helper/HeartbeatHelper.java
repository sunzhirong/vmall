package com.ysarch.vmall.helper;

import android.os.Message;

import com.google.gson.Gson;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.CustomActivityManager;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.HeartbeatResult;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.page.main.MainActivity;
import com.ysarch.vmall.page.msg.MsgActivity;
import com.yslibrary.component.YSTimer;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fysong on 27/10/2020
 **/
public class HeartbeatHelper implements IEventListener {

    private Gson mGson;
    private YSTimer mTimer;
    private YSTimer.TimerCallback mTimerCallback;

    public static HeartbeatHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private YSTimer.TimerCallback getTimerCallback() {
        if (mTimerCallback == null) {
            mTimerCallback = new YSTimer.TimerCallback() {
                @Override
                protected void onTime() {
                    checkHasNewMsg();
                }
            };
        }
        return mTimerCallback;
    }

    private YSTimer getTimer() {
        if (mTimer == null) {
            mTimer = new YSTimer(60000 * 5);
            mTimer.setTimerCallback(getTimerCallback());
        }
        return mTimer;
    }

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    public void startHeartbeat() {
        if (mTimer == null || !mTimer.isTimeGoing()) {
            EventCenter.getInstance().registerNotification(NotificationDef.EVENT_USER_ACCOUNT_CHANGE, this);
            checkHasNewMsg();
            getTimer().startTime();
        }
    }


    public void stopHeartbeat() {
        if(mTimer == null || !mTimer.isTimeGoing())
            return;
        EventCenter.getInstance().unregisterNotification(this);
        if (mTimer != null) {
            getTimer().reset();
        }
    }

    private void checkHasNewMsg() {
        if (!UserInfoManager.isLogin()) {
            stopHeartbeat();
            return;
        }

        AccountLoader.getInstance().checkHasNewMsg()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.errorBody() != null) {
                            return;
                        }
                        if (response.body() != null) {
                            try {
                                String content = response.body().string();
                                HeartbeatResult heartbeatResult = getGson().fromJson(content, HeartbeatResult.class);
                                if (heartbeatResult.getCode() == 200) {
                                    if (heartbeatResult.isData()) {
                                        AppContext.getsInstance().setHasNewMsg(true);
                                        EventCenter.getInstance().notify(NotificationDef.EVENT_MSG_NEW);
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    @Override
    public void onNotify(Message message) {
        if (message.what == NotificationDef.EVENT_USER_ACCOUNT_CHANGE) {
            if (UserInfoManager.isLogin()) {
                if (!(CustomActivityManager.getInstance().getCurrentActivity() instanceof MsgActivity)) {
                    startHeartbeat();
                }
            } else {
                stopHeartbeat();
            }
        }
    }

    private static class SingletonHolder {
        static final HeartbeatHelper INSTANCE = new HeartbeatHelper();
    }
}
