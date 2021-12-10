package com.ysarch.vmall.page.webview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;
import com.ysarch.uibase.dialog.LoadingDialog;
import com.ysarch.uibase.recyclerview.FRecyclerView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.ThreadManager;
import com.ysarch.vmall.component.web.VideoChromClient;
import com.ysarch.vmall.component.web.VideoImpl;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.webview.presenter.CommonWebPresenter;
import com.ysarch.vmall.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by fysong on 21/01/2019.
 */

public class CommonWebActivity extends XActivity<CommonWebPresenter> {

    /**
     * 通知js重新检查登陆信息
     */
    public static final String JS_RECHECK_LOGIN = "javascript:loadLogin()";
    public static final String JS_RECHECK_LOAD_COMMENT = "javascript:loadComment()";

    public static final String ARG_URL = "arg_url";
    public static final String ARG_TITLE = "arg_title";
    public final static int REQUEST_USER_CODE = 7777;
    private static final String TAG = "CommonWebActivity";
    protected ImageView mIVBack, mIShare;
    protected TextView mTVTitle;
    protected WebView mWebView;
    protected FrameLayout mLyRoot;
    protected LinearLayout etCommentLayout, imagesLayout;
    protected View coverView;
    protected FRecyclerView recycleViewImages;
    protected ImageView imgComment;
    protected TextView sendBtn;
    @BindView(R.id.webView_pb)
    ProgressBar webViewPb;
    @BindView(R.id.iv_common_web_placeholder)
    ImageView mIVPlaceholder;
    //    private WebInteractor webInteractor;
    ArrayList<String> mListSelectImg = new ArrayList<>();
    private String mTitle;
    private String mUrl;
    private String mPageName;
    private ViewStub mViewStub;
    private LoadingDialog loadingDialog;

    public static Bundle getBundle(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url);
        return bundle;
    }

    /**
     * @param url
     * @param pagename
     * @return
     */
    public static Bundle getBundle(String url, String pagename) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url);
        bundle.putString(BundleKey.ARG_TALKINGDATA_PAGE_NAME, pagename);
        return bundle;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);

        initContentView();

        initTitleBar();

        initWebSetting();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        parseArgument(getIntent());

//        addJavascriptInterface(mWebView);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
//        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_USER_ACCOUNT_CHANGE, this);
    }

    @Override
    public void setUpStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white));
    }

    protected void resetViewProperties() {
        if (mTVTitle != null && !TextUtils.isEmpty(mTitle)) {
            mTVTitle.setText(mTitle);
        }

        if (mWebView != null) {
            if (!TextUtils.isEmpty(mUrl)) {
                reloadWebWithUserInfo();
            } else {
                showTs(getString(R.string.url_is_null));
            }
        }
    }


    protected void reload() {
        if (mWebView != null) {
            mWebView.reload();
        }
    }


    protected void reloadWebWithUserInfo() {
        clearCookies(context);
        mWebView.clearHistory();
        mWebView.stopLoading();
        String url = getUrl();
        Log.i(TAG, "reloadWebWithUserInfo: \n" + url);
        mWebView.loadUrl(url, getHeaders());
    }


    protected String getUrl() {
//        if (UserInfoManager.isLogin()) {
//            StringBuilder stringBuilder = new StringBuilder(mUrl);
//            if (mUrl.lastIndexOf("?") != -1) {
//                stringBuilder.append("&");
//            } else {
//                stringBuilder.append("?");
//            }
//            stringBuilder.append("token=" + UserInfoManager.getUser().getToken()
//                    + "&user_id=" + UserInfoManager.getUser().getId() + "&platform=1");
//            return stringBuilder.toString();
//        }

        return mUrl;
    }

    protected Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
