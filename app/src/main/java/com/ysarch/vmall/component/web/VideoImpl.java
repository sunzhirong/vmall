package com.ysarch.vmall.component.web;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.core.util.Pair;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by fysong on 2020-02-27
 **/
public class VideoImpl implements IWebVideo, EventInterceptor {

    private static final String TAG = VideoImpl.class.getSimpleName();
    private Activity mActivity;
    private WebView mWebView;
    private Set<Pair<Integer, Integer>> mFlags = null;
    private View mMoiveView = null;
    private ViewGroup mMoiveParentView = null;
    private WebChromeClient.CustomViewCallback mCallback;


    private boolean mBarExitWhileFullScreen = true;


    public VideoImpl(Activity activity, WebView webView) {
        this(activity, webView, true);
    }

    public VideoImpl(Activity mActivity, WebView webView, boolean barExitWhileFullScreen) {
        this.mActivity = mActivity;
        this.mWebView = webView;
        mFlags = new HashSet<>();
        this.mBarExitWhileFullScreen = barExitWhileFullScreen;
    }


    @Override
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
//        Activity mActivity;
//        if ((mActivity = this.mActivity) == null || mActivity.isFinishing()) {
//            return;
//        }
////        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//        Window mWindow = mActivity.getWindow();
//        Pair<Integer, Integer> mPair = null;
//        //保存当前屏幕的状态
//        if ((mWindow.getAttributes().flags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) == 0) {
//            mPair = new Pair<>(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 0);
//            mWindow.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//            mFlags.add(mPair);
//        }
//
//        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) && (mWindow.getAttributes().flags & WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED) == 0) {
//            mPair = new Pair<>(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, 0);
//            mWindow.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//            mFlags.add(mPair);
//        }
//
//        SystemUtil.hideSupportActionBar(this.mActivity, true);
//
//
//        if (mMoiveView != null) {
//            callback.onCustomViewHidden();
//            return;
//        }
//
//        if (mWebView != null) {
//            mWebView.setVisibility(View.GONE);
//        }
//
//        if (mMoiveParentView == null) {
////            FrameLayout mDecorVie = (FrameLayout) mActivity.getWindow().getDecorView();
//            FrameLayout mDecorView = //.getWindow().getDecorView();
//                    mActivity.findViewById(Window.ID_ANDROID_CONTENT);
//            mMoiveParentView = new FrameLayout(mActivity);
//            mMoiveParentView.setBackgroundColor(Color.BLACK);
//            mDecorView.addView(mMoiveParentView);
//        }
//        this.mCallback = callback;
//        mMoiveParentView.addView(this.mMoiveView = view);
//        mMoiveParentView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onHideCustomView() {

//        if (mMoiveView == null) {
//            return;
//        }
//        if (mActivity != null && mActivity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            SystemUtil.showSupportActionBar(this.mActivity, true);
//            JZUtils.setRequestedOrientation(this.mActivity, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
//
//        if (!mFlags.isEmpty()) {
//            for (Pair<Integer, Integer> mPair : mFlags) {
//                mActivity.getWindow().setFlags(mPair.second, mPair.first);
//            }
//            mFlags.clear();
//        }
//
//        mMoiveView.setVisibility(View.GONE);
//        if (mMoiveParentView != null && mMoiveView != null) {
//            mMoiveParentView.removeView(mMoiveView);
//
//        }
//        if (mMoiveParentView != null) {
//            mMoiveParentView.setVisibility(View.GONE);
//        }
//
//        if (this.mCallback != null) {
//            mCallback.onCustomViewHidden();
//        }
//        this.mMoiveView = null;
//        if (mWebView != null) {
//            mWebView.setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public boolean isVideoState() {
        return mMoiveView != null;
    }

    @Override
    public boolean event() {

        if (isVideoState()) {
            onHideCustomView();
            return true;
        } else {
            return false;
        }

    }
}
