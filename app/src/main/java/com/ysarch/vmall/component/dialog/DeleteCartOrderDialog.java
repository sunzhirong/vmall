package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 2020-06-15
 **/
public class DeleteCartOrderDialog extends Dialog {


    @BindView(R.id.tv_delete_title)
    TextView mTvTitle;
    @BindView(R.id.tv_dialog_confirm)
    TextView mTvConfirm;
    @BindView(R.id.tv_dialog_cancel)
    TextView mTvCancel;




    private DeleteCallback mDeleteCallback;

    public DeleteCartOrderDialog(@NonNull Context context) {
        this(context, R.style.CenterDialog);
    }

    public DeleteCartOrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
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



    public void setDeleteCallback(DeleteCallback deleteCallback) {
        mDeleteCallback = deleteCallback;
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_delete_cart_order, null);
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
        private String submitText;
        private String cancelText;



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
        public Builder setSubmitText(String submitText){
            this.submitText = submitText;
            return this;
        }
        public Builder setCancelText(String cancelText){
            this.cancelText = cancelText;
            return this;
        }





        public DeleteCartOrderDialog build() {
            DeleteCartOrderDialog dialog = new DeleteCartOrderDialog(mContext);
            dialog.setDeleteCallback(mPayCallback);
            if (!TextUtils.isEmpty(title)) {
                dialog.setTvTitle(title);
            }
            if (!TextUtils.isEmpty(submitText)) {
                dialog.setTvCancel(submitText);
            }
            if (!TextUtils.isEmpty(cancelText)) {
                dialog.setTvConfirm(cancelText);
            }
            return dialog;
        }
    }


}
