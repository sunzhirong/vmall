package com.ysarch.uibase.recyclerview;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fysong on 07/01/2019.
 */

public abstract class AbsViewHolder extends RecyclerView.ViewHolder {

    protected int mPosition;
    protected ItemDataWrapper.OnItemClickListener mOnItemClickListener;


    public AbsViewHolder(View itemView) {
        super(itemView);
    }

    public void onBindDataWrapper(final int position, final ItemDataWrapper dataWrapper) {
        mPosition = position;
        mOnItemClickListener = dataWrapper.getOnItemClickListener();
        if (dataWrapper.getOnItemClickListener() != null) {
            itemView.setOnClickListener(v ->
                    dataWrapper.getOnItemClickListener().onItemClick(position, dataWrapper.getData()));
        } else {
            Log.e("AbsViewHolder", "OnItemClickListener null on position:" + position  ) ;
        }
        onBindData(position, dataWrapper.getData(), dataWrapper.getCallback());
    }

    public abstract void onBindData(int position, Object data, Object callback);
}