//        if (UserInfoManager.isLogin()) {
//            headers.put("token", UserInfoManager.getUser().getToken());
//            headers.put("user_id", UserInfoManager.getUser().getId());
//        }

        headers.put("platform", "1");
        return headers;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseArgument(null);
    }

    private void parseArgument(Intent intent) {
        if (intent != null) {
            mTitle = intent.getStringExtra(ARG_TITLE);
            mUrl = intent.getStringExtra(ARG_URL);
            mPageName = intent.getStringExtra(BundleKey.ARG_TALKINGDATA_PAGE_NAME);

            resetViewProperties();
        }
    }

    protected void initContentView() {
        mLyRoot = findViewById(R.id.ly_root_common_web);
//        etCommentLayout = findViewById(R.id.et_comment_layout);
//        imagesLayout = findViewById(R.id.images_layout);
        coverView = findViewById(R.id.cover_view);
//        imgComment = findViewById(R.id.img_comment);
//        richEditor = findViewById(R.id.et_comment);
//        sendBtn = findViewById(R.id.send_btn);
        mWebView = mLyRoot.findViewById(R.id.wv_common_web_webview);
        mViewStub = findViewById(R.id.vs_title_bar_web);

//        recycleViewImages = findViewById(R.id.recycle_view_images);
    }


    /**
     * titlebar这里之所以分开多个方法写，为了下游能自由定制，目前仅仅是粗设计，后面同学可再优化，上游设计尽量简单些
     **/
    protected void initTitleBar() {
        if (mViewStub != null && getTitleBarLayoutRes() != -1) {
            mViewStub.setLayoutResource(getTitleBarLayoutRes());
            mViewStub.inflate();
            initTitleBarViews();
        }
    }


    /**
     * 如果传入的titlebar layout文件的id跟默认的不一致，重写该方法
     */
    protected void initTitleBarViews() {
        mIVBack = findViewById(R.id.iv_back_title_bar_web);
//        mIShare = findViewById(R.id.iv_title_bar_right);
        mTVTitle = findViewById(R.id.tv_title_bar_web);

        mIVBack.setOnClickListener(e -> finish());
    }

    /**
     * titleBar 布局
     *
     * @return
     */
    protected int getTitleBarLayoutRes() {
        return R.layout.include_title_bar_web;
    }


    protected void resetWebViewSettings(WebSettings webSettings) {
        webSettings.setUseWideViewPort(true);//设置WebView是否应该启用“ViewPort”
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setBuiltInZoomControls(true);//设置WebView是否应该使用其内置的放大机制
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportMultipleWindows(false);//是否支持多窗口
        webSettings.setLoadsImagesAutomatically(true);//设置WebView是否应该载入图像资源
        webSettings.setBlockNetworkImage(false);//设置WebView是否不应该从网络加载图像资源
        webSettings.setAllowFileAccess(false);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + ", bee for Android");


        try {
            webSettings.setJavaScriptEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         *  目前
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
//
//    protected void addJavascriptInterface(WebView webView) {
//        webInteractor = new WebInteractor(this);
//        webView.addJavascriptInterface(webInteractor, "verify");
//    }

    protected void initWebSetting() {
        resetWebViewSettings(mWebView.getSettings());


        mWebView.setDownloadListener((s, s1, s2, s3, l) -> {
            Uri uri = Uri.parse(s);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return overrideUrlLoading(url);
            }

            /**
             * 警告 有些url资源加载完成后，并没有正确回调onPageFinished方法
             *
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                view.requestFocus();
                super.onPageFinished(view, url);
                CommonWebActivity.this.onPageFinished(view, url);
            }


        });

        mWebView.setWebChromeClient(new VideoChromClient(new VideoImpl(this, mWebView)) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress > 90) {
                    mIVPlaceholder.setVisibility(View.GONE);
                }

                if (newProgress == 100) {
                    webViewPb.setVisibility(View.GONE);
                } else {
                    if (webViewPb.getVisibility() == View.GONE) {
                        webViewPb.setVisibility(View.VISIBLE);
                    }
                    webViewPb.setProgress(newProgress);
                }
                CommonWebActivity.this.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTVTitle.setText(title);
            }

        });

    }


    protected void onProgressChanged(WebView view, int newProgress) {
        if (newProgress > 90) {
            dismissLoadingDialog();
        }

        if (TextUtils.isEmpty(mTitle) && newProgress > 98) {
            mTitle = mWebView.getTitle();
            coverView.setVisibility(View.GONE);
            if (TextUtils.isEmpty(mTitle) || mTitle.equals("帖子详情")) {
                ThreadManager.getInstance().mainThread(new Runnable() {
                    @Override
                    public void run() {
                        mTitle = mWebView.getTitle();
                        mTVTitle.setText(mTitle);
                    }
                }, 1000);
            }
        }
    }


    protected boolean overrideUrlLoading(String url) {
        return false;
    }

    public String getWebViewTitle() {
        return mTitle;
    }

    protected void onPageFinished(WebView view, String url) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
        if(!TextUtils.isEmpty(mPageName))
            TCAgent.onPageStart(context,mPageName);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
        if(!TextUtils.isEmpty(mPageName))
            TCAgent.onPageEnd(context,mPageName);
    }

    @Override
    protected void onDestroy() {
//        EventCenter.getInstance().unregisterNotification(this);
        if (mWebView != null) {
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.removeAllViews();

            mWebView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void initData(Bundle savedInstanceState) {

    }


    private void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d("TAG", "Using clearCookies code for API >=" + Build.VERSION_CODES.LOLLIPOP_MR1);
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            Log.d("TAG", "Using clearCookies code for API <" + Build.VERSION_CODES.LOLLIPOP_MR1);
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_web;
    }

    public Dialog showLoadingDialog() {
        return this.showLoadingDialog(false);
    }

    public Dialog showLoadingDialog(boolean isCancelable) {
        return this.showLoadingDialog(null, isCancelable);
    }

    public Dialog showLoadingDialog(String loadingText, boolean isCancelable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        return loadingDialog.show(loadingText, isCancelable);
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showTs(String msg) {

    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public CommonWebPresenter newPresenter() {
        return new CommonWebPresenter();
    }

//    @Override
//    public void onNotify(Message message) {
//        if (message.what == NotificationDef.EVENT_USER_ACCOUNT_CHANGE && UserInfoManager.isLogin()) {
//            reloadWebWithUserInfo();
//        }
//    }


}
