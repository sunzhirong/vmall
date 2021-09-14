package com.ysarch.uibase.recyclerview;

/**
 * Created by fysong on 07/01/2019.
 */

public class ItemDataWrapper {
    private int mType;
    private Object mData;
    private Object mCallback;
    private Object mExtraData;

    public Object getData() {
        return mData;
    }

    public Object getCallback() {
        return mCallback;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public ItemDataWrapper(int type) {
        mType = type;
    }
    public ItemDataWrapper(int type, Object data) {
        mType = type;
        mData = data;
    }

    public ItemDataWrapper(int type, Object data, Object callback) {
        mType = type;
        mData = data;
        mCallback = callback;
    }

    public Object getExtraData() {
        return mExtraData;
    }

    public void setExtraData(Object extraData) {
        mExtraData = extraData;
    }

    public ItemDataWrapper setData(Object data) {
        mData = data;
        return this;
    }

    public ItemDataWrapper setCallback(Object callback) {
        mCallback = callback;
        return this;
    }

    public ItemDataWrapper setOnItemClickListener(OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
        return this;
    }

    public int getType() {
        return mType;
    }

    public Object getItemData() {
        return mData;
    }



    public interface OnItemClickListener {
        void onItemClick(int position, Object data);
    }

}
