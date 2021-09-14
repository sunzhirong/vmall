package com.ysarch.vmall.utils;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * 防止handle内存泄漏，使用弱引用
 */

public class SafeHandler<T extends Handler.Callback> extends Handler {
    protected WeakReference<T> mRef;

    public SafeHandler(WeakReference<T> ref) {
        mRef = ref;
    }

    public SafeHandler(T obj) {
        mRef = new WeakReference<>(obj);
    }

    public T getContainer() {
        return mRef.get();
    }

    @Override
    public void handleMessage(android.os.Message msg) {
        super.handleMessage(msg);
        Callback container = getContainer();
        if (container != null) {
            container.handleMessage(msg);
        }
    }
}
