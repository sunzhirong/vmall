package org.devio.takephoto.uitl;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Author: crazycodeboy
 * Date: 2016/11/5 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org/
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class TFileUtils {
    private static final String TAG = "TFileUtils";
    private static String DEFAULT_DISK_CACHE_DIR = "takephoto_cache";

    public static File getPhotoCacheDir(Context context, File file) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File mCacheDir = new File(cacheDir, DEFAULT_DISK_CACHE_DIR);
            if (!mCacheDir.mkdirs() && (!mCacheDir.exists() || !mCacheDir.isDirectory())) {
                return file;
            } else {
                return new File(mCacheDir, file.getName());
            }
        }
        if (Log.isLoggable(TAG, Log.ERROR)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return file;
    }

    public static void delete(String path) {
        try {
            if (path == null) {
                return;
            }
            File file = new File(path);
            if (!file.delete()) {
                file.deleteOnExit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    fun Uri.toFile(context: Context): File? = when (scheme) {
//        ContentResolver.SCHEME_FILE -> toFile()
//        ContentResolver.SCHEME_CONTENT -> {
//            val cursor = context.contentResolver.query(this, null, null, null, null)
//            val file = cursor?.let {
//                if (it.moveToFirst()) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        //保存到本地
//                        val ois = context.contentResolver.openInputStream(this)
//                        val displayName =
//                                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                        ois?.let {
//                            val file = File(
//                                    context.externalCacheDir!!.absolutePath,
//                                    "${Random.nextInt(0, 9999)}$displayName"
//                        )
//                            val fos = FileOutputStream(file)
//                            android.os.FileUtils.copy(ois, fos)
//                            fos.close()
//                            it.close()
//                            file
//                        }
//                    } else
//                        //直接转换
//                        File(it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA)))
//                } else null
//
//            }
//            cursor?.close()
//            file
//        }
//    else -> null
//    }
}
