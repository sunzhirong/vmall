package com.ysarch.vmall.page.msg;

import androidx.fragment.app.Fragment;

import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.event.NotificationDef;
import com.yslibrary.event.EventCenter;

/**
 * Created by fysong on 27/10/2020
 **/
public class MsgActivity extends BaseTitleActivity {
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_msg);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppContext.getsInstance().setHasNewMsg(false);
        EventCenter.getInstance().notify(NotificationDef.EVENT_MSG_NEW_NONE);
    }

    @Override
    protected Fragment createFragment() {
        return new MsgVPFragment();
    }
}
