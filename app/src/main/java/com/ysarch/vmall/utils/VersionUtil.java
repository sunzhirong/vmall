package com.ysarch.vmall.utils;

/**
 * Created by fys on 2017/4/7.
 */

public class VersionUtil {

    /**
     * 检测是否需要更新版本（针对可能出现的缓存情况，获取到的线上版本号可能是低版本的）
     *
     * @param localVersionName
     * @param netVersionName
     * @return true: 需要更新
     */
    public static boolean checkVersion(String localVersionName, String netVersionName) {
        String[] curArr = localVersionName.split("[.]");
        String[] newArr = netVersionName.split("[.]");
        int len = Math.min(curArr.length, newArr.length);
        try {
            for (int i = 0; i < len; i++) {
                int p1 = Integer.parseInt(curArr[i]);
                int p2 = Integer.parseInt(newArr[i]);
                if (p2 == p1) {
                    continue;
                }
                return p1 < p2;
            }
            return curArr.length < newArr.length;
        } catch (Exception e) {

        }

        return false;
    }
}
