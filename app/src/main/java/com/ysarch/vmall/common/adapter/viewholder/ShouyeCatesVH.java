package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;

/**
 * Created by fysong on 19/09/2020
 **/
public class ShouyeCatesVH extends AbsViewHolder {
    private RecyclerView mRecyclerView;
    public ShouyeCatesVH(View itemView) {
        super(itemView);
        mRecyclerView = (RecyclerView) itemView;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {

    }

    private void initRecyclerview(){

    }
}
