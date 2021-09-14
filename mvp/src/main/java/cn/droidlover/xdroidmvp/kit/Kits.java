package cn.droidlover.xdroidmvp.kit;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.droidlover.xdroidmvp.R;
import cn.droidlover.xdroidmvp.log.XLog;

/**
 * Created by wanglei on 2016/11/28.
 */

public class Kits {

    public static class Package {


        /**
         * 打开第三方app
         *
         * @param context
         * @param packageName
         * @return
         */
        public static void openOtherApp(Context context, String packageName, String errMsg) {
            errMsg = "该应用不存在，无法打开";
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
                context.startActivity(intent);
            } else {
                // 没有安装要跳转的app应用，提醒一下
                Toast.makeText(context, errMsg, Toast.LENGTH_LONG).show();
            }
        }


        /**
         * 判断手机是不是有这个应用存在
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean checkApkExist(Context context, String packageName) {
            if (TextUtils.isEmpty(packageName))
                return false;
            try {
                ApplicationInfo info = context.getPackageManager()
                        .getApplicationInfo(packageName,
                                PackageManager.GET_UNINSTALLED_PACKAGES);
                XLog.e("the app info is" + info);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        /**
         * 检查是否有版本更新
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean checkUpdate(Context context, String versionCode, String packageName) {
            if (checkApkExist(context, packageName)) {
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                    int tarVersionCode = Integer.valueOf(versionCode);
                    int currVersionCode = packageInfo.versionCode;
                    return tarVersionCode > currVersionCode;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        }

        /**
         * 判断手机是否安装某个应用
         *
         * @param context
         * @param appPackageName 应用包名
         * @return true：安装，false：未安装
         */
        public static boolean isApplicationAvilible(Context context, String appPackageName) {
            PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (appPackageName.equals(pn)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 获取版本号
         *
         * @param context
         * @return
         */
        public static int getVersionCode(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionCode;
        }

        /**
         * 获取当前版本
         *
         * @param context
         * @return
         */
        public static String getVersionName(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionName;
        }

        /**
         * 安装App
         *
         * @param context
         * @param apkPath
         * @return
         */
        public static boolean installNormal(Context context, String packName, String apkPath) {
            XLog.e("安装app:包名" + packName);
            XLog.e("安装app:apkPath" + apkPath);
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //判断是否是AndroidN以及更高的版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context, packName + ".fileprovider", new java.io.File(apkPath));
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(new java.io.File(apkPath)), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 卸载App
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean uninstallNormal(Context context, String packageName) {
            if (packageName == null || packageName.length() == 0) {
                return false;
            }

            Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder().append("package:")
                    .append(packageName).toString()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 判断是否是系统App
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        public static boolean isSystemApplication(Context context, String packageName) {
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || packageName == null || packageName.length() == 0) {
                return false;
            }

            try {
                ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
                return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 判断某个包名是否运行在顶层
         *
         * @param context
         * @param packageName
         * @return
         */
        public static Boolean isTopActivity(Context context, String packageName) {
            if (context == null || TextUtils.isEmpty(packageName)) {
                return null;
            }

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo == null || tasksInfo.isEmpty()) {
                return null;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 获取Meta-Data
         *
         * @param context
         * @param key
         * @return
         */
        public static String getAppMetaData(Context context, String key) {
            if (context == null || TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return resultData;
        }

        /**
         * 判断当前应用是否运行在后台
         *
         * @param context
         * @return
         */
        public static boolean isApplicationInBackground(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                return topActivity != null && !topActivity.getPackageName().equals(context.getPackageName());
            }
            return false;
        }
    }


    public static class Dimens {
        public static float dpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

        public static float pxToDp(Context context, float px) {
            return px / context.getResources().getDisplayMetrics().density;
        }

        public static int dpToPxInt(Context context, float dp) {
            return (int) (dpToPx(context, dp) + 0.5f);
        }

        public static int pxToDpCeilInt(Context context, float px) {
            return (int) (pxToDp(context, px) + 0.5f);
        }
    }


    public static class Random {
        public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS = "0123456789";
        public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        public static String getRandomNumbers(int length) {
            return getRandom(NUMBERS, length);
        }

        public static String getRandomLetters(int length) {
            return getRandom(LETTERS, length);
        }

        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        public static String getRandom(String source, int length) {
            return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        public static String getRandom(char[] sourceChar, int length) {
            if (sourceChar == null || sourceChar.length == 0 || length < 0) {
                return null;
            }

            StringBuilder str = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                str.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return str.toString();
        }

        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        public static int getRandom(int min, int max) {
            if (min > max) {
                return 0;
            }
            if (min == max) {
                return min;
            }
            return min + new java.util.Random().nextInt(max - min);
        }
    }

    public static class File {
        public final static String FILE_EXTENSION_SEPARATOR = ".";

        /**
         * read file
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static StringBuilder readFile(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            StringBuilder fileContent = new StringBuilder();
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param content
         * @param append   is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if content is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, String content, boolean append) {
            if (TextUtils.isEmpty(content)) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                fileWriter.write(content);
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param contentList
         * @param append      is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if contentList is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
            if (contentList == null || contentList.isEmpty()) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                int i = 0;
                for (String line : contentList) {
                    if (i++ > 0) {
                        fileWriter.write("\r\n");
                    }
                    fileWriter.write(line);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file, the string will be written to the begin of the file
         *
         * @param filePath
         * @param content
         * @return
         */
        public static boolean writeFile(String filePath, String content) {
            return writeFile(filePath, content, false);
        }

        /**
         * write file, the string list will be written to the begin of the file
         *
         * @param filePath
         * @param contentList
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList) {
            return writeFile(filePath, contentList, false);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param filePath
         * @param stream
         * @return
         * @see {@link #writeFile(String, InputStream, boolean)}
         */
        public static boolean writeFile(String filePath, InputStream stream) {
            return writeFile(filePath, stream, false);
        }

        /**
         * write file
         *
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(String filePath, InputStream stream, boolean append) {
            return writeFile(filePath != null ? new java.io.File(filePath) : null, stream, append);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param file
         * @param stream
         * @return
         * @see {@link #writeFile(java.io.File, InputStream, boolean)}
         */
        public static boolean writeFile(java.io.File file, InputStream stream) {
            return writeFile(file, stream, false);
        }

        /**
         * write file
         *
         * @param file   the file to be opened for writing.
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(java.io.File file, InputStream stream, boolean append) {
            OutputStream o = null;
            try {
                makeDirs(file.getAbsolutePath());
                o = new FileOutputStream(file, append);
                byte[] data = new byte[1024];
                int length = -1;
                while ((length = stream.read(data)) != -1) {
                    o.write(data, 0, length);
                }
                o.flush();
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(o);
                IO.close(stream);
            }
        }

        /**
         * move file
         *
         * @param sourceFilePath
         * @param destFilePath
         */
        public static void moveFile(String sourceFilePath, String destFilePath) {
            if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
                throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
            }
            moveFile(new java.io.File(sourceFilePath), new java.io.File(destFilePath));
        }

        /**
         * move file
         *
         * @param srcFile
         * @param destFile
         */
        public static void moveFile(java.io.File srcFile, java.io.File destFile) {
            boolean rename = srcFile.renameTo(destFile);
            if (!rename) {
                copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                deleteFile(srcFile.getAbsolutePath());
            }
        }

        /**
         * copy file
         *
         * @param sourceFilePath
         * @param destFilePath
         * @return
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            }
            return writeFile(destFilePath, inputStream);
        }

        /**
         * read file to string list, a element of list is a line
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static List<String> readFileToList(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            List<String> fileContent = new ArrayList<String>();
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * get file name from path, not include suffix
         * <p/>
         * <pre>
         *      getFileNameWithoutExtension(null)               =   null
         *      getFileNameWithoutExtension("")                 =   ""
         *      getFileNameWithoutExtension("   ")              =   "   "
         *      getFileNameWithoutExtension("abc")              =   "abc"
         *      getFileNameWithoutExtension("a.mp3")            =   "a"
         *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
         *      getFileNameWithoutExtension("c:\\")              =   ""
         *      getFileNameWithoutExtension("c:\\a")             =   "a"
         *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
         *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
         *      getFileNameWithoutExtension("/home/admin")      =   "admin"
         *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
         * </pre>
         *
         * @param filePath
         * @return file name from path, not include suffix
         * @see
         */
        public static String getFileNameWithoutExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (filePosi == -1) {
                return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            }
            return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
        }

        /**
         * get file name from path, include suffix
         * <p/>
         * <pre>
         *      getFileName(null)               =   null
         *      getFileName("")                 =   ""
         *      getFileName("   ")              =   "   "
         *      getFileName("a.mp3")            =   "a.mp3"
         *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
         *      getFileName("abc")              =   "abc"
         *      getFileName("c:\\")              =   ""
         *      getFileName("c:\\a")             =   "a"
         *      getFileName("c:\\a.b")           =   "a.b"
         *      getFileName("c:a.txt\\a")        =   "a"
         *      getFileName("/home/admin")      =   "admin"
         *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
         * </pre>
         *
         * @param filePath
         * @return file name from path, include suffix
         */
        public static String getFileName(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
        }

        /**
         * get folder name from path
         * <p/>
         * <pre>
         *      getFolderName(null)               =   null
         *      getFolderName("")                 =   ""
         *      getFolderName("   ")              =   ""
         *      getFolderName("a.mp3")            =   ""
         *      getFolderName("a.b.rmvb")         =   ""
         *      getFolderName("abc")              =   ""
         *      getFolderName("c:\\")              =   "c:"
         *      getFolderName("c:\\a")             =   "c:"
         *      getFolderName("c:\\a.b")           =   "c:"
         *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
         *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
         *      getFolderName("/home/admin")      =   "/home"
         *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFolderName(String filePath) {

            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
        }

        /**
         * get suffix of file from path
         * <p/>
         * <pre>
         *      getFileExtension(null)               =   ""
         *      getFileExtension("")                 =   ""
         *      getFileExtension("   ")              =   "   "
         *      getFileExtension("a.mp3")            =   "mp3"
         *      getFileExtension("a.b.rmvb")         =   "rmvb"
         *      getFileExtension("abc")              =   ""
         *      getFileExtension("c:\\")              =   ""
         *      getFileExtension("c:\\a")             =   ""
         *      getFileExtension("c:\\a.b")           =   "b"
         *      getFileExtension("c:a.txt\\a")        =   ""
         *      getFileExtension("/home/admin")      =   ""
         *      getFileExtension("/home/admin/a.txt/b")  =   ""
         *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFileExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (extenPosi == -1) {
                return "";
            }
            return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
        }

        /**
         * Creates the directory named by the trailing filename of this file, including the complete directory path required
         * to create this directory. <br/>
         * <br/>
         * <ul>
         * <strong>Attentions:</strong>
         * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
         * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
         * </ul>
         *
         * @param filePath
         * @return true if the necessary directories have been created or the target directory already exists, false one of
         * the directories can not be created.
         * <ul>
         * <li>if {@link File#getFolderName(String)} return null, return false</li>
         * <li>if target directory already exists, return true</li>
         * </ul>
         */
        public static boolean makeDirs(String filePath) {
            String folderName = getFolderName(filePath);
            if (TextUtils.isEmpty(folderName)) {
                return false;
            }

            java.io.File folder = new java.io.File(folderName);
            return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
        }

        /**
         * @param filePath
         * @return
         * @see #makeDirs(String)
         */
        public static boolean makeFolders(String filePath) {
            return makeDirs(filePath);
        }

        /**
         * Indicates if this file represents a file on the underlying file system.
         *
         * @param filePath
         * @return
         */
        public static boolean isFileExist(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return false;
            }

            java.io.File file = new java.io.File(filePath);
            return (file.exists() && file.isFile());
        }

        /**
         * Indicates if this file represents a directory on the underlying file system.
         *
         * @param directoryPath
         * @return
         */
        public static boolean isFolderExist(String directoryPath) {
            if (TextUtils.isEmpty(directoryPath)) {
                return false;
            }

            java.io.File dire = new java.io.File(directoryPath);
            return (dire.exists() && dire.isDirectory());
        }

        /**
         * delete file or directory
         * <ul>
         * <li>if path is null or empty, return true</li>
         * <li>if path not exist, return true</li>
         * <li>if path exist, delete recursion. return true</li>
         * <ul>
         *
         * @param path
         * @return
         */
        public static boolean deleteFile(String path) {
            if (TextUtils.isEmpty(path)) {
                return true;
            }

            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (java.io.File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            return file.delete();
        }

        /**
         * get file size
         * <ul>
         * <li>if path is null or empty, return -1</li>
         * <li>if path exist and it is a file, return file size, else return -1</li>
         * <ul>
         *
         * @param path
         * @return returns the length of this file in bytes. returns -1 if the file does not exist.
         */
        public static long getFileSize(String path) {
            if (TextUtils.isEmpty(path)) {
                return -1;
            }

            java.io.File file = new java.io.File(path);
            return (file.exists() && file.isFile() ? file.length() : -1);
        }

    }

    public static class IO {
        /**
         * 关闭流
         *
         * @param closeable
         */
        public static void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    public static class Date {
        private static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
        private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
        private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
        private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        private static SimpleDateFormat ymdCn = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        private static SimpleDateFormat ymdhmCn = new SimpleDateFormat("yyyy年MM月dd日HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());


        public static String timeStampForPHP(java.util.Date date) {
            if (date == null) return null;
            return "" + (date.getTime() / 1000);
        }

        public static Long timeStampFromPHP(String timePHP) {
            if (timePHP == null) return 0l;

            return Long.parseLong(timePHP) * 1000;
        }

//        public static long parseDate(java.util.Date date) {
//            try {
//                return date.getTime();
//            } catch (Exception e) {
//                return 0;
//            }
//        }

        public static long parseYmdhms(String date) {
            try {
                return ymdhms.parse(date).getTime();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        public static long parseYmdDot(String date) {
            try {
                return ymdDot.parse(date).getTime();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        /**
         * 年月日[2015-07-28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmd(long timeInMills) {
            return ymd.format(new java.util.Date(timeInMills));
        }

        public static String getYmd(String timeInMills) {
            return ymd.format(timeStampFromPHP(timeInMills));
        }

        public static String getYmd(java.util.Date timeInMills) {
            return ymd.format(timeInMills);
        }

        /**
         * 年月日[2015.07.28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmdDot(long timeInMills) {
            return ymdDot.format(new java.util.Date(timeInMills));
        }

        public static String getYmdDot(java.util.Date timeInMills) {
            return ymdDot.format(timeInMills);
        }

        public static String getYmdhms(long timeInMills) {
            return ymdhms.format(new java.util.Date(timeInMills));
        }

        public static String getYmdCn(long timeInMills) {
            return ymdCn.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhmCn(long timeInMills) {
            return ymdhmCn.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhmsS(long timeInMills) {
            return ymdhmss.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhm(long timeInMills) {
            return ymdhm.format(new java.util.Date(timeInMills));
        }

        public static String getHm(long timeInMills) {
            return hm.format(new java.util.Date(timeInMills));
        }

        public static String getMd(long timeInMills) {
            return md.format(new java.util.Date(timeInMills));
        }

        public static String getMdhm(long timeInMills) {
            return mdhm.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink(long timeInMills) {
            return mdhmLink.format(new java.util.Date(timeInMills));
        }

        public static String getM(long timeInMills) {
            return m.format(new java.util.Date(timeInMills));
        }

        public static String getD(long timeInMills) {
            return d.format(new java.util.Date(timeInMills));
        }

        /**
         * 是否是今天
         *
         * @param timeInMills
         * @return
         */
        public static boolean isToday(long timeInMills) {
            String dest = getYmd(timeInMills);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是不是同一天
         *
         * @param dateStr
         * @return
         */
        public static boolean isToDay(String dateStr) {
            parseYmdDot(dateStr);
            return isToday(parseYmdDot(dateStr));
        }

        /**
         * 是否是同一天
         *
         * @param aMills
         * @param bMills
         * @return
         */
        public static boolean isSameDay(long aMills, long bMills) {
            String aDay = getYmd(aMills);
            String bDay = getYmd(bMills);
            return aDay.equals(bDay);
        }

        /**
         * 获取年份
         *
         * @param mills
         * @return
         */
        public static int getYear(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         *
         * @param mills
         * @return
         */
        public static int getMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.MONTH) + 1;
        }


        /**
         * 获取月份的天数
         *
         * @param mills
         * @return
         */
        public static int getDaysInMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }


        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         *
         * @param mills
         * @return
         */
        public static int getWeek(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        public static int getWeek(java.util.Date date) {
            return getWeek(parseYmdDot(getYmdDot(date)));
        }

        /**
         * 获取当月第一天的时间（毫秒值）
         *
         * @param mills
         * @return
         */
        public static long getFirstOfMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            return calendar.getTimeInMillis();
        }


        /**
         * String的年月日 转 date
         *
         * @return
         */
        public static java.util.Date calendarToData(String dateStr) {
            String[] dateStrs = dateStr.split("\\.");
            if (dateStrs.length == 3) {
                int year = Integer.valueOf(dateStrs[0]);
                int month = Integer.valueOf(dateStrs[1]);
                int day = Integer.valueOf(dateStrs[2]);
                Calendar calendar = Calendar.getInstance();//日历类的实例化
                calendar.set(year, month - 1, day);//设置日历时间，月份必须减一
                java.util.Date date = calendar.getTime(); // 从一个 Calendar 对象中获取 Date 对象
                return date;
            } else {
                return null;
            }
        }

        /**
         * Date 转 Calendar
         *
         * @param date
         * @return
         */
        public static Calendar date2Calendar(java.util.Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }

        /**
         * 年月日转 Calendar
         *
         * @param dateStr 只能试别用.分割的
         * @return
         */
        public static Calendar DateStr2Calendar(String dateStr) {
            Calendar calendar = null;
            java.util.Date date = calendarToData(dateStr);
            if (date != null) {
                calendar = date2Calendar(date);
            }
            return calendar;
        }

        /**
         * 获取前一周的日子
         *
         * @return
         */
        public static List<String> getForwardWeek(Calendar c) {
            List<String> titles = new ArrayList<String>();
            String mYear; // 当前年
            String mMonth; // 月
            String mDay;
            int current_day;
            int current_month;
            int current_year;
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            current_day = c.get(Calendar.DAY_OF_MONTH);
            current_month = c.get(Calendar.MONTH);
            current_year = c.get(Calendar.YEAR);
            for (int i = 0; i < 7; i++) {
                c.clear();//记住一定要clear一次
                c.set(Calendar.MONTH, current_month);
                c.set(Calendar.DAY_OF_MONTH, current_day);
                c.set(Calendar.YEAR, current_year);
                c.add(Calendar.DATE, -i);//j记住是DATE
                mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
                mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
                mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
                String date = mYear + "." + mMonth + "." + mDay;
                titles.add(date);
            }
            return titles;
        }

        /**
         * 获取后一个礼拜
         *
         * @return
         */
        public static List<String> getFutureWeek(Calendar c) {
            List<String> titles = new ArrayList<String>();
            String mYear; // 当前年
            String mMonth; // 月
            String mDay;
            int current_day;
            int current_month;
            int current_year;

            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            current_day = c.get(Calendar.DAY_OF_MONTH);
            current_month = c.get(Calendar.MONTH);
            current_year = c.get(Calendar.YEAR);
            for (int i = 0; i < 7; i++) {
                c.clear();//记住一定要clear一次
                c.set(Calendar.MONTH, current_month);
                c.set(Calendar.DAY_OF_MONTH, current_day);
                c.set(Calendar.YEAR, current_year);
                c.add(Calendar.DATE, +i);//j记住是DATE
                mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
                mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前日份的日期号码
                mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
                String date = mYear + "." + mMonth + "." + mDay;
                titles.add(date);
            }
            Collections.reverse(titles);
            return titles;
        }
    }


    public static class NetWork {
        public static final String NETWORK_TYPE_WIFI = "wifi";
        public static final String NETWORK_TYPE_3G = "eg";
        public static final String NETWORK_TYPE_2G = "2g";
        public static final String NETWORK_TYPE_WAP = "wap";
        public static final String NETWORK_TYPE_UNKNOWN = "unknown";
        public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

        public static int getNetworkType(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
            return networkInfo == null ? -1 : networkInfo.getType();
        }

        /**
         * 是不是wifi
         *
         * @param context
         * @return
         */
        public static boolean isWifi(Context context) {
            return NETWORK_TYPE_WIFI.equals(getNetworkTypeName(context));
        }

        public static String getNetworkTypeName(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo;
            String type = NETWORK_TYPE_DISCONNECT;
            if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
                return type;
            }

            if (networkInfo.isConnected()) {
                String typeName = networkInfo.getTypeName();
                if ("WIFI".equalsIgnoreCase(typeName)) {
                    type = NETWORK_TYPE_WIFI;
                } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                            : NETWORK_TYPE_WAP;
                } else {
                    type = NETWORK_TYPE_UNKNOWN;
                }
            }
            return type;
        }

        private static boolean isFastMobileNetwork(Context context) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return false;
            }

            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true;
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        }

    }


    public static class Empty {


        public static boolean check(Object obj) {
            return obj == null;
        }

        public static boolean check(List list) {
            return list == null || list.isEmpty();
        }

        public static boolean check(Object[] array) {
            return array == null || array.length == 0;
        }

        public static boolean check(String str) {
            return str == null || "".equals(str.trim());
        }

        public static boolean check(Map map) {
            return map == null || map.isEmpty();
        }

        public static boolean check(Set set) {
            return set == null || set.isEmpty();
        }

        public static boolean checkShow(Activity activity, String checkContent, String showMessage) {
            if (check(checkContent)) {
                Toast.makeText(activity, showMessage, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        public static boolean checkZeroShow(Activity activity, String checkContent, String showMessage) {
            if (check(checkContent)) {
                Toast.makeText(activity, showMessage, Toast.LENGTH_SHORT).show();
                return true;
            }

            if ("0".equals(checkContent.trim())) {
                Toast.makeText(activity, showMessage, Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        }

    }

    /**
     * 正则表达式
     */
    public static class Regular {

        /**
         * 正则：手机号（简单）
         */
        public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";

        /**
         * 隐藏手机号码
         *
         * @param tel
         * @return
         */
        public static String hidePhoneNumber(String tel) {
            // 括号表示组，被替换的部分$n表示第n组的内容
            return tel.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }

        /**
         * 验证手机号码是否合法
         */
        public static boolean isPhoneNumber(String mobiles) {

            if (mobiles == null) return false;
            return mobiles.length() >= 11;
        }

        /**
         * 验证手机号（简单）
         *
         * @param input 待验证文本
         * @return {@code true}: 匹配<br>{@code false}: 不匹配
         */
        public static boolean isMobileSimple(final CharSequence input) {
            return isMatch(REGEX_MOBILE_SIMPLE, input);
        }

        /**
         * 判断是否匹配正则
         *
         * @param regex 正则表达式
         * @param input 要匹配的字符串
         * @return {@code true}: 匹配<br>{@code false}: 不匹配
         */
        public static boolean isMatch(final String regex, final CharSequence input) {
            return input != null && input.length() > 0 && Pattern.matches(regex, input);
        }

        /**
         * 验证身份证号码是否合法
         */
        public static boolean isIdCardNumber(String number) {
            return (number.length() == 15 && number.matches("^\\d{15}"))
                    || (number.length() == 18 && (number.matches("^\\d{17}[x,X,\\d]")));
        }

        /**
         * 验证密码是否合法
         */
        public static boolean validatePass(String password, int limitMinCount, int limitMaxCount) {
            return password.length() >= limitMinCount && password.length() <= limitMaxCount;
        }

        /**
         * 判断是不是英文字母
         */
        public static boolean isECharacter(String codePoint) {
            return codePoint.matches("^[A-Za-z]$");
        }

        /**
         * 验证是否是邮箱
         *
         * @param email
         * @return
         */
        public static boolean isEmail(String email) {
            String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(email);
            return m.matches();
        }

        /**
         * 验证是否是完好的密码
         * 6-16
         * 包含数字字母
         *
         * @param password
         * @return
         */
        public static boolean isValidatePassWord(String password) {
            String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);
            return m.matches();
        }
    }

    /**
     * 计算类
     */
    public static class Calculation {
        /**
         * 字节转
         *
         * @param size
         * @return
         */
        public static String calculaSize(int size) {
            //获取到的size为：1705230
            int GB = 1024 * 1024 * 1024;//定义GB的计算常量
            int MB = 1024 * 1024;//定义MB的计算常量
            int KB = 1024;//定义KB的计算常量
            DecimalFormat df = new DecimalFormat("0");//格式化小数
            String resultSize = "";
            if (size / GB >= 1) {
                //如果当前Byte的值大于等于1GB
                resultSize = df.format(size / (float) GB) + "GB   ";
            } else if (size / MB >= 1) {
                //如果当前Byte的值大于等于1MB
                resultSize = df.format(size / (float) MB) + "MB   ";
            } else if (size / KB >= 1) {
                //如果当前Byte的值大于等于1KB
                resultSize = df.format(size / (float) KB) + "KB   ";
            } else {
                resultSize = size + "B";
            }
            return resultSize;
        }
    }


    public static class PHOTO {

        /**
         * 拍照片
         *
         * @param packName    包名
         * @param REQ_GALLERY requestCode
         */
        public static String takePhoto(Fragment fragment, String packName, int REQ_GALLERY) {
            String mPublicPhotoPath = null;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //判断是否有相机应用
            if (takePictureIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
                //创建临时图片文件
                java.io.File photoFile = null;
                try {
                    photoFile = PictureUtils.createPublicImageFile();
                    mPublicPhotoPath = photoFile.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //设置Action为拍照
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //这里加入flag
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri photoURI = FileProvider.getUriForFile(fragment.getContext(), packName + ".fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fragment.startActivityForResult(takePictureIntent, REQ_GALLERY);
            }
            return mPublicPhotoPath;
        }


        /**
         * 拍照片
         *
         * @param activity    activity对象
         * @param packName    包名
         * @param REQ_GALLERY requestCode
         */
        public static String takePhoto(Activity activity, String packName, int REQ_GALLERY) {
            String mPublicPhotoPath = null;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //判断是否有相机应用
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                //创建临时图片文件
                java.io.File photoFile = null;
                try {
                    photoFile = PictureUtils.createPublicImageFile();
                    mPublicPhotoPath = photoFile.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //设置Action为拍照
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //这里加入flag
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri photoURI = FileProvider.getUriForFile(activity, packName + ".fileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQ_GALLERY);
            }
            return mPublicPhotoPath;
        }

        /**
         * 获取image的真实URI
         *
         * @param context
         * @param imageFile
         * @return
         */
        private static Uri getImageContentUri(Context context, java.io.File imageFile) {
            String filePath = imageFile.getAbsolutePath();
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID},
                    MediaStore.Images.Media.DATA + "=? ",
                    new String[]{filePath}, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                return Uri.withAppendedPath(baseUri, "" + id);
            } else {
                if (imageFile.exists()) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.DATA, filePath);
                    return context.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                } else {
                    return null;
                }
            }
        }

        /**
         * 拿到临时的缓存目录
         *
         * @param context
         * @return
         */
        private static String getDiskCacheDir(Context context) {
            String cachePath = null;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
            return cachePath;
        }

        /**
         * 剪裁图片
         *
         * @param activity
         * @param file
         * @param CROP_PHOTO
         */
        public static String startPhotoZoom(Activity activity, java.io.File file, int CROP_PHOTO) {
            java.io.File cacheFile = PictureUtils.getCacheFile(new java.io.File(getDiskCacheDir(activity)), System.currentTimeMillis() + "_crop_image.jpg");
            Log.i("TAG", getImageContentUri(activity, file) + "裁剪照片的真实地址");
            try {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(getImageContentUri(activity, file), "image/*");//自己使用Content Uri替换File Uri
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile));//定义输出的File Uri
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true);
                activity.startActivityForResult(intent, CROP_PHOTO);
            } catch (ActivityNotFoundException e) {
                String errorMessage = "Your device doesn't support the crop action!";
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cacheFile.getAbsolutePath();
        }

        /**
         * 剪裁图片
         *
         * @param activity
         * @param file
         * @param CROP_PHOTO
         */
        public static String startPhotoZoom(Activity activity, java.io.File file, int CROP_PHOTO, int aspectX, int aspectY) {
            java.io.File cacheFile = PictureUtils.getCacheFile(new java.io.File(getDiskCacheDir(activity)), System.currentTimeMillis() + "_crop_image.jpg");
            Log.i("TAG", getImageContentUri(activity, file) + "裁剪照片的真实地址");
            try {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(getImageContentUri(activity, file), "image/*");//自己使用Content Uri替换File Uri
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", aspectX);
                intent.putExtra("aspectY", aspectY);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile));//定义输出的File Uri
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true);
                activity.startActivityForResult(intent, CROP_PHOTO);
            } catch (ActivityNotFoundException e) {
                String errorMessage = "Your device doesn't support the crop action!";
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cacheFile.getAbsolutePath();
        }

        /**
         * 获取相册中的图片
         */
        public static void getImageFromAlbum(Activity activity, int REQUEST_CODE_PICK_IMAGE) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    }

    public static class Video {
        /**
         * 相册中选择视频
         * @param activity
         * @param REQUEST_CODE_PICK_VIDEO
         */
        public static void getVideoFromAlbum(Activity activity, int REQUEST_CODE_PICK_VIDEO) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");//相片类型
            activity.startActivityForResult(intent, REQUEST_CODE_PICK_VIDEO);
        }
    }

    public static class PhoneUtils {


//        public static void copy(Context context, String msg) {
//            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//            cm.setText(msg);
//            if (cm.getText().equals(msg)) {
//                Toast.makeText(context, R.string.tip_copy_succ, Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, R.string.tip_copy_failed, Toast.LENGTH_SHORT).show();
//            }
//        }

        /**
         * 播放视频
         *
         * @param context
         * @param videoUrl
         */
        public static void play(Context context, String videoUrl) {
//            videoUrl="http://oztdowev7.bkt.clouddn.com/luxSmI_hcBonNuhtzT6ToAzRoryd";
//             String extension = MimeTypeMap.getFileExtensionFromUrl(videoUrl);
//             String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//             Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//             mediaIntent.setDataAndType(Uri.parse(videoUrl), mimeType);
//             context.startActivity(mediaIntent);


            XLog.e("the url is" + videoUrl);
            Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
            mediaIntent.setDataAndType(Uri.parse(videoUrl), "video/*");
            context.startActivity(mediaIntent);
        }

    }
}
