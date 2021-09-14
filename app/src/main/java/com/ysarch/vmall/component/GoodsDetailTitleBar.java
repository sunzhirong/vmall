package com.ysarch.vmall.component;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;

import com.ysarch.vmall.R;
import com.ysarch.vmall.utils.LPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 22/09/2020
 **/
public class GoodsDetailTitleBar extends FrameLayout {
    @BindView(R.id.iv_back_oval_goods_title_bar)
    ImageView mIVBackOval;
    @BindView(R.id.iv_back_black_goods_title_bar)
    ImageView mIVBackBlack;
    @BindView(R.id.iv_share_oval_goods_title_bar)
    ImageView mIVShareOval;
    @BindView(R.id.iv_share_black_goods_title_bar)
    ImageView mIVShareBlack;
    @BindView(R.id.iv_msg_oval_goods_title_bar)
    ImageView mIVMsgOval;
    @BindView(R.id.iv_msg_black_goods_title_bar)
    ImageView mIVMsgBlack;


    private List<View> mAntiAlphaViews = new ArrayList<>();
    private List<View> mAlphaViews = new ArrayList<>();
    private Callback mCallback;

    public GoodsDetailTitleBar(Context context) {
        this(context, null, 0);
    }

    public GoodsDetailTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsDetailTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.include_goods_title_bar, null);
        addView(view, LPUtils.FILL, LPUtils.WRAP);
        ButterKnife.bind(this, view);

        mAlphaViews.add(mIVBackBlack);
        mAlphaViews.add(mIVMsgBlack);
        mAlphaViews.add(mIVShareBlack);

        mAntiAlphaViews.add(mIVBackOval);
        mAntiAlphaViews.add(mIVMsgOval);
        mAntiAlphaViews.add(mIVShareOval);

        setViewAlpha(0);
    }

    public void setViewAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        float colorAlpha = alpha * 255;
        this.setBackgroundColor(Color.argb((int) colorAlpha, 255, 255, 255));


        for (int i = 0; i < mAlphaViews.size(); i++) {
            mAlphaViews.get(i).setAlpha(alpha);
        }

        float antiAlpha = 1.0f - alpha;
        for (int i = 0; i < mAntiAlphaViews.size(); i++) {
            mAntiAlphaViews.get(i).setAlpha(antiAlpha);
        }

        if(mCallback != null){
            mCallback.onAlphaChange(alpha);
        }
    }

    @OnClick({R.id.iv_back_oval_goods_title_bar, R.id.iv_back_black_goods_title_bar,
            R.id.iv_share_oval_goods_title_bar, R.id.iv_share_black_goods_title_bar,
            R.id.iv_msg_oval_goods_title_bar, R.id.iv_msg_black_goods_title_bar})
    void onViewClick(View view) {
        if (mCallback == null)
            return;
        switch (view.getId()) {
            case R.id.iv_back_oval_goods_title_bar:
            case R.id.iv_back_black_goods_title_bar:
                mCallback.onBackClick();
                break;
            case R.id.iv_share_oval_goods_title_bar:
            case R.id.iv_share_black_goods_title_bar:
                mCallback.onShareClick();
                break;
            case R.id.iv_msg_oval_goods_title_bar:
            case R.id.iv_msg_black_goods_title_bar:
                mCallback.onMsglick();
                break;
        }
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        /**
         * 回退点击
         */
        void onBackClick();

        /**
         * 分享点击
         */
        void onShareClick();

        /**
         * 消息点击
         */
        void onMsglick();

        /**
         *
         * @param alpha 取值[0.0, 1.0]
         */
        void onAlphaChange(float alpha);
    }
}
