package com.ysarch.uibase.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fysong on 07/01/2019.
 */
public abstract class AbsRVAdapter extends RecyclerView.Adapter<AbsViewHolder> {
    private static int sTypeId = 0;
    protected List<ItemDataWrapper> mMixData = new ArrayList<>();

    public static int generateTypeId() {
        return ++sTypeId;
    }

    public List<ItemDataWrapper> getListData() {
        return mMixData;
    }

    public void clearData() {
        mMixData.clear();
    }

    @Override
    public int getItemCount() {
        return mMixData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMixData.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
