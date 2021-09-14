package com.ysarch.vmall.page.main;

import android.os.Bundle;
import android.os.Message;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.StatusBarView;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.LinearVerDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.DiscoverRcyAdapter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.helper.HeartbeatHelper;
import com.ysarch.vmall.page.main.presenter.MainBrandPresenter;
import com.ysarch.vmall.page.msg.MsgActivity;
import com.ysarch.vmall.utils.LPUtils;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;
import com.yslibrary.event.OnSingleClickListener;

/**
 * 分类
 * Created by fysong on 24/08/2020
 **/
public class MainDiscoverFragment extends CommonPureListFragment<MainBrandPresenter> implements IEventListener {
    private View mMsgEntrance;
    private View mMsgDot;
    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);

        StatusBarView statusBarView = new StatusBarView(getContext());
        statusBarView.setBackgroundColor(0xffffffff);
        mLinearLayout.addView(statusBarView,0, LPUtils.getLinearLayoutParams(LPUtils.FILL,LPUtils.WRAP));

        View view = layoutInflater.inflate(R.layout.include_title_bar_discover_main, null);
        mLinearLayout.addView(view, 1, LPUtils.getLinearLayoutParams(LPUtils.FILL, ResUtils.getDimeI(R.dimen.title_bar_height)));
        mRootView.setBackgroundColor(0xffefeef4);

        mMsgEntrance = view.findViewById(R.id.iv_bell_title_bar_discover_main);
        mMsgDot = view.findViewById(R.id.v_msg_title_bar_discover_main);
        mMsgEntrance.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(UserInfoManager.judeIsLogin(getActivity())){
                    NavHelper.startActivity(getActivity(), MsgActivity.class);
                }
            }
        });
    }


    @Override
    public void bindEvent() {
        super.bindEvent();
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_MSG_NEW,this);
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_MSG_NEW_NONE,this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        initRecyclerView(new LinearVerDecoration(marginH,0,0,marginH));
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new DiscoverRcyAdapter(getContext());
    }

    @Override
    protected void requestData(int page) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            checkMsg();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isHidden()){
            checkMsg();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregisterNotification(this);
    }

    private void checkMsg(){
        mMsgDot.setVisibility(AppContext.getsInstance().getHasNewMsg()? View.VISIBLE:View.GONE);
    }

    @Override
    public MainBrandPresenter newPresenter() {
        return new MainBrandPresenter();
    }

    @Override
    public void onNotify(Message message) {
        switch (message.what){
            case NotificationDef.EVENT_MSG_NEW:
            case NotificationDef.EVENT_MSG_NEW_NONE:
                if(!isHidden())
                    checkMsg();
                break;
        }
    }
}
