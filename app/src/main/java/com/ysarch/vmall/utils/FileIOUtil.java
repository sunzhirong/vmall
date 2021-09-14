package com.ysarch.vmall.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by fysong on 11/03/2019.
 */

public class FileIOUtil {
    private static String CACHEDIR = "com/platform/android/uuid";


    public static boolean isExsitFile(Context context, String uniqueName) {
        if (uniqueName != null && !TextUtils.isEmpty(uniqueName)) {
            File externalFile = getDiskCacheDir(context, uniqueName);
            return externalFile.exists() && externalFile.isFile();
        } else {
            throw new RuntimeException("the uniqueName not be wallow null");
        }
    }

    public static boolean createExternalFile(Context context, String uniqueName) {
        if (uniqueName != null && !TextUtils.isEmpty(uniqueName)) {
            File externalFile = getDiskCacheDir(context, "");
            if (!externalFile.exists()) {
                externalFile.mkdirs();
            }

            String cacheFilePath = externalFile.getAbsolutePath() + File.separator + uniqueName;
            externalFile = new File(cacheFilePath);
            if (!externalFile.exists()) {
                try {
                    return externalFile.createNewFile();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }
            }

            return false;
        } else {
            throw new RuntimeException("the uniqueName not be wallow null");
        }
    }

    public static synchronized String readExternalFile(Context context, String uniqueName) {
        if (uniqueName != null && !TextUtils.isEmpty(uniqueName)) {
            File externalFile = getDiskCacheDir(context, uniqueName);

            try {
                BufferedReader br = new BufferedReader(new FileReader(externalFile));
                String readline = "";
                StringBuffer sb = new StringBuffer();

                while ((readline = br.readLine()) != null) {
                    sb.append(readline);
                }

                br.close();
                return sb.toString();
            } catch (Exception var6) {
                var6.printStackTrace();
                return null;
            }
        } else {
            throw new RuntimeException("the uniqueName not be wallow null");
        }
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = getExternalCacheDir(context).getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    @SuppressLint({"NewApi"})
    public static boolean isExternalStorageRemovable() {
        return Build.VERSION.SDK_INT >= 9 ? Environment.isExternalStorageRemovable() : true;
    }

    public static File getExternalCacheDir(Context context) {
        String cacheDir = "/" + CACHEDIR;
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static void writeExternalFile(Context context, String uniqueName, String content) {
        if (uniqueName != null && !TextUtils.isEmpty(uniqueName)) {
            File externalFile = getDiskCacheDir(context, uniqueName);

            try {
                FileOutputStream stream = new FileOutputStream(externalFile);
                stream.write(content.getBytes());
                stream.close();
            } catch (Exception var5) {
                var5.printStackTrace();
            }

        } else {
            throw new RuntimeException("the uniqueName not be wallow null");
        }
    }
}
