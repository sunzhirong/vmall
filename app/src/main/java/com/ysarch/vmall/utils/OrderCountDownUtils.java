package com.ysarch.vmall.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.ysarch.vmall.R;

import java.lang.ref.WeakReference;


/**
 * @author Ly
 * @date 2017/11/10
 */

public class OrderCountDownUtils extends CountDownTimer {
    private WeakReference<TextView> mTextViewWeakReference;
    private boolean isRunning = false;
    private WeakReference<Context> mContextWeakReference;
    private String mLabelIdle;
    private Context context;

    /**
     * @param context
     * @param millisInFuture    倒计时总时长---毫秒数
     * @param countDownInterval 倒计时间隔长---毫秒数
     * @param mView             倒计时要显示的View---目前为TextView
     * @param labelIdle         IDLE 状态的label
     */
    public OrderCountDownUtils(Context context, long millisInFuture, long countDownInterval, TextView mView, String labelIdle) {
        super(millisInFuture, countDownInterval);
        this.mContextWeakReference = new WeakReference<Context>(context);
        this.mTextViewWeakReference = new WeakReference<TextView>(mView);
        this.context = context;
        mLabelIdle = labelIdle;
    }

    public OrderCountDownUtils(Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mContextWeakReference = new WeakReference<Context>(context);
    }

    public void setView(TextView view) {
        this.mTextViewWeakReference = new WeakReference<TextView>(view);
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void onTick(long l) {
        isRunning = true;
        if (mTextViewWeakReference.get() != null) {
            mTextViewWeakReference.get().setEnabled(false);
            mTextViewWeakReference.get().setText(String.format(ResUtils.getString(R.string.format_count_down),timeConversion(l/1000)));
//            mTextViewWeakReference.get().setText(String.format((l / 1000) + "s"));

        }
    }
    private String timeConversion(long time) {
        long minutes = 0;
        long sencond = 0;
        long temp = time % 3600;
        if (time > 3600) {
            if (temp != 0) {
                if (temp > 60) {
                    minutes = temp / 60;
                    if (temp % 60 != 0) {
                        sencond = temp % 60;
                    }
                } else {
                    sencond = temp;
                }
            }
        } else {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
        return  (minutes<10?("0"+minutes):minutes) + ":" + (sencond<10?("0"+sencond):sencond);
    }


//    private Spanned getText(long l) {
//        return Html.fromHtml("<font color='#e30808'><big>" + (l / 1000) + "S</font>");
//    }

    @Override
    public void onFinish() {
        isRunning = false;
        if (mTextViewWeakReference.get() != null && mContextWeakReference.get() != null) {
            mTextViewWeakReference.get().setEnabled(true);
            mTextViewWeakReference.get().setClickable(true);
            mTextViewWeakReference.get().setText(
                    mLabelIdle);
//            mTextViewWeakReference.get().setBackgroundResource(R.drawable.bg_fillet_login);
//            mTextViewWeakReference.get().setTextColor(mContextWeakReference.get().getResources().getColor(R.color.main_black));
        }
    }

    public void doFinish(String tips) {
        cancel();
        isRunning = false;
        if (mTextViewWeakReference.get() != null) {
            mTextViewWeakReference.get().setEnabled(true);
            mTextViewWeakReference.get().setClickable(true);
            mTextViewWeakReference.get().setText(tips);
        }
    }
}
