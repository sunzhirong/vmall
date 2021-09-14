package com.ysarch.uibase.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.ysarch.vmall.utils.SystemUtil;
import com.yslibrary.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装处理编辑页的键盘隐藏
 * Created by fysong on 2019-10-31
 **/
public class WinSoftKeyboardManager {

    private ViewGroup mVGRoot;
    private WeakReference<EditText> mETWeakReference;
    private WeakReference<View.OnClickListener> onClickListenerWeakReference;
    private boolean hasRegisteredEditText = false;

    public WinSoftKeyboardManager(ViewGroup rootView) {
        mVGRoot = rootView;
    }

    /**
     * 检测所有Edittext焦点变化
     */
    public void registEditTexts() {
        List<EditText> editTexts = new ArrayList<>();
        findEditTexts(mVGRoot, editTexts);

        if (CollectionUtils.isNotEmpty(editTexts)) {
            for (int i = 0; i < editTexts.size(); i++) {
                EditText editText = editTexts.get(i);
                editText.setOnFocusChangeListener(new WrapFocusListener(
                        editText.getOnFocusChangeListener()) {
                    @Override
                    public void onETFocusChange(View v, boolean hasFocus) {
                        mETWeakReference = new WeakReference<>((EditText) v);
                    }
                });
            }
            hasRegisteredEditText = true;

            if( onClickListenerWeakReference != null ) {
                setRootViewClickListener(onClickListenerWeakReference.get());
            } else {
                setRootViewClickListener(null);
            }

        }
    }


    /**
     * 设置rootView的点击事件
     * 页面如果需要监听root的点击事件，需要调用该api设置，否则会导致动态监测隐藏键盘的功能失效
     * @param onClickListener
     */
    public void setRootViewClickListener(View.OnClickListener onClickListener) {
        if (hasRegisteredEditText) {
            mVGRoot.setOnClickListener(new WrapClickListener(onClickListener) {
                @Override
                public void onRootClick(View view) {
                    if( mETWeakReference != null && mETWeakReference.get()!= null){
                        SystemUtil.hideKeyboard(mETWeakReference.get());
                    } else {
                        SystemUtil.hideKeyboard(view);
                    }
                }
            });
        } else {
            mVGRoot.setOnClickListener(onClickListener);
            if (onClickListener != null) {
                onClickListenerWeakReference = new WeakReference<>(onClickListener);
            }
        }
    }


    private void findEditTexts(ViewGroup viewGroup, List<EditText> editTexts) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                findEditTexts((ViewGroup) view, editTexts);
            } else if (view instanceof EditText) {
                editTexts.add((EditText) view);
            }
        }
    }


    public static abstract class WrapClickListener implements View.OnClickListener {
        private WeakReference<View.OnClickListener> mClickListenerWRefer;

        public WrapClickListener(View.OnClickListener clickListener) {
            if (clickListener != null) {
                mClickListenerWRefer = new WeakReference<>(clickListener);
            }
        }

        @Override
        public void onClick(View v) {
            if (mClickListenerWRefer != null && mClickListenerWRefer.get() != null) {
                mClickListenerWRefer.get().onClick(v);
            }

            onRootClick(v);
        }

        public abstract void onRootClick(View view);
    }

    public static abstract class WrapFocusListener implements View.OnFocusChangeListener {

        private WeakReference<View.OnFocusChangeListener> mFocusListenerWRefer;


        public WrapFocusListener(View.OnFocusChangeListener focusChangeListener) {
            if (focusChangeListener != null) {
                //避免影响外部设定逻辑处理
                mFocusListenerWRefer = new WeakReference<>(focusChangeListener);
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (mFocusListenerWRefer != null && mFocusListenerWRefer.get() != null) {
                mFocusListenerWRefer.get().onFocusChange(v, hasFocus);
            }

            onETFocusChange(v, hasFocus);
        }

        public abstract void onETFocusChange(View v, boolean hasFocus);
    }


}
