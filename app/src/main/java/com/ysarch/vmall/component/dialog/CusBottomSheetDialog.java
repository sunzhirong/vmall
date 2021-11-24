package com.ysarch.vmall.component.dialog;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tendcloud.tenddata.TCAgent;

import org.jetbrains.annotations.NotNull;

public class CusBottomSheetDialog extends BottomSheetDialog {

    private String name;

    public CusBottomSheetDialog(@NonNull @NotNull Context context,String name) {
        super(context);
        this.name = name;
    }

    @Override
    public void show() {
        super.show();
        if(!TextUtils.isEmpty(name))
            TCAgent.onPageStart(getContext(),name);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(!TextUtils.isEmpty(name))
            TCAgent.onPageEnd(getContext(),name);
    }
}
