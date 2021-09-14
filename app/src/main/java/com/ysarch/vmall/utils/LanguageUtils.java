package com.ysarch.vmall.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;


import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;

import java.util.Locale;

/**
 * 多语言工具类
 * Created by Fitem on 2020/03/20.
 */

public class LanguageUtils {

    public static final String TAG = "LanguageUtils";
    public static final Locale KH = new Locale("kh");
    public static final Locale EN = new Locale("en");
    public static final String KEY = "store_language";

    /**
     * gradle 中配置值区分不同版本，发布时需要修改配置值
     */
    public static final int COUNTRY_VERSION_KH = 1;// 印尼版本
    public static final int COUNTRY_VERSION_EN = 2;// 马来版本


    /**
     * 设置 app 语言，兼容 8.0 及以上系统
     *
     * @param context
     * @return
     */
    public static Context setAppLocalLanguage(Context context) {
        Locale locale = getUserSetLanguageLocal(context);
        return updateConfiguration(context, locale);
    }

    /**
     * @param context
     * @param locale
     * @return
     */
    private static Context updateConfiguration(Context context, Locale locale) {
        if (context == null) {
            return null;
        }
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 更新 application 的 updateConfiguration,否则 context.getResource.getString
     * 中 当 context 为applicationContext 时不会生效
     *
     * @param context
     */
    public static void updateApplicationConfiguration(Context context, Locale locale) {
        if (context == null) {
            return;
        }
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    /**
     * 获取用户设置的语言 locale
     *
     * @return
     */
    public static Locale getUserSetLanguageLocal(Context context) {
        return Locale.forLanguageTag(getUserSetLanguageTag(context));
    }

    /**
     * 获取用户设置的语言 tag
     *
     * @return
     */
    public static String getUserSetLanguageTag(Context context) {
        if (context == null) {
//            LogUtils.e(KEY, "context is empty!");
            return "";
        }
//        return SharePreferenceUtils.getString(context, KEY, getLanguageLocaleFromGradle().toLanguageTag());
        return CacheHelper.getString(CacheKeys.KEY_LANGUAGE,"en");
    }

    /**
     * 从 gradle 中根据国家配置获取 locale 值
     *
     * @return
     */
//    public static Locale getLanguageLocaleFromGradle() {
//        Locale locale = Locale.ENGLISH;
//        switch (com.shopee.fms.BuildConfig.countryVersion) {
//            case COUNTRY_VERSION_KH:
//                locale = KH;
//                break;
//            case COUNTRY_VERSION_EN:
//                locale = EN;
//                break;
//            default:
//                break;
//        }
//        return locale;
//    }

    /**
     * 保存用户设置的语言 tag
     *
     * @param locale
     */
    public static void saveLanguage(Context context, Locale locale) {
        if (locale == null) {
//            LogUtils.e(KEY, "locale is empty!");
            return;
        }
//        SharePreferenceUtils.putString(context, KEY, locale.toLanguageTag());
        updateApplicationConfiguration(context, locale);
    }

    public static void saveSystemCurrentLanguage(Context context) {
        Locale userSetLocale = getUserSetLanguageLocal(context);
//        if (userSetLocale == null) {
//            Locale defaultLocale = getLanguageLocaleFromGradle();
//            CacheHelper.putString(context, KEY, defaultLocale.toLanguageTag());
//        }
    }

    /**
     * 当用户手机切换语言时，不跟随
     *
     * @param context
     */
    public static void onConfigurationChanged(Context context) {
        Locale locale = getUserSetLanguageLocal(context);
        updateConfiguration(context, locale);
        updateApplicationConfiguration(context, locale);
    }

}
