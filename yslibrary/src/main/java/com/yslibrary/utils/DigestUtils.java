package com.yslibrary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要生成实用类
 *
 * @author Darcy yeguozhong@yeah.com
 */
public class DigestUtils {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 返回md5加密后的字符串
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        if (str == null) {
            return "";
        }
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取某个文件的MD5值
     *
     * @param file
     * @return
     * @throws NullPointerException
     */
    public static String md5(File file) {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        MessageDigest messageDigest;
        FileInputStream fis = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 8];
            int readCount;
            while ((readCount = fis.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, readCount);
            }
            return toHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeSilently(fis);
        }
        return "";
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
