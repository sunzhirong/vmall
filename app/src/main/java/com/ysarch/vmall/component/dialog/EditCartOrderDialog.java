package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 2020-06-15
 **/
public class EditCartOrderDialog extends Dialog {


    @BindView(R.id.et_dialog_num)
    EditText mEtNum;
    @BindView(R.id.tv_dialog_confirm)
    TextView mTvConfirm;
    @BindView(R.id.tv_dialog_cancel)
    TextView mTvCancel;




    private DeleteCallback mDeleteCallback;

    public EditCartOrderDialog(@NonNull Context context) {
        this(context, R.style.CenterDialog);
    }

    public EditCartOrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }


    public void setDeleteCallback(DeleteCallback deleteCallback) {
        mDeleteCallback = deleteCallback;
    }

    private void setNum(String num) {
        mEtNum.setText(num);
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_edit_cart_order, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        mEtNum.setInputType(InputType.TYPE_CLASS_NUMBER);
    }


    @OnClick({R.id.tv_dialog_cancel, R.id.tv_dialog_confirm})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_confirm:
                if (mDeleteCallback != null) {
                    mDeleteCallback.onConfirm(mEtNum.getText().toString());
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    public interface DeleteCallback {
        void onConfirm(String num);

    }

    public static class Builder {

        private Context mContext;
        private DeleteCallback mPayCallback;
        private String num;



        public Builder(Context context) {
            this.mContext = context;
        }


        public Builder setDeleteCallback(DeleteCallback payCallback) {
            mPayCallback = payCallback;
            return this;
        }

        public Builder setNum(String num){
            this.num = num;
            return this;
        }



        public EditCartOrderDialog build() {
            EditCartOrderDialog dialog = new EditCartOrderDialog(mContext);
            dialog.setDeleteCallback(mPayCallback);
            dialog.setNum(num);
            return dialog;
        }
    }




}
