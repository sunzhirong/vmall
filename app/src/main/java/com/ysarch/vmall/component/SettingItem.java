package com.ysarch.vmall.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niko on 2019/5/24.
 */

public class SettingItem extends RelativeLayout {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.iv_enter)
    ImageView mIvEnter;

    public SettingItem(Context context) {
        super(context);
        init(context, null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_item_setting, this, true);
        ButterKnife.bind(this, this);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);
            if (array.hasValue(R.styleable.SettingItem_leftText)) {
                mTvTitle.setText(array.getString(R.styleable.SettingItem_leftText));
            }
            if (array.hasValue(R.styleable.SettingItem_rightText)) {
                mTvRight.setText(array.getString(R.styleable.SettingItem_rightText));
            }
            if (array.hasValue(R.styleable.SettingItem_leftIcon)) {
                mIvLeft.setImageResource(array.getResourceId(R.styleable.SettingItem_leftIcon, 0));
            } else {
                mIvLeft.setVisibility(GONE);
            }
            if (array.hasValue(R.styleable.SettingItem_showEnter)) {
                boolean aBoolean = array.getBoolean(R.styleable.SettingItem_showEnter,true);
                mIvEnter.setVisibility(aBoolean?VISIBLE:INVISIBLE);
            }
            array.recycle();
        }
    }

    public void setRightText(String rightText) {
        mTvRight.setText(rightText);
    }

}
