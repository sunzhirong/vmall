package com.yslibrary.event;

import android.view.View;

/**
 * 限制快速点击的listener
 * Created by fysong on 2019-12-19
 **/
public abstract class OnSingleClickListener implements View.OnClickListener {

    private static final long DELAY = 300;
    private long mLastClickTime;

    @Override
    public void onClick(View v) {
        long curTime = System.currentTimeMillis();
        if (curTime - mLastClickTime >= DELAY) {
            mLastClickTime = curTime;
            onSingleClick(v);
        }
    }


    public abstract void onSingleClick(View view);
}
