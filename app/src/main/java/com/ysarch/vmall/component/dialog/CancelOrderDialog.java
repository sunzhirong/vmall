package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;
import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 01/10/2020
 **/
public class CancelOrderDialog extends Dialog {

    @BindView(R.id.rg_reason)
    RadioGroup mRgReason;
    @BindView(R.id.rb_1)
    RadioButton mRb1;
    @BindView(R.id.rb_2)
    RadioButton mRb2;
    @BindView(R.id.rb_3)
    RadioButton mRb3;
    @BindView(R.id.rb_4)
    RadioButton mRb4;
    @BindView(R.id.rb_5)
    RadioButton mRb5;
    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    private CancelOrderDialog(Context context, int style) {
        super(context, style);
        init();
    }


    private void setMenuImg(TextView textView) {
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.selector_ic_radiobtn_cancel);
        int dimension = (int) getContext().getResources().getDimension(R.dimen.rb_size);
        drawable.setBounds(0, 0, dimension, dimension);
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public void init() {
        View view = View.inflate(getContext(), R.layout.dialog_cancel_order, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(true);

        setMenuImg(mRb1);
        setMenuImg(mRb2);
        setMenuImg(mRb3);
        setMenuImg(mRb4);
        setMenuImg(mRb5);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
    }


    @OnClick({R.id.iv_cancel_order_close, R.id.tv_submit_cancel_dialog})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel_order_close:
                dismiss();
                break;
            case R.id.tv_submit_cancel_dialog:
                if (mCallback != null) {
                    mCallback.onCancelOpt(mRgReason.getCheckedRadioButtonId());
                }
                dismiss();
                break;
        }
    }


    public interface Callback {
        void onCancelOpt(int id);
    }


    public static class Builder {
        private Context mContext;
        private int mStyle = R.style.ScrollDialog;
        private Callback mCallback;

        public Builder setCallback(Callback callback) {
            mCallback = callback;
            return this;
        }

        public Builder(Context context) {
            mContext = context;
        }


        public CancelOrderDialog build() {
            CancelOrderDialog dialog = new CancelOrderDialog(mContext, mStyle);
            dialog.setCallback(mCallback);
            return dialog;
        }
    }

    @Override
    public void show() {
        super.show();
        TCAgent.onPageStart(getContext(), "取消订单弹窗");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TCAgent.onPageEnd(getContext(), "取消订单弹窗");
    }
}
