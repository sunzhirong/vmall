package com.ysarch.vmall.language;

import android.content.Context;
import android.content.res.Configuration;


import com.github.jokar.multilanguages.library.MultiLanguage;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.utils.Log;

import java.util.Locale;

public class LocalManageUtil {

    private static final String TAG = "LocalManageUtil";

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context) {
        return SPUtil.getInstance(context).getSystemCurrentLocal();
    }

    public static String getSelectLanguage(Context context) {
        switch (SPUtil.getInstance(context).getSelectLanguage()) {
            case 0:
                return context.getString(R.string.label_lan_english);
            case 1:
                return context.getString(R.string.label_lan_english);
            case 2:
                return context.getString(R.string.label_lan_cambodia);
            case 3:
                return context.getString(R.string.label_lan_chinese);
            default:
                return context.getString(R.string.label_lan_english);
        }
    }

    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {
//        Log.e("language111",SPUtil.getInstance(context).getSelectLanguage()+"");
//        return  MultiLanguage.getSystemLocal(context);
        switch (SPUtil.getInstance(context).getSelectLanguage()) {
//        switch (AppContext.getsInstance().getLanguageEntity().getLanId()) {
            case 0:
                return Locale.ENGLISH;
            case 1:
                return new Locale("kh");
            case 2:
                return Locale.CHINA;
//            case 3:
//                return Locale.ENGLISH;
            default:
                return Locale.ENGLISH;
        }

    }


    public static void saveSystemCurrentLanguage(Context context) {
        SPUtil.getInstance(context).setSystemCurrentLocal(MultiLanguage.getSystemLocal(context));
    }

    /**
     * 保存系统语言
     * @param context
     * @param newConfig
     */
    public static void saveSystemCurrentLanguage(Context context, Configuration newConfig) {
        SPUtil.getInstance(context).setSystemCurrentLocal(MultiLanguage.getSystemLocal(newConfig));
    }

    public static void saveSelectLanguage(Context context, int select) {
        SPUtil.getInstance(context).saveLanguage(select);
        MultiLanguage.setApplicationLanguage(context);
    }
}
