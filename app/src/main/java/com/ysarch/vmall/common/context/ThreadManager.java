package com.ysarch.vmall.common.context;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * Created by fysong on 2017/9/15.
 */

public class ThreadManager {

    private static final int WORKER_PRIORITY = Process.THREAD_PRIORITY_BACKGROUND;
    private static final int BACKGROUND_PRIORITY = Process.THREAD_PRIORITY_DEFAULT;
    private WeakHandler mMainHandler;
    private WeakHandler mWorkerHandler;
    private WeakHandler mBackgroundHandler;

    private ThreadManager() {
        mMainHandler = new WeakHandler(Looper.getMainLooper());
        HandlerThread workThread = new HandlerThread("worker", WORKER_PRIORITY);
        workThread.start();
        mWorkerHandler = new WeakHandler(workThread.getLooper());

        HandlerThread backgroundThread = new HandlerThread("background", BACKGROUND_PRIORITY);
        backgroundThread.start();
        mBackgroundHandler = new WeakHandler(backgroundThread.getLooper());
    }

    public static ThreadManager getInstance() {
        return Holder.instance;
    }

    public void mainThread(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public void mainThread(Runnable runnable, long delay) {
        mMainHandler.postDelayed(runnable, delay);
    }

    public void mainThreadRemove(Runnable runnable) {
        mMainHandler.removeCallbacks(runnable);
    }

    public void workerThread(Runnable runnable) {
        mWorkerHandler.post(runnable);
    }

    public void workerThread(Runnable runnable, long delay) {
        mWorkerHandler.postDelayed(runnable, delay);
    }

    public void backgroundThread(Runnable runnable) {
        mBackgroundHandler.post(runnable);
    }

    public void backgroundThread(Runnable runnable, long delay) {
        mBackgroundHandler.postDelayed(runnable, delay);
    }

    public Looper getWorkerLooper() {
        return mWorkerHandler.getLooper();
    }
    private static class Holder {
        static ThreadManager instance = new ThreadManager();
    }
}
