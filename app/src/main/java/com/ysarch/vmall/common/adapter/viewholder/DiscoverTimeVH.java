package com.ysarch.vmall.common.adapter.viewholder;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.utils.LPUtils;
import com.ysarch.vmall.utils.SizeUtils;

/**
 * Created by fysong on 12/09/2020
 **/
public class DiscoverTimeVH extends AbsViewHolder {
    public DiscoverTimeVH(View itemView) {
        super(itemView);
    }

    public static View createItemView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextColor(0xff9A9A9A);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(LPUtils.FILL, LPUtils.WRAP);
        textView.setGravity(Gravity.CENTER);
        layoutParams.topMargin = layoutParams.bottomMargin = SizeUtils.dp2px(16);
        textView.setLayoutParams(layoutParams);
        textView.setText("18：30");
        return textView;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {

    }
}
