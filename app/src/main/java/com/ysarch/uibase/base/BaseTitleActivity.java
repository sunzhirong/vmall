package com.ysarch.uibase.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ysarch.vmall.R;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.event.OnSingleClickListener;
import com.yslibrary.utils.qmui.QMUIStatusBarHelper;

import qiu.niorgai.StatusBarCompat;


public abstract class BaseTitleActivity extends AppCompatActivity {

    protected ViewGroup mRootView;
    private LinearLayout mLyLeft;
    private LinearLayout mLyRight;
    private FrameLayout mContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArgument();

        Fragment fragment = createFragment();
        if (fragment != null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            mRootView = (ViewGroup) inflater.inflate(R.layout.activity_base, null, false);
            mContentContainer = mRootView.findViewById(R.id.fl_fragment_container);
            View titleView = onCreateTitleView(inflater, mRootView);

            if (titleView != null) {
                //沉浸处理
                if (isTranslucentStatusBar()) {
                    //暂时写死沉浸的样式，有不同的页面出现的话，要抽出来，提供下游定制  -- fys
                    QMUIStatusBarHelper.translucent(this);
                    QMUIStatusBarHelper.setStatusBarLightMode(this);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ResUtils.getDimeI(R.dimen.title_bar_height));
                    titleView.setBackgroundColor(0x00000000);
                    layoutParams.topMargin = SizeUtils.getStatusHeight(this);

                    FrameLayout frameLayout = new FrameLayout(this);
                    mRootView.removeView(mContentContainer);
                    frameLayout.addView(mContentContainer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    frameLayout.addView(titleView, layoutParams);
                    mRootView.addView(frameLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    mRootView.addView(titleView, 0);
                }
            }
            setContentView(mRootView);

            FragmentTransaction fragTransact = getSupportFragmentManager().beginTransaction();
            fragTransact.replace(R.id.fl_fragment_container, fragment);
            fragTransact.commitAllowingStateLoss();
        }
    }


    /**
     * 是否使用沉浸样式
     */
    protected boolean isTranslucentStatusBar() {
        return false;
    }


    protected void parseArgument() {

    }


    protected void onBindUI(ViewGroup root) {

    }

    protected View onCreateTitleView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(getTitleBarLayoutRes(), container, false);
        initTitleBarWidgets(view);
        return view;
    }


    protected void initTitleBarWidgets(View barRootView) {
        ((TextView) barRootView.findViewById(R.id.tv_base_title_bar_title)).setText(getCustomTitle());
        mLyRight = barRootView.findViewById(R.id.ly_base_title_bar_right);
        View leftView = initBarLeft();
        if (leftView != null) {
            mLyLeft = barRootView.findViewById(R.id.ly_base_title_bar_left);
            mLyLeft.addView(leftView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLyLeft.setVisibility(View.VISIBLE);
            mLyLeft.setOnClickListener(v -> onTitlebarLeftClick(v));
        }

        View rightView = initBarRight();
        if (rightView != null) {
            mLyRight = barRootView.findViewById(R.id.ly_base_title_bar_right);
            mLyRight.addView(rightView);//, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLyRight.setVisibility(View.VISIBLE);
            mLyRight.setOnClickListener(new OnSingleClickListener() {

                @Override
                public void onSingleClick(View view) {
                    onTitlebarRightClick(view);
                }
            });
        }
    }


    protected void setBackground(int bgColor) {
        mContentContainer.setBackgroundColor(bgColor);
    }

    protected @LayoutRes
    int getTitleBarLayoutRes() {
        return R.layout.layout_base_title_bar;
    }


    protected abstract String getCustomTitle();

    protected View initBarLeft() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_back_black);
        return imageView;
    }


    protected View initBarRight() {
        return null;
    }


    protected void onTitlebarLeftClick(View view) {
        onBackPressed();
    }

    protected void onTitlebarRightClick(View view) {
    }


    /**
     * 子类应该创建对应的Fragment
     */
    protected abstract Fragment createFragment();
}
