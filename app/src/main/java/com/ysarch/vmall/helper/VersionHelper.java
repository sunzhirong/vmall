package com.ysarch.vmall.helper;

import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.domain.constant.CacheKeys;

/**
 * Created by fysong on 23/01/2019.
 */

public class VersionHelper {
    private static Object mObject = new Object();
    private static VersionHelper sInstance;
    private int mLastVersionCode;

    public VersionHelper() {
        mLastVersionCode = CacheHelper.getInt(CacheKeys.KEY_VERSION_CODE, 0);

        if (isFirstLaunch()) {
            updateVersionCode();
        }
    }

    public static VersionHelper getsInstance() {
        if (sInstance == null) {
            init();
        }

        return sInstance;
    }

    public static void init() {
        if (sInstance == null) {
            synchronized (mObject) {
                if (sInstance == null) {
                    sInstance = new VersionHelper();
                }
            }
        }
    }

    /**
     * 是否全新安装
     *
     * @return
     */
    public boolean isNewlyInstall() {
        return mLastVersionCode == 0;
    }

    /**
     * 是否第一次启动
     *
     * @return
     */
    public boolean isFirstLaunch() {
        return mLastVersionCode != BuildConfig.VERSION_CODE;
    }


    /**
     * 在主页打开后更新
     */
    public void updateVersionCode() {
        CacheHelper.putInt(CacheKeys.KEY_VERSION_CODE, BuildConfig.VERSION_CODE);
    }


}
