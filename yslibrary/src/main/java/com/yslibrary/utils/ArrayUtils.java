package com.yslibrary.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Darcy https://yedaxia.github.io/
 * @version 2017/1/16
 */

public class ArrayUtils {

    /**
     * 判断是否为Null或者空
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断是否为Null或者空
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> array) {
        return array == null || array.size() == 0;
    }

    /**
     * 判断是否不为空
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length > 0;
    }


    public static <T> int size(T[] array) {
        if (isEmpty(array)) {
            return 0;
        }

        return array.length;
    }

    public static <T> int size(List<T> array) {
        if (isEmpty(array)) {
            return 0;
        }

        return array.size();
    }

    public static <T> ArrayList<T> toArrayList(T[] arr) {
        if (arr == null) {
            return null;
        }

        ArrayList<T> list = new ArrayList<>();
        for (T t : arr) {
            list.add(t);
        }

        return list;
    }

    public static <T> ArrayList<T> toArrayList(T[] arr, int maxLen) {
        if (arr == null) {
            return null;
        }

        ArrayList<T> list = new ArrayList<>();
        for (T t : arr) {
            list.add(t);
            if( maxLen > 0 && list.size() == maxLen ) {
                break;
            }
        }

        return list;
    }

    public static <T> T pickOne(T[] arr, int idx) {
        if (arr == null || idx < 0 || idx > size(arr) - 1) {
            return null;
        }

        return arr[idx];
    }
}
