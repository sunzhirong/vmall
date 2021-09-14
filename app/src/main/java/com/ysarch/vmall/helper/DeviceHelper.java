package com.ysarch.vmall.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.ysarch.vmall.domain.constant.CacheKeys;
import com.yslibrary.utils.DIDMD5Util;
import com.yslibrary.utils.DeviceUtil;
import com.ysarch.vmall.utils.FileIOUtil;

import java.util.UUID;

/**
 * Created by fysong on 11/03/2019.
 */

public class DeviceHelper {

    public static final String uuidFile = "uuid_value.txt";
    private static String sDeviceId;
    private static final String SALT = "cat09&^%G@y$";


    public static String getDeviceId(Context context) {
        if (TextUtils.isEmpty(sDeviceId)) {
            try {
                if (FileIOUtil.isExsitFile(context, uuidFile)) {
                    sDeviceId = FileIOUtil.readExternalFile(context, uuidFile);
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }


            if (sDeviceId == null || sDeviceId.equals("")) {
                sDeviceId = CacheHelper.getString(CacheKeys.KEY_DEVICE_ID,"");
                if (!TextUtils.isEmpty(sDeviceId)) {
                    try {
                        if (FileIOUtil.createExternalFile(context, uuidFile)) {
                            FileIOUtil.writeExternalFile(context, uuidFile, sDeviceId);
                        }
                    } catch (Exception var3) {
                        var3.printStackTrace();
                    }
                }
            }


            if (sDeviceId == null || sDeviceId.equals("")) {
                sDeviceId = DeviceUtil.getDeviceId(context);
                Log.d("DID IMEI", "sDeviceId imei : " + sDeviceId);
                if (sDeviceId == null || sDeviceId.contains("0000000") || sDeviceId.equals("")) {
                    sDeviceId = "" + UUID.randomUUID();
                    sDeviceId = DIDMD5Util.MD5(DIDMD5Util.MD5(sDeviceId) + SALT);
                    sDeviceId = sDeviceId.toUpperCase();
                    Log.d("DID randomUUID", "sDeviceId randomUUID : " + sDeviceId);
                }

                try {
                    if (FileIOUtil.createExternalFile(context, uuidFile)) {
                        FileIOUtil.writeExternalFile(context, uuidFile, sDeviceId);
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

                CacheHelper.putString(CacheKeys.KEY_DEVICE_ID, sDeviceId);
            }

        }
        return sDeviceId;
    }
}
