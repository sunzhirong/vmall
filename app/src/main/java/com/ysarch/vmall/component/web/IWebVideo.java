package com.ysarch.vmall.component.web;

import android.view.View;
import android.webkit.WebChromeClient;

/**
 * Created by fysong on 2020-02-27
 **/
public interface IWebVideo {

    void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback);


    void onHideCustomView();


    boolean isVideoState();
}
