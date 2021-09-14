package com.ysarch.uibase.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysarch.vmall.R;


/**
 * Created by wanglei on 2016/10/30.
 */

public class SimpleLoadMoreFooter extends RelativeLayout implements LoadMoreUIHandler {

    public boolean isError;
    TextView tvMsg;
    ProgressBar progressBar;
    private LoadMoreCallback mLoadMoreCallback;

    public SimpleLoadMoreFooter(Context context) {
        this(context, null);
    }

    public SimpleLoadMoreFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    private void setupView() {
        inflate(getContext(), R.layout.x_view_footer_load_more_simple, this);
        tvMsg = findViewById(R.id.tv_msg);
        progressBar = findViewById(R.id.progressBar);

        setVisibility(GONE);
    }

    @Override
    public void onLoading() {
        isError = false;
        setVisibility(VISIBLE);
        tvMsg.setText(R.string.loading);
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadFinish(boolean hasMore) {
        isError = false;
        if (hasMore) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
            tvMsg.setText(R.string.no_more);
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onLoadError() {
        setVisibility(VISIBLE);
        tvMsg.setText(R.string.load_failed_try_agian);
        isError = true;
        progressBar.setVisibility(GONE);
    }

    public void setLoadMoreCallback(LoadMoreCallback loadMoreCallback) {
        mLoadMoreCallback = loadMoreCallback;
        if (mLoadMoreCallback != null) {
            this.setOnClickListener(v -> {
                if (isError) {
                    mLoadMoreCallback.onLoadMore();
                    onLoading();
                }
            });
        }
    }

    public interface LoadMoreCallback {
        void onLoadMore();
    }
}

