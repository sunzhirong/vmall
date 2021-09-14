package com.yslibrary.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by fysong on 04/01/2019.
 */

public class CollectionUtils {

    public static boolean isEmpty(Collection col) {
        return col == null || col.isEmpty();
    }

    public static boolean isNotEmpty(Collection col) {
        return !isEmpty(col);
    }

    public static int size(Collection col) {
        return col == null ? 0 : col.size();
    }

//    public static  T[] toArray(ArrayList<T> arr, int maxLen) {
//        if (arr == null)
//            return null;
//        T[] tArr = new T[arr.size()];
//        for (int i = 0; i < arr.size(); i++) {
//            tArr[i] = arr.get(i);
//        }
//
//        return tArr;
//    }
}
