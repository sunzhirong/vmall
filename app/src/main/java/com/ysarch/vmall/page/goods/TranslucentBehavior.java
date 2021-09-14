package com.ysarch.vmall.page.goods;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ysarch.vmall.component.GoodsDetailTitleBar;

/**
 * Created by fysong on 20/08/2020
 **/
public class TranslucentBehavior extends CoordinatorLayout.Behavior<GoodsDetailTitleBar> {

    private int mChildHeight = 0;

    public TranslucentBehavior() {
    }

    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull GoodsDetailTitleBar child, @NonNull View dependency) {
        return dependency instanceof FrameLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull GoodsDetailTitleBar child, @NonNull View dependency) {

        if (mChildHeight == 0) {
            mChildHeight = child.getHeight();
        }

        float percent = dependency.getY() / (dependency.getHeight() - mChildHeight);
//        Log.e("TranslutionBehavior", "dependency.getY(): " + dependency.getY());
//        Log.e("TranslutionBehavior", "dependency.getHeight(): " + dependency.getHeight());
        if (percent > 1)
            percent = 1f;

//        float alpha = percent * 255;
//        child.setBackgroundColor(Color.argb((int) alpha, 63, 91, 181));

        child.setViewAlpha(percent);
        return true;
    }
}
