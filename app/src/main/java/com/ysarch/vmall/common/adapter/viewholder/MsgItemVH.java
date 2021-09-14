package com.ysarch.vmall.common.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.MsgBean;
import com.ysarch.vmall.utils.StringUtil;
import com.yslibrary.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息vh
 * Created by fysong on 27/10/2020
 **/
public class MsgItemVH extends AbsViewHolder {
    @BindView(R.id.tv_time_msg_item)
    TextView mTVTime;
    @BindView(R.id.tv_title_msg_item)
    TextView mTVTitle;
    @BindView(R.id.tv_content_msg_item)
    TextView mTVContent;
    private MsgBean mMsgBean;
    public MsgItemVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mMsgBean = (MsgBean) data;
        mTVTime.setText(TimeUtils.formatDateFromUTC(mMsgBean.getUpdateTime()));
        if(!TextUtils.isEmpty(mMsgBean.getTitle())){
            mTVTitle.setVisibility(View.VISIBLE);
            mTVTitle.setText(mMsgBean.getTitle());
        } else {
            mTVTitle.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(mMsgBean.getContent())){
            mTVContent.setVisibility(View.VISIBLE);
            mTVContent.setText(mMsgBean.getContent());
        } else {
            mTVContent.setVisibility(View.GONE);
        }
    }

    public static int getLayoutRes() {
        return R.layout.item_msg;
    }
}
