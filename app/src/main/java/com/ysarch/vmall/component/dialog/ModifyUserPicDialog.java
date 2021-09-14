package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ysarch.vmall.R;


/**
 * Author: Ly
 * Data：2018/4/8-10:03
 * Description: 修改头像弹窗
 */
public class ModifyUserPicDialog extends DialogFragment {

    private static final String TAG = ModifyUserPicDialog.class.getName();
    protected Dialog mDialog;
    private onPickerClickListener mOnPickerClickListener;

    public static ModifyUserPicDialog newInstance() {
        Bundle args = new Bundle();
        ModifyUserPicDialog fragment = new ModifyUserPicDialog();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 防止重复弹出 显示此dialog的唯一方法
     *
     * @param activity
     * @return
     */
    public static ModifyUserPicDialog showDialog(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        ModifyUserPicDialog modifyUserPicDialog =
                (ModifyUserPicDialog) fragmentManager.findFragmentByTag(TAG);
        if (null == modifyUserPicDialog) {
            modifyUserPicDialog = newInstance();
        }
        if (!activity.isFinishing()
                && null != modifyUserPicDialog
                && !modifyUserPicDialog.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(modifyUserPicDialog, TAG)
                    .commitAllowingStateLoss();
        }
        return modifyUserPicDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_pic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setOnPickerClickListener(onPickerClickListener onPickerClickListener) {
        mOnPickerClickListener = onPickerClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ScrollDialog);
        View view = View.inflate(getActivity(), R.layout.dialog_select_pic, null);
        builder.setView(view);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = mDialog.getWindow();
        if (window != null && window.getDecorView() != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            //设置没有效果
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);
        } else {
            Log.e(TAG, "window是空的或者是window.getDecorView()是空的");
        }
        initView(view);
        return mDialog;
    }

    private void initView(View view) {
        TextView tvTakePhoto, tvTakeAlbum, tvCancel;
        tvTakePhoto = view.findViewById(R.id.tv_select_take_photo);
        tvTakeAlbum = view.findViewById(R.id.tv_select_take_album);
        tvCancel = view.findViewById(R.id.tv_select_take_cancel);

        tvTakeAlbum.setOnClickListener(v -> {
            if (mOnPickerClickListener != null) {
                mOnPickerClickListener.onClickToFromAlbum();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
        tvTakePhoto.setOnClickListener(v -> {
            if (mOnPickerClickListener != null) {
                mOnPickerClickListener.onClickToTakePhone();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
        tvCancel.setOnClickListener(v -> {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        });
    }

    /**
     * 监听事件
     */
    public interface onPickerClickListener {
        /**
         * 拍照
         */
        void onClickToTakePhone();

        /**
         * 相册
         */
        void onClickToFromAlbum();
    }
}
