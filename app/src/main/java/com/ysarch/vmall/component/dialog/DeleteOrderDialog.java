package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.ysarch.vmall.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 2020-06-15
 **/
public class DeleteOrderDialog extends Dialog {

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



        public Builder(Context context) {
            this.mContext = context;
        }


        public Builder setDeleteCallback(DeleteCallback payCallback) {
            mPayCallback = payCallback;
            return this;
        }


        public DeleteOrderDialog build() {
            DeleteOrderDialog dialog = new DeleteOrderDialog(mContext);
            dialog.setDeleteCallback(mPayCallback);
            return dialog;
        }
    }
}
