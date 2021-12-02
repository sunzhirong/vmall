package com.ysarch.vmall.domain;

import com.ysarch.vmall.common.context.VMallBuildConfig;

/**
 * Created by fysong on 2020-07-11
 **/
public class ApiUrl {

//    public static final String DEV_HOST = "39.98.50.168:8085";
//    public static final String DEV_HK_HOST = "8.210.36.207:8085";
//    public static final String RELEASE_HOST = "39.98.50.168:8085";
    public static final String DEV_HOST_SZ = "47.112.237.160:8085";


//    public static String getDevHost() {
//        return "8.210.36.207:8085";
//    }
//
//    public static String getPrivacyPolicy() {
//        return "http://www.danding.fun/privacyPolicy.html";
//    }

    public static String getBaseApiUrl() {
//        if (VMallBuildConfig.isDebugBuild()) {
//            if (VMallBuildConfig.isDebugEnv()) {
//                return "http://" + DEV_HOST + "/";
//            } else if (VMallBuildConfig.isDebugHKEnv()) {
//                return "http://" + DEV_HK_HOST + "/";
//            }else if (VMallBuildConfig.isDebugSZEnv()) {
//                return "http://" + DEV_HOST_SZ + "/";
//            }
//        }
//        return "http://" + RELEASE_HOST + "/";

        return "http://" + DEV_HOST_SZ + "/";
    }
}
