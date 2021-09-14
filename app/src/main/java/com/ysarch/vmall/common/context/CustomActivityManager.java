package com.ysarch.vmall.common.context;

import android.app.Activity;
import android.util.Log;


import com.yslibrary.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 记录相关Activity，配合apk安装等功能使用
 * Created by fysong on 2019-10-15
 */

public class CustomActivityManager {
    private static CustomActivityManager sInstance = new CustomActivityManager();
    private List<WeakReference<Activity>> sAllActivity = new ArrayList<>();
    private boolean isForeground;

    private CustomActivityManager() {

    }

    public static CustomActivityManager getInstance() {

        return sInstance;
    }

    public Activity getFirstActivity() {
        Activity currentActivity = null;
        Iterator<WeakReference<Activity>> it = sAllActivity.iterator();
        while (it.hasNext()) {
            WeakReference wr = it.next();
            if (wr != null && wr.get() != null && !((Activity) wr.get()).isFinishing()) {
                currentActivity = (Activity) wr.get();
                break;
            }
        }
        return currentActivity;
    }

    public void printActivities() {
        Log.d("ActivityManager", "==========");
        Iterator<WeakReference<Activity>> it = sInstance.sAllActivity.iterator();
        while (it.hasNext()) {
            WeakReference wr = it.next();
            if (wr != null && wr.get() != null) {
                Log.d("ActivityManager", "activity:" + wr.get().getClass().getName());
            }
        }
        Log.d("ActivityManager", "==========");
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        Iterator<WeakReference<Activity>> it = sAllActivity.iterator();
        while (it.hasNext()) {
            WeakReference wr = it.next();
            if (wr != null && wr.get() != null && !((Activity) wr.get()).isFinishing()) {
                currentActivity = (Activity) wr.get();
            }
        }
        return currentActivity;
    }

    public Activity getActivityUnder(Activity activity) {
        Activity target = null;
        for (int i = CollectionUtils.size(sAllActivity) - 1; i >= 0; i--) {
            if (activity == sAllActivity.get(i).get() && i != 0) {
                target = sAllActivity.get(i - 1).get();
            }
        }

        return target;
    }

    public boolean noneActivity() {
        Iterator<WeakReference<Activity>> it = sAllActivity.iterator();
        while (it.hasNext()) {
            WeakReference wr = it.next();
            if (wr != null && wr.get() != null) {
                return false;
            }
        }

        return true;
    }

    public void addActivity(Activity activity) {
        sAllActivity.add(new WeakReference(activity));
    }

    public void removeActivity(Activity activity) {
        Iterator<WeakReference<Activity>> it = sAllActivity.iterator();
        while (it.hasNext()) {
            WeakReference wr = it.next();
            if (wr == null || wr.get() == null || wr.get() == activity) {
                it.remove();
            }
        }
    }

    public boolean isForeground() {
        return isForeground;
    }


    public void setForeground(boolean foreground) {
        isForeground = foreground;
    }

    public void finishAll() {
        for (WeakReference wr : sAllActivity) {
            if (wr != null && wr.get() != null) {
                ((Activity) wr.get()).finish();
            }
        }
    }


    public int getActivitySize() {
        return sAllActivity.size();
    }
}
