package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 11/09/2020
 **/
public class CateLevel3Header extends AbsViewHolder {
    private TextView mTextView;
    public CateLevel3Header(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.tv_cate_name);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mTextView.setText((String)data);
    }


    public static int getLayoutRes() {
        return R.layout.item_header_cate_level3;
    }
}
