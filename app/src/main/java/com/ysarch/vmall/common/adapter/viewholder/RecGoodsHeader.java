package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;

/**
 * Created by fysong on 18/09/2020
 **/
public class RecGoodsHeader extends AbsViewHolder {
    public RecGoodsHeader(View itemView) {
        super(itemView);
    }

    public static int getLayoutRes() {
        return R.layout.item_rec_goods_header;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {

    }
}
