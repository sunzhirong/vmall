package com.ysarch.vmall.common.context;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ysarch.vmall.helper.ClipContentHelper;
import com.ysarch.vmall.helper.HeartbeatHelper;
import com.ysarch.vmall.page.msg.MsgActivity;
import com.ysarch.vmall.page.splash.SplashActivity;


/**
 * 该类未来还将处理ui适配等问题
 * Created by fysong on 2019-10-15
 **/
public class ActivityLifeCallback implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "ActivityLifeCallback";
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;
    boolean mInForeground = true;
    private int count = 0;

    private ClipContentHelper mClipContentHelper;

    public ClipContentHelper getClipContentHelper() {
        if(mClipContentHelper == null){
            mClipContentHelper = new ClipContentHelper();
        }
        return mClipContentHelper;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        CustomActivityManager.getInstance().addActivity(activity);
        proofreadDensity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }


    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "activity resumed:" + activity);
        count++;
        if (!mInForeground) {
            mInForeground = true;
        }

        if(!CustomActivityManager.getInstance().isForeground()
                && !(activity instanceof SplashActivity)){
            getClipContentHelper().checkClipBoard(activity);
        }
        CustomActivityManager.getInstance().setForeground(true);

        if(activity instanceof MsgActivity )
            HeartbeatHelper.getInstance().stopHeartbeat();
        else if(UserInfoManager.isLogin())
            HeartbeatHelper.getInstance().startHeartbeat();
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (--count == 0) {
            mInForeground = false;
            CustomActivityManager.getInstance().setForeground(false);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        CustomActivityManager.getInstance().removeActivity(activity);
    }


    private void proofreadDensity(Activity activity) {
//        if (activity instanceof ThirdPartyActivity) {
//            return;
//        }
        Application application = AppContext.getsInstance().getsApplication();
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        float targetDensity = appDisplayMetrics.widthPixels * 1F / 375;
        int targetDensityDpi = (int) (160 * targetDensity);
        float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
