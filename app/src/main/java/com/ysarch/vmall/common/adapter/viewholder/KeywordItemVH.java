package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 17/09/2020
 **/
public class KeywordItemVH extends AbsViewHolder {
    @BindView(R.id.ctv_keyword_search)
    CompatTextView mCompatTextView;
    private Callback mCallback;
    private String mKeyword;
    public KeywordItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCallback = (Callback) callback;
        mKeyword = (String) data;
        mCompatTextView.setText(mKeyword);
    }

    @OnClick(R.id.iv_delete_keyword_search)
    void onDeleteClick() {
        if(mCallback != null){
            mCallback.onDeleteClick(mPosition, mKeyword);
        }
    }


    public interface Callback{
        void onDeleteClick(int position, String keyword);
    }


    public static int getLayoutRes() {
        return R.layout.item_keyword_search;
    }
}
