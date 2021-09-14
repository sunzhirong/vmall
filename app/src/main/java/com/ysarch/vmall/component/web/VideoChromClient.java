package com.ysarch.vmall.component.web;

import android.view.View;
import android.webkit.WebChromeClient;

/**
 * Created by fysong on 2020-02-27
 **/
public class VideoChromClient extends WebChromeClient {
    private IWebVideo mIVideo;

    public VideoChromClient(IWebVideo mIVideo) {
        this.mIVideo = mIVideo;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (mIVideo != null) {
            mIVideo.onShowCustomView(view, callback);
        }

    }

    @Override
    public void onHideCustomView() {
        if (mIVideo != null) {
            mIVideo.onHideCustomView();
        }
    }
}
