package com.yslibrary.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Darcy https://yedaxia.github.io/
 * @version 2017/1/10
 */

public class FileUtils {

    public static void deleteFile(File... files) {
        for (int i = 0, length = files.length; i < length; i++) {
            if (null != files[i])
                files[i].delete();
        }
    }

    /**
     * 删除目录中的文件
     *
     * @param dir
     */
    public static void deleteDir(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] listFiles = dir.listFiles();
                for (File f : listFiles) {
                    deleteDir(f);
                }
            } else {
                dir.delete();
            }
        }
    }

    /**
     * 获取整个目录大小
     *
     * @param dir
     * @return 单位是byte
     */
    public static long getDirSize(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] listFiles = dir.listFiles();
                long size = 0;
                for (File f : listFiles) {
                    size += getDirSize(f);
                }
                return size;
            } else {
                return dir.length();
            }
        } else {
            return 0;
        }
    }

    /**
     * 压缩打包多个文件
     *
     * @param zipFile
     * @param files
     * @return
     */
    public static void compressFiles(File zipFile, File[] files) throws IOException {
        if (zipFile == null || ArrayUtils.isEmpty(files)) {
            return;
        }
        ZipOutputStream osZip = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32()));
        int bufferSize = 8192;
        for (File file : files) {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(file.getName());
            osZip.putNextEntry(entry);
            int count;
            byte data[] = new byte[bufferSize];
            while ((count = bis.read(data, 0, bufferSize)) != -1) {
                osZip.write(data, 0, count);
            }
            IOUtils.closeSilently(bis);
        }
        IOUtils.closeSilently(osZip);
    }

    public static String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            throw new IllegalArgumentException(
                    "Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(
                        c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }

    /**
     * 获取文件的后缀
     *
     * @param f
     * @return
     */
    public static String getFileExt(File f) {
        if (f == null) {
            return "";
        }

        return getFileExt(f.getName());
    }

    public static String getFileExt(String pathOrName) {
        if (pathOrName == null) {
            return "";
        }

        int idx = pathOrName.lastIndexOf(".");
        if (idx == -1) {
            return "";
        }

        return pathOrName.substring(idx + 1);
    }

    /**
     * 文件全名，包括后缀名
     *
     * @param pathOrName
     * @return
     */
    public static String getWholeFileName(String pathOrName) {
        if (pathOrName == null) {
            return "";
        }

        int idx = pathOrName.lastIndexOf(File.separator);
        if (idx == -1) {
            return pathOrName;
        }
        return pathOrName.substring(idx + 1);
    }

    /**
     * 文件名，不包括后缀名
     *
     * @param pathOrName
     * @return
     */
    public static String getFileNameWithoutExt(String pathOrName) {
        if (pathOrName == null) {
            return "";
        }

        String wholeFileName = getWholeFileName(pathOrName);
        int idx = wholeFileName.lastIndexOf(".");
        if (idx == -1) {
            return wholeFileName;
        }

        return wholeFileName.substring(0, idx);
    }

    public static void makePath(String filePath, boolean isDirectory) {
        File file = new File(filePath);
        if (isDirectory && !file.exists()) {
            file.mkdir();
        }

        if (!isDirectory && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

    }


    /**
     * 缓存文件夹
     *
     * @param context
     * @param name
     * @return
     */
    public static File getCertainCacheDir(Context context, String name) {
        return new File(context.getExternalCacheDir(), name);
    }

}
