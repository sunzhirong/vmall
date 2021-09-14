package com.ysarch.uibase.recyclerview.scroller;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * Created by fysong on 12/09/2020
 **/
public class LinearTopSnapSmoothScroller extends LinearSmoothScroller {
    public LinearTopSnapSmoothScroller(Context context) {
        super(context);
    }


    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;
    }
}
