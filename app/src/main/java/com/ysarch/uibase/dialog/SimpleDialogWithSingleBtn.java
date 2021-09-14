package com.ysarch.uibase.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysarch.vmall.R;
import com.ysarch.vmall.utils.SizeUtils;


public class SimpleDialogWithSingleBtn extends Dialog {

    private TextView mTVConfirm;
    private TextView mTVContent;
    private TextView mTVTitle;
    private OnSubmitListener mOnSubmitListener;
    private boolean mAutoDismissWhileClick = true;
    private int mContentTextSize = 17;
    private int mContentTextColor = 0xff000000;

    private float mScreenProportion;

    public SimpleDialogWithSingleBtn(@NonNull Context context, int themeResId) {
        this(context, themeResId, 0.75f, null);
    }


//    public SimpleDialogWithSingleBtn(@NonNull Context context, float screenProportion, OnSubmitListener mOnSubmitListener) {
//        super(context, R.style.CustomDialog);
//        this.mOnSubmitListener = mOnSubmitListener;
//        this.mScreenProportion = screenProportion;
//        init();
//    }

    public SimpleDialogWithSingleBtn(@NonNull Context context, int themeResId, float screenProportion) {
        this(context, themeResId, screenProportion, null);
    }

    public SimpleDialogWithSingleBtn(@NonNull Context context, int themeResId, float screenProportion, OnSubmitListener mOnSubmitListener) {
        super(context, themeResId);
        this.mOnSubmitListener = mOnSubmitListener;
        this.mScreenProportion = screenProportion;
        init();
    }

    public SimpleDialogWithSingleBtn setContentMarginDp(int contentMarginHDp, int contentMarginTopDp) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTVContent.getLayoutParams();
        int marginH = SizeUtils.dp2px(contentMarginHDp);
        layoutParams.topMargin = SizeUtils.dp2px(contentMarginTopDp);
        layoutParams.leftMargin = layoutParams.rightMargin = marginH;
        mTVContent.invalidate();
        return this;
    }

    public SimpleDialogWithSingleBtn setButtonMarginHDp(int buttonMarginHDp) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTVConfirm.getLayoutParams();
        int marginH = SizeUtils.dp2px(buttonMarginHDp);
        layoutParams.leftMargin = layoutParams.rightMargin = marginH;
        mTVContent.invalidate();
        return this;
    }

    public void setContentTextSize(int contentTextSize) {
        mContentTextSize = contentTextSize;
        mTVContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mContentTextSize);
    }

    public void setContentTextColor(int contentTextColor) {
        mContentTextColor = contentTextColor;
        mTVContent.setTextColor(mContentTextColor);
    }

    public void setAutoDismissWhileClick(boolean autoDismissWhileClick) {
        mAutoDismissWhileClick = autoDismissWhileClick;
    }


    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        mOnSubmitListener = onSubmitListener;
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_simple_dialog_with_single_btn, null);
        mTVConfirm = view.findViewById(R.id.tv_confirm_simple_dialog_singlebtn);
        mTVContent = view.findViewById(R.id.tv_content_simple_dialog_singlebtn);
        mTVTitle = view.findViewById(R.id.tv_title_simple_dialog_singlebtn);

        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.width = (int) (defaultDisplay.getWidth() * mScreenProportion + 0.6);
        getWindow().setAttributes(attributes);

        addBtnClickListener();
    }

    public SimpleDialogWithSingleBtn addBtnClickListener() {
        mTVConfirm.setOnClickListener(view -> {
            if (mOnSubmitListener != null) mOnSubmitListener.onConfirmClick();
            if (mAutoDismissWhileClick)
                dismiss();
        });

        return this;
    }

    private SimpleDialogWithSingleBtn setConfirmLabel(String cancelLabel) {
        mTVConfirm.setText(cancelLabel);
        return this;
    }

    private SimpleDialogWithSingleBtn setContent(String rightLabel) {
        mTVContent.setText(rightLabel);
        return this;
    }

    private SimpleDialogWithSingleBtn setContentSpannable(Spannable spannable) {
        mTVContent.setMovementMethod(LinkMovementMethod.getInstance());
        mTVContent.setText(spannable);
        return this;
    }

    private SimpleDialogWithSingleBtn setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTVTitle.setVisibility(View.VISIBLE);
            mTVTitle.setText(title);
        } else {
            mTVTitle.setVisibility(View.GONE);
        }

        return this;
    }

    public enum DialogSize {
        Normal,
        Large
    }

    public interface OnSubmitListener {
        void onConfirmClick();
    }

    public static class Builder {
        private Context mContext;
        private String mContent;
        private String mTitle;
        private String mConfirmLabel;
        private OnSubmitListener mOnSubmitListener;
        private boolean mCancelable;
        private int mThemeResId = R.style.CustomDialog;
        private boolean mAutoDismissWhileClick = true;
        private int mContentTextSizeSp = 17;
        private int mContentTextColor = 0xff343434;
        private int mContentMarginHDp = 26;
        private int mButtonMarginHDp = 32;
        private float mScreenProportion = 0.75f;
        private int mContentMarginTopDp = 0;//36;
        private Spannable mContentSpannable;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setContentMarginTopDp(int contentMarginTopDp) {
            mContentMarginTopDp = contentMarginTopDp;
            return this;
        }

        public Builder setContentSpannable(Spannable contentSpannable) {
            mContentSpannable = contentSpannable;
            return this;
        }

        public Builder setDialogSize(DialogSize dialogSize) {
            if (dialogSize.equals(DialogSize.Large)) {
                mContentMarginHDp = 24;
                mButtonMarginHDp = 49;
                mScreenProportion = 0.832f;
                mContentMarginTopDp = 24;
            }
            return this;
        }

        public int getContentTextSizeSp() {
            return mContentTextSizeSp;
        }

        public Builder setContentTextSizeSp(int contentTextSize) {
            mContentTextSizeSp = contentTextSize;
            return this;
        }

        public int getContentTextColor() {
            return mContentTextColor;
        }

        public Builder setContentTextColor(int contentTextColor) {
            mContentTextColor = contentTextColor;
            return this;
        }

        public Builder setAutoDismissWhileClick(boolean autoDismissWhileClick) {
            mAutoDismissWhileClick = autoDismissWhileClick;
            return this;
        }

        /**
         * 不支持标题
         *
         * @param mTitle
         * @return
         */
        public Builder setTitle(String mTitle) {
            this.mTitle = mTitle;
            return this;
        }

        public Builder setmConfirmLabel(String mConfirmLabel) {
            this.mConfirmLabel = mConfirmLabel;
            return this;
        }

        public Builder setThemeResId(int themeResId) {
            mThemeResId = themeResId;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
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


        public SimpleDialogWithSingleBtn build() {
            SimpleDialogWithSingleBtn dialog = new SimpleDialogWithSingleBtn(mContext, mThemeResId,
                    mScreenProportion);
            dialog.setOnSubmitListener(mOnSubmitListener);
            dialog.setCancelable(mCancelable);
            dialog.setContentTextColor(mContentTextColor);
            dialog.setContentTextSize(mContentTextSizeSp);
            dialog.setAutoDismissWhileClick(mAutoDismissWhileClick);
            dialog.setContentMarginDp(mContentMarginHDp, mContentMarginTopDp)
                    .setButtonMarginHDp(mButtonMarginHDp)
                    .setConfirmLabel(mConfirmLabel)
                    .setTitle(mTitle);

            if (mContentSpannable != null) {
                dialog.setContentSpannable(mContentSpannable);
            } else {
                dialog.setContent(mContent);
            }

            return dialog;
        }

    }

}
