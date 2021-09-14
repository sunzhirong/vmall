package com.ysarch.uibase.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysarch.vmall.R;


public class SimpleDialogWithTwoBtn extends Dialog {

    public TextView tvLeft;
    public TextView tvRight;
    TextView mTVWarning;
    OnSubmitListener mOnSubmitListener;
    private Context context;
    private boolean mAutoDismissWhileClick = true;

    public SimpleDialogWithTwoBtn(@NonNull Context context, OnSubmitListener mOnSubmitListener) {
        this(context, R.style.CustomDialog);
        this.mOnSubmitListener = mOnSubmitListener;
    }

    public SimpleDialogWithTwoBtn(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    public void setAutoDismissWhileClick(boolean autoDismissWhileClick) {
        mAutoDismissWhileClick = autoDismissWhileClick;
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        mOnSubmitListener = onSubmitListener;
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_simple_dialog_with_two_btn, null);
        tvLeft = view.findViewById(R.id.tv_cancel_simple_dialog_two_btn);
        tvRight = view.findViewById(R.id.tv_confirm_simple_dialog_two_btn);
        mTVWarning = view.findViewById(R.id.tv_content_simple_dialog_two_btn);
        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = (int) (defaultDisplay.getWidth() * 0.74);
        getWindow().setAttributes(attributes);

        addBtnClickListener();
    }


    public void setContent(String content) {
        mTVWarning.setText(content);
    }

    public SimpleDialogWithTwoBtn addBtnClickListener() {
        tvLeft.setOnClickListener(view -> {

            if (mOnSubmitListener != null) mOnSubmitListener.onLeftBtnClick();

            if (mAutoDismissWhileClick)
                dismiss();
        });

        tvRight.setOnClickListener(view -> {
            if (mOnSubmitListener != null) mOnSubmitListener.onRightBtnClick();
            if (mAutoDismissWhileClick)
                dismiss();
        });

        return this;
    }

    private SimpleDialogWithTwoBtn setLeftLabel(String cancelLabel) {
        tvLeft.setText(cancelLabel);
        return this;
    }

    private SimpleDialogWithTwoBtn setRightLabel(String rightLabel) {
        tvRight.setText(rightLabel);
        return this;
    }

    private SimpleDialogWithTwoBtn setWarning(String warning) {
        mTVWarning.setText(warning);
        return this;
    }

    public interface OnSubmitListener {
        void onLeftBtnClick();

        void onRightBtnClick();
    }

    public static class Builder {
        private Context mContext;
        private String mWarning;
        private String mLeftLabel;
        private OnSubmitListener mOnSubmitListener;
        private String mRightLabel;
        private boolean mCancelable = true;
        private int mThemeResId = R.style.CustomDialog;
        private boolean mWithinCloseBtn;
        private boolean mAutoDismissWhileClick = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setAutoDismissWhileClick(boolean autoDismissWhileClick) {
            mAutoDismissWhileClick = autoDismissWhileClick;
            return this;
        }

        public Builder setWithinCloseBtn(boolean withinCloseBtn) {
            mWithinCloseBtn = withinCloseBtn;
            return this;
        }

        public Builder setThemeResId(int themeResId) {
            mThemeResId = themeResId;
            return this;
        }

        public Builder setWarning(String content) {
            mWarning = content;
            return this;
        }

        public Builder setLeftLabel(String leftLabel) {
            mLeftLabel = leftLabel;
            return this;
        }


        public Builder setRightLabel(String rightLabel) {
            mRightLabel = rightLabel;
            return this;
        }

        public Builder setOnSubmitListener(OnSubmitListener listener) {
            mOnSubmitListener = listener;
            return this;
        }


        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }


        public SimpleDialogWithTwoBtn build() {
            SimpleDialogWithTwoBtn dialog = new SimpleDialogWithTwoBtn(mContext, mThemeResId);
            dialog.setOnSubmitListener(mOnSubmitListener);
            dialog.setOnSubmitListener(mOnSubmitListener);
            dialog.setCancelable(mCancelable);
            dialog.setAutoDismissWhileClick(mAutoDismissWhileClick);
            dialog.setLeftLabel(mLeftLabel)
                    .setRightLabel(mRightLabel)
                    .setWarning(mWarning);
            return dialog;
        }

    }


}
