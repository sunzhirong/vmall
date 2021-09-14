package com.ysarch.vmall.common.context;

import com.ysarch.vmall.BuildConfig;

/**
 * Created by fysong on 14/08/2020
 **/
public class VMallBuildConfig {

    /**
     * 开发环境
     */
    public static final int ENV_DEV = 0x3;
    public static final String ENV_DEV_DESC = "dev";

    /**
     * 开发环境
     */
    public static final int ENV_DEV_HK = 0x2;
    public static final String ENV_DEV_HK_DESC = "dev_hk";
    /**
     * 开发环境
     */
    public static final int ENV_DEV_SZ = 0x1;
    public static final String ENV_DEV_SZ_DESC = "dev_sz";

    /**
     * 生产环境˙
     */
    public static final int ENV_PROD = 0x4;
    public static final String ENV_PROD_DESC = "prod";


    /**
     * release 打包方式无效
     */
    public static int CUR_ENV = ENV_DEV_SZ;



    public static void setEnv(int env){
        CUR_ENV = env;
    }

    /**
     * 是否测试环境
     *
     * @return
     */
    public static final boolean isDebugEnv() {
        if (isReleaseBuild()) {
            return false;
        }
        return CUR_ENV == ENV_DEV;
    }

    /**
     * 是否香港测试环境
     *
     * @return
     */
    public static final boolean isDebugHKEnv() {
        if (isReleaseBuild()) {
            return false;
        }
        return CUR_ENV == ENV_DEV_HK;
    }


    /**
     * 是否深圳测试环境
     *
     * @return
     */
    public static final boolean isDebugSZEnv() {
        if (isReleaseBuild()) {
            return false;
        }
        return CUR_ENV == ENV_DEV_SZ;
    }

    /**
     * 是否正式环境
     *
     * @return
     */
    public static final boolean isReleaseEnv() {
        return isReleaseBuild() || CUR_ENV == ENV_PROD;
    }

    /**
     * 是否以DEBUG方式出包
     *
     * @return
     */
    public static boolean isDebugBuild() {
        return "debug".equals(BuildConfig.BUILD_TYPE);
    }


    /**
     * 是否以RELEASE方式出包
     *
     * @return
     */
    public static boolean isReleaseBuild() {
        return "release".equals(BuildConfig.BUILD_TYPE);
    }


    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }
}
