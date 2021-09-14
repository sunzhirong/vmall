package com.yslibrary.utils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * Created by fysong on 2019-10-11
 **/
public class FragmentTransUtil {

    /**
     * 提交事物
     */
    public static void transFragment(FragmentManager fragmentManager, @IdRes int container, Fragment frag, String tag, boolean isAddToBackStack) {
        if (frag == null || tag == null) {
            return;
        }
        Fragment tagFragment = fragmentManager.findFragmentByTag(tag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (tagFragment == null) {
            fragmentTransaction.add(container, frag, tag);
        }
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != frag && fragment != null) {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        fragmentTransaction.show(frag);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        try {
            fragmentTransaction.commit();
            // 首页切换时Fragment和上层搜索按钮、底部TAB显示尽量一致，所以这里强制同步执行
            fragmentManager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            try {
                fragmentTransaction.commitAllowingStateLoss();
            } catch (IllegalStateException ise) {
                e.printStackTrace();
            }
        }
    }
}
