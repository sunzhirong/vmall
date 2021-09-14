package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 01/10/2020
 **/
public class ClipSearchDialog extends Dialog {

    @BindView(R.id.tv_content)
    TextView mTvContent;
    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setContent(String content){
        if(!TextUtils.isEmpty(content)) {
            mTvContent.setText(content);
        }
    }

    private ClipSearchDialog(Context context, int style) {
        super(context, style);
        init();
    }


    public void init() {
        View view = View.inflate(getContext(), R.layout.dialog_clip_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setCancelable(true);


        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
    }


    @OnClick({R.id.tv_clip_search, R.id.v_close_clipsearch})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.v_close_clipsearch:
                dismiss();
                break;
            case R.id.tv_clip_search:
                if (mCallback != null) {
                    mCallback.onSearch();
                }
                dismiss();
                break;
        }
    }


    public interface Callback {
        void onSearch();
    }


    public static class Builder {
        private Context mContext;
        private int mStyle = R.style.ScrollDialog;
        private Callback mCallback;
        private String content;


        public Builder setCallback(Callback callback) {
            mCallback = callback;
            return this;
        }
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder(Context context) {
            mContext = context;
        }


        public ClipSearchDialog build() {
            ClipSearchDialog dialog = new ClipSearchDialog(mContext, mStyle);
            dialog.setCallback(mCallback);
            dialog.setContent(content);

            return dialog;
        }
    }
}
