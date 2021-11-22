package com.ysarch.vmall.utils;

import com.ysarch.vmall.common.context.UserInfoManager;

import java.util.HashMap;
import java.util.Map;

public class UploadUtils {
    public static Map<String,String> getUploadRequest(){
        Map<String,String> map = new HashMap<String,String>();
        String userid = "";
        if(UserInfoManager.isLogin()) {
            userid = String.valueOf(UserInfoManager.getUser().getId());
        }
        map.put("distinct_id",userid);
//        map.put("langage",);



        return map;
    }
}
