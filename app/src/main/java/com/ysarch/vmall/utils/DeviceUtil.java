package com.ysarch.vmall.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ysarch.vmall.VMallApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * author : zuofeng
 * date : 2019-06-25
 * desc : 设备工具，获取设备的各种信息
 */
public class DeviceUtil {

    /**
     * 获取设备IMEI，即设备唯一标识
     */
    public static final String getIMEI(Context context) {
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            //获取IMEI号
//            String imei = telephonyManager.getDeviceId();
//            if (imei == null) {
//                imei = "";
//            }
//            return imei;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
        return "";
    }

    /**
     * 获取设备ID
     */
    public static String getDeviceId(Context context) {
        try {
            String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return android_id != null ? android_id : "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当前手机系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备名称
     */
    public static String getDeviceName() {
        return android.os.Build.DEVICE;
    }

    /**
     * 获取设备Mac地址
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress() != null ? info.getMacAddress() : "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取设备型号
     */
    public static String getDeviceModel() {
        return (android.os.Build.MODEL).replace(" ", "");
    }

    /**
     * 获取网络IP
     */
    public static String getLocalIp(Context context) {
        if (isWifiConnected(context)) {//连接wifi
            return getWifiIpAddress(context);
        }
        return get3GIpAddress();
    }

    /**
     * 获取本机wifi状态下ip
     */
    private static String getWifiIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            // 获取32位整型IP地址
            int ipAddress = wifiInfo.getIpAddress();
            //返回整型地址转换成“*.*.*.*”地址
            return String.format("%d.%d.%d.%d",
                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本机数据流量状态下ip
     */
    private static String get3GIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                if (intf.isUp()) {//过滤虚拟网卡ip10.0.2.15
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断当前是否连接wifi
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }

    /**
     * 获取当前的网络状态
     */
    public static String getNetType(Context context) {
        switch (getAPNType(context)) {
            case 1:
                return "Wi-Fi";
            case 2:
                return "2G";
            case 3:
                return "3G";
            case 4:
                return "4G";
            default:
                return "unknown";
        }
    }

    /**
     * 获取当前的网络状态 ：没有网络-0, WIFI网络-1, 2G网络-2, 3G网络-3, 4G网络-4
     * 自定义
     */
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (telephonyManager != null) {
                if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = 4;
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                        || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = 3;
                    //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
                } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                        || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                        || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                        && !telephonyManager.isNetworkRoaming()) {
                    netType = 2;
                } else {
                    netType = 2;
                }
            }
        }
        return netType;
    }


    /**
     * 获取App版本名称，如 "1.0.0"
     */
    public static String getVersionName(Context context) {
        if (context == null) return "";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知版本号";
        }
    }

    /**
     * 获取app的版本号，如 15
     */
    public static int getVersionCode(Context context) {
        int versionCode = Integer.MAX_VALUE;
        if (context == null) {
            return versionCode;
        }
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取.apk文件的PackageInfo
     *
     * @param context context
     * @param path    .apk文件的路径
     */
    public static PackageInfo getApkInfoByPath(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        return pi;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     */
    public static int getNoHasVirtualKey(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            WindowManager windowManager = ((WindowManager) VMallApplication.sApplication.getSystemService(Context.WINDOW_SERVICE));
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        } catch (Exception e) {
            displayMetrics = null;
        }
        return displayMetrics;
    }

    /**
     * 注意一点: 如果 同时要获取 屏宽和屏高, 那么采用 getDisplayMetrics().
     * 调用本函数, 只适用于只想获取屏高的情况.
     */
    public static int getScreenHeight() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        if (displayMetrics == null) {
            return 0;
        } else {
            return displayMetrics.heightPixels;
        }
    }

    /**
     * 注意一点: 如果 同时要获取 屏宽和屏高, 那么采用 getDisplayMetrics().
     * 调用本函数, 只适用于只想获取屏宽的情况.
     */
    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        if (displayMetrics == null) {
            return 0;
        } else {
            return displayMetrics.widthPixels;
        }
    }


    /**
     * 获取app名称
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }


    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }
}
