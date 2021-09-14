package com.yslibrary.event;

import android.os.Message;
import android.util.SparseArray;

import com.yslibrary.context.ThreadManager;
import com.yslibrary.utils.MessageHelper;

import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by fysong on 2018/8/22.
 * 后期参考EventBus增加编译期注解自动注册notifyId方式
 */
public class EventCenter {

    private final SparseArray<CopyOnWriteArrayList<WeakReference<IEventListener>>> mListeners = new SparseArray<>();

    private EventCenter() {
    }

    public static EventCenter getInstance() {
        return SingletonHolder.sInstance;
    }

    public void clear() {
        synchronized (mListeners) {
            mListeners.clear();
        }
    }

    public void registerNotification(IEventListener eventListener) {
        //todo 二期优化 通过编译期注解自动注册notifyId，不用一个一个输入
    }

    /**
     * @param notifyId
     * @param eventListener 不可以直接new一个临时变量传入，因为仅仅只有一个WeakReference引用的话，会被立即回收
     */
    public void registerNotification(int notifyId, IEventListener eventListener) {
        CopyOnWriteArrayList<WeakReference<IEventListener>> list;
        synchronized (mListeners) {
            list = mListeners.get(notifyId);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
                mListeners.put(notifyId, list);
            }
        }

        list.add(new WeakReference<>(eventListener));
        for (WeakReference<IEventListener> item : list) {
            if (item.get() == null) {
                //删掉已被GC回收的listener
                list.remove(item);
            }
        }
    }

    public void unregisterNotification(IEventListener eventListener) {
        synchronized (mListeners) {
            int size = mListeners.size();
            for (int i = 0; i < size; i++) {
                CopyOnWriteArrayList<WeakReference<IEventListener>> value = mListeners.valueAt(i);
                for (WeakReference<IEventListener> item : value) {
                    IEventListener listener = item.get();
                    if (listener == eventListener || listener == null) {
                        //删掉已被GC回收的listener
                        value.remove(item);
                    }
                }
            }
        }
    }

    /**
     * 这个不是强制的，一般情况下不需要注销
     *
     * @param notifyId
     * @param eventListener
     */
    public void unregisterNotification(int notifyId, IEventListener eventListener) {
        CopyOnWriteArrayList<WeakReference<IEventListener>> list;
        synchronized (mListeners) {
            list = mListeners.get(notifyId);
        }

        if (list != null) {
            for (WeakReference<IEventListener> item : list) {
                IEventListener listener = item.get();
                if (listener == eventListener || listener == null) {
                    //删掉已被GC回收的listener
                    list.remove(item);
                }
            }
        }
    }

    public void notify(int what) {
        notify(MessageHelper.obtain(what));
    }

    public void notify(int what, Object obj) {
        notify(MessageHelper.obtain(what, obj));
    }

    public void notify(int what, int arg1) {
        notify(MessageHelper.obtain(what, arg1));
    }

    public void notify(int what, int arg1, int arg2) {
        notify(MessageHelper.obtain(what, arg1, arg2));
    }

    public void notify(final Message msg) {
        notifyInner(msg);
    }

    private void notifyInner(Message msg) {
        int notifyId = msg.what;

        CopyOnWriteArrayList<WeakReference<IEventListener>> list;
        synchronized (mListeners) {
            list = mListeners.get(notifyId);
        }
        if (list != null) {
            // 可能这个通知没有任何监听
            for (WeakReference<IEventListener> item : list) {
                IEventListener eventListener = item.get();
                if (eventListener != null) {
                    ThreadManager.getInstance().mainThread(() -> eventListener.onNotify(msg));
                } else {
                    //删掉已被GC回收的listener
                    list.remove(item);
                }
            }
        }

    }

    private static class SingletonHolder {
        final static EventCenter sInstance = new EventCenter();
    }
}
