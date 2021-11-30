package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.tendcloud.tenddata.TCAgent;
import com.ysarch.vmall.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 2020-06-15
 **/
public class DeleteOrderDialog extends Dialog {


    @BindView(R.id.tv_delete_title)
    TextView mTvTitle;
    @BindView(R.id.tv_delete_content)
    TextView mTvContent;
    @BindView(R.id.tv_dialog_confirm)
    TextView mTvConfirm;
    @BindView(R.id.tv_dialog_cancel)
    TextView mTvCancel;




    private DeleteCallback mDeleteCallback;

    public DeleteOrderDialog(@NonNull Context context) {
        this(context, R.style.CenterDialog);
    }

    public DeleteOrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    public void setTvContent(String content) {
        mTvContent.setText(content);
    }

    public void setTvTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTvConfirm(String confirm) {
        mTvConfirm.setText(confirm);
    }

    public void setTvCancel(String cancel) {
        mTvCancel.setText(cancel);
    }

    private void hintContent() {
        mTvContent.setVisibility(View.GONE);
    }


    public void setDeleteCallback(DeleteCallback deleteCallback) {
        mDeleteCallback = deleteCallback;
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_delete_order, null);
        setContentView(view);
        ButterKnife.bind(this, view);

    }


    @OnClick({R.id.tv_dialog_cancel, R.id.tv_dialog_confirm})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_confirm:
                if (mDeleteCallback != null) {
                    mDeleteCallback.onConfirm();
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    public interface DeleteCallback {
        void onConfirm();

    }

    public static class Builder {

        private Context mContext;
        private DeleteCallback mPayCallback;
        private String title;
        private String content;
        private String submitText;
        private String cancelText;
        private boolean hintContent;



        public Builder(Context context) {
            this.mContext = context;
        }


        public Builder setDeleteCallback(DeleteCallback payCallback) {
            mPayCallback = payCallback;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder setContent(String content){
            this.content = content;
            return this;
        }
        public Builder setSubmitText(String submitText){
            this.submitText = submitText;
            return this;
        }
        public Builder setCancelText(String cancelText){
            this.cancelText = cancelText;
            return this;
        }


        public Builder isHintContent(boolean hintContent){
            this.hintContent = hintContent;
            return this;
        }




        public DeleteOrderDialog build() {
            DeleteOrderDialog dialog = new DeleteOrderDialog(mContext);
            dialog.setDeleteCallback(mPayCallback);
            if (!TextUtils.isEmpty(title)) {
                dialog.setTvTitle(title);
            }
            if (!TextUtils.isEmpty(content)) {
                dialog.setTvContent(content);
            }
            if (!TextUtils.isEmpty(submitText)) {
                dialog.setTvCancel(submitText);
            }
            if (!TextUtils.isEmpty(cancelText)) {
                dialog.setTvConfirm(cancelText);
            }
            if(hintContent){
                dialog.hintContent();
            }
            return dialog;
        }
    }

    @Override
    public void show() {
        super.show();
        TCAgent.onPageStart(getContext(), "删除订单弹窗");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TCAgent.onPageEnd(getContext(), "删除订单弹窗");
    }


}
