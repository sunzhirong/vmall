package com.ysarch.vmall.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import cn.droidlover.xdroidmvp.log.XLog;

/**
 * Created by fysong on 23/01/2019.
 */

public class CacheHelper {

    private static final String CACHE_FILE_NAME = "app_cache";
    private static SharedPreferences sSharePreferences;

    public static void init(Application app) {
        sSharePreferences = app.getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        XLog.d("cache key=>" + key + ",  value=>" + value);
        sSharePreferences.edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return sSharePreferences.getString(key, null);
    }

    public static String getString(String key, String defValue) {
        return sSharePreferences.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        sSharePreferences.edit().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return sSharePreferences.getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return sSharePreferences.getInt(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        sSharePreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return sSharePreferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sSharePreferences.getBoolean(key, defValue);
    }

    public static void putLong(String key, long value) {
        sSharePreferences.edit().putLong(key, value).apply();
    }


    public static void putStrings(String key, Set<String> values) {
        sSharePreferences.edit().putStringSet(key, values).apply();
    }


    public static Set<String> getStrings(String key){
        return sSharePreferences.getStringSet(key, null);
    }

    public static long getLong(String key) {
        return sSharePreferences.getLong(key, 0);
    }

    public static long getLong(String key, long defValue) {
        return sSharePreferences.getLong(key, defValue);
    }

    public static boolean containsKey(String key) {
        return sSharePreferences.contains(key);
    }

    public static void removeKey(String key) {
        sSharePreferences.edit().remove(key).apply();
    }
}
