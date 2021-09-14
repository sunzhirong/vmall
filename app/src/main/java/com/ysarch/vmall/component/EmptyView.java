package com.ysarch.vmall.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 01/11/2020
 **/
public class EmptyView extends FrameLayout {

    @BindView(R.id.iv_pic_empty)
    ImageView mImageView;
    @BindView(R.id.tv_tip_empty)
    TextView mTVTip;
    @BindView(R.id.tv_opt_empty)
    TextView mTVOpt;
    private Callback mCallback;

    public EmptyView(@NonNull Context context) {
        this(context, null, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.include_data_empty, null, false);
        addView(view);
        ButterKnife.bind(this, view);
    }

    public void setTips(String tips) {
        if (!TextUtils.isEmpty(tips)) {
            mTVTip.setText(tips);
            mTVTip.setVisibility(VISIBLE);
        } else {
            mTVTip.setVisibility(GONE);
        }
    }

    public void setOptCallback(Callback callback) {
        mCallback = callback;
//        if (mCallback == null) {
//            mTVOpt.setVisibility(GONE);
//        } else {
//            mTVOpt.setVisibility(VISIBLE);
//        }
    }



    public void setOptLabel(String label) {
        if (!TextUtils.isEmpty(label)) {
            mTVOpt.setText(label);
            mTVOpt.setVisibility(VISIBLE);
        } else {
            mTVOpt.setVisibility(GONE);
        }
    }



    @OnClick(R.id.tv_opt_empty)
    void onViewClick() {
        if (mCallback != null) {
            mCallback.onOptClick();
        }
    }

    public interface Callback {
        void onOptClick();
    }

}
