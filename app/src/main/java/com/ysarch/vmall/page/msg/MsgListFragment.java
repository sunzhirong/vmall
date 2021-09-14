package com.ysarch.vmall.page.msg;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.vmall.common.adapter.MsgListAdapter;
import com.ysarch.vmall.domain.bean.MsgBean;
import com.ysarch.vmall.domain.constant.BundleKey;

import java.util.List;

/**
 * Created by fysong on 27/10/2020
 **/
public class MsgListFragment extends CommonPureListFragment<MsgListPresenter> {

    private MsgListAdapter mAdapter;
    private int mMsgType;

    public static Bundle getBundle(int msgType) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_MSG_TYPE, msgType);
        return bundle;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mAdapter = new MsgListAdapter(getContext());
    }

    @Override
    protected void requestData(int page) {
        if (mMsgType != -1)
            getPresenter().requestMsgList(mMsgType, false);
    }


    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        initRecyclerView(null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null)
            mMsgType = getArguments().getInt(BundleKey.ARG_MSG_TYPE, -1);

        if (mMsgType != -1)
            getPresenter().requestMsgList(mMsgType, getUserVisibleHint());
    }

    @Override
    public MsgListPresenter newPresenter() {
        return new MsgListPresenter();
    }

    public void onDataSucc(List<MsgBean> msgBeans) {
        resetUIStatus(1, true);
        mAdapter.refreshData(msgBeans);
    }
}
