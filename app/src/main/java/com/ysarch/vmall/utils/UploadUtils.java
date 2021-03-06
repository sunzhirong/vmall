package com.ysarch.vmall.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadUtils {
    public static Map<String,Object> getUploadRequest(){
        Map<String,Object> deviceBaseInfoMap = new HashMap<String,Object>();
        deviceBaseInfoMap.put("appVersion",BuildConfig.VERSION_NAME);
        TelephonyManager manager = (TelephonyManager)VMallApplication.sApplication.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getSimOperatorName();
        deviceBaseInfoMap.put("carrier",carrierName);
        deviceBaseInfoMap.put("language", AppContext.getsInstance().getLanguageEntity().getShortCut());
        deviceBaseInfoMap.put("manufacturer",DeviceUtil.getDeviceName());
        if(UserInfoManager.getUser()==null){
            deviceBaseInfoMap.put("memberId",0);
        }else {
            deviceBaseInfoMap.put("memberId",UserInfoManager.getUser().getId());
        }
        deviceBaseInfoMap.put("model",DeviceUtil.getDeviceModel());
        deviceBaseInfoMap.put("os","Android");
        deviceBaseInfoMap.put("osVersion", Build.VERSION.RELEASE);
        deviceBaseInfoMap.put("screenHeight",DeviceUtil.getScreenHeight());
        deviceBaseInfoMap.put("screenWidth",DeviceUtil.getScreenWidth());
        deviceBaseInfoMap.put("wifi",DeviceUtil.isWifiConnected(VMallApplication.sApplication));

        return deviceBaseInfoMap;
    }


}
