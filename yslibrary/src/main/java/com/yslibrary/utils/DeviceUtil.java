package com.yslibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by fysong on 22/11/2018.
 */

public class DeviceUtil {
    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        String deviceId;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            deviceId = "";
        }

        return deviceId;
    }

    /**
     * 得到当前系统语言
     */
    public static String getLanguage(Context context) {
        String language = "NO Search";
        String country = context.getResources().getConfiguration().locale
                .getCountry();
        language = context.getResources().getConfiguration().locale
                .getLanguage();
        // 区分简体和繁体中文
        if (language.equals("zh")) {
            if (country.equals("CN")) {
                language = "Simplified Chinese";
            } else {
                language = "Traditional Chinese";
            }
        }
        return language;
    }

    /**
     * 得到当前系统国家和地区
     */
    public static String getCountry(Context context) {
        String country = "NO Search";
        country = context.getResources().getConfiguration().locale.getCountry();
        return country;
    }

    /**
     * 得到设备mac地址
     */
    public static String getMAC(Context context) {
//        String mac = "NO Search";
//        WifiManager manager = (WifiManager) context
//                .getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        mac = info.getMacAddress();
//        return mac;
        InetAddress inetAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        break;
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        String mac_s = "";
        if (inetAddress != null) {
            try {
                byte[] mac;
                NetworkInterface ne =
                        NetworkInterface.getByInetAddress(inetAddress);
                mac = ne.getHardwareAddress();

                StringBuilder buf = new StringBuilder();
                if (mac != null) {
                    for (byte b : mac) {
                        buf.append(String.format("%02X:", b));
                    }
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                mac_s = buf.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mac_s;
    }


    /**
     * 获得手机sim号
     */
    @SuppressLint("MissingPermission")
    public static String getSIM(Context context) {
        String sim = "NO Search";

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        sim = telephonyManager.getSimSerialNumber();

        return sim;
    }


    /**
     * 得到手机产品序列号
     */
    public static String getSN() {
        String sn = "NO Search";
        String serial = android.os.Build.SERIAL;// 第二种得到序列号的方法
//        try {
//            Class<?> c = Class.forName("android.os.SystemProperties");
//            Method get = c.getMethod("get", String.class);
//            sn = (String) get.invoke(c, "ro.serialno");
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
        return serial;
    }

    /**
     * 权限READ_PHONE_STATE
     * 获得imsi号
     */
    @SuppressLint("MissingPermission")
    public static String getIMSI(Context context) {
        String imsi = "NO Search";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imsi = telephonyManager.getSubscriberId();
        return imsi;
    }

    /**
     * imei
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        try {
            @SuppressLint("WrongConstant") TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            return tm.getDeviceId();
        } catch (Exception var2) {
            var2.printStackTrace();

        }
        return "nothing";
    }


    public static String getNetIP(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
        return ip;
    }


    @SuppressLint("MissingPermission")
    public static Location getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location == null) {
                continue;
            }
            if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = location;
            }
        }
        return bestLocation;
    }

}
