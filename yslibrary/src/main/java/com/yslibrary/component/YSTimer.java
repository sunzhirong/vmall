package com.yslibrary.component;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by fysong on 18/12/2017.
 */
public class YSTimer {
    private static int MSG_ON_TIME = 1;
    private static int MSG_ON_PAUSE = 2;
    private long mPeriod;
    private boolean mTimeGoing;

    private TimerCallback mTimerCallback;
    private Handler mHandler;

    public YSTimer(long period) {
        this.mPeriod = period;
    }



    protected Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MSG_ON_TIME && mTimeGoing) {
                        if (mTimerCallback != null) {
                            mTimerCallback.onTime();
                        }
                        sendEmptyMessageDelayed(MSG_ON_TIME, mPeriod);
                    }
                }
            };
        }
        return mHandler;
    }

    /**
     * 开始计时，计时期间重复调用无效
     */
    public void startTime() {
        if (!getHandler().hasMessages(MSG_ON_TIME)) {
            getHandler().sendEmptyMessageDelayed(MSG_ON_TIME, mPeriod);
        }
        mTimeGoing = true;
    }

    public void reset() {
        mTimeGoing = false;
        if (mHandler != null && mHandler.hasMessages(MSG_ON_TIME)) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    public boolean isTimeGoing() {
        return mTimeGoing;
    }

    /**
     * 计时并立即执行任务
     */
    public void callAndStartTime() {
        if (!getHandler().hasMessages(MSG_ON_TIME)) {
            getHandler().sendEmptyMessage(MSG_ON_TIME);
        }
        mTimeGoing = true;
    }

    public void setTimerCallback(TimerCallback timerCallback) {
        mTimerCallback = timerCallback;
    }

    public abstract static class TimerCallback {
        /**
         * 计时器周期执行调用
         */
        protected abstract void onTime();
    }
}
