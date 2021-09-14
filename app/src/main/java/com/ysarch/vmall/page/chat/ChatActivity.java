package com.ysarch.vmall.page.chat;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.recorder.MediaManager;
import com.hyphenate.helpdesk.easeui.ui.BaseActivity;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.OrderInfo;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.RouterManager;
import com.ysarch.vmall.component.ease.EaseHelper;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.OrderBean;
import com.ysarch.vmall.domain.bean.OrderListBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.VMallUtils;

/**
 * Created by fysong on 26/10/2020
 **/
public class ChatActivity extends BaseActivity {

    public static ChatActivity instance = null;
    private String toChatUsername;
    private ChatFragment chatFragment;


    private static final int PAGE_FROM_GOODS  = 0;
    private static final int PAGE_FROM_ORDER  = 1;


    private int mPageFrom;
    private String mMSGHeader;
    private String mMSGTitle;
    private String mMSGPrice;
    private String mMSGDec;
    private String mMSGImg;
    private String mMSGTargetUrl;

    /*
    *   info.title("test_order1")
                .orderTitle("订单号：7890")
                .price("￥128")
                .desc("2015早春新款高腰复古牛仔裙")
                .imageUrl("https://cbu01.alicdn.com/img/ibank/2020/965/899/21104998569_301332844.jpg")
                .itemUrl("https://detail.1688.com/offer/625787437908.html");*/

    public static Bundle getBundle(GoodsDetailItemBean goodsDetailItemBean) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_CHAT_MSG_HEAD, ResUtils.getString(R.string.tip_msg_looking_goods));
        bundle.putString(BundleKey.ARG_CHAT_MSG_TITLE, goodsDetailItemBean.getTitle());
        bundle.putString(BundleKey.ARG_CHAT_MSG_PRICE, "$"+goodsDetailItemBean.getDollarPrice());
        bundle.putString(BundleKey.ARG_CHAT_MSG_DEC, goodsDetailItemBean.getSubTitle());
        bundle.putString(BundleKey.ARG_CHAT_MSG_IMAGE, goodsDetailItemBean.getImages().get(0));

        if(goodsDetailItemBean.getSource() == Constants.TYPE_PLATFORM_TB){
            bundle.putString(BundleKey.ARG_CHAT_MSG_TARGET_URL, goodsDetailItemBean.getUrl());
        } else {
            bundle.putString(BundleKey.ARG_CHAT_MSG_TARGET_URL,
                    VMallUtils.get1688GoodsUrl(goodsDetailItemBean.getItemId() + ""));
        }

        bundle.putInt(BundleKey.ARG_PAGE_LAUNCH_TYPE, PAGE_FROM_GOODS);
        bundle.putString(Config.EXTRA_TITLE_NAME, ResUtils.getString(R.string.label_title_chat));
        return bundle;
    }

    public static Bundle getBundle(OrderBean orderBean) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_CHAT_MSG_HEAD, ResUtils.getString(R.string.tip_msg_looking_order));
        bundle.putString(BundleKey.ARG_CHAT_MSG_TITLE,
                String.format(ResUtils.getString(R.string.format_order_sn,orderBean.getOrderSn())));
        bundle.putString(BundleKey.ARG_CHAT_MSG_PRICE, "$"+orderBean.getPayAmount());
        bundle.putString(BundleKey.ARG_CHAT_MSG_IMAGE, orderBean.getOrderItemList().get(0).getProductPic());
        bundle.putString(BundleKey.ARG_CHAT_MSG_TARGET_URL, RouterManager.generateOrderUrl(orderBean.getId()));


        bundle.putInt(BundleKey.ARG_PAGE_LAUNCH_TYPE, PAGE_FROM_ORDER);
        bundle.putString(Config.EXTRA_TITLE_NAME, ResUtils.getString(R.string.label_title_chat));
        return bundle;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.hd_activity_chat);
        instance = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //IM服务号
            toChatUsername = bundle.getString(Config.EXTRA_SERVICE_IM_NUMBER);
            Bundle bundleExtra = bundle.getBundle(Config.EXTRA_BUNDLE);

            if(bundleExtra != null){
                mPageFrom = bundleExtra.getInt(BundleKey.ARG_PAGE_LAUNCH_TYPE);
                mMSGHeader = bundleExtra.getString(BundleKey.ARG_CHAT_MSG_HEAD);
                mMSGTitle = bundleExtra.getString(BundleKey.ARG_CHAT_MSG_TITLE);
                mMSGDec = bundleExtra.getString(BundleKey.ARG_CHAT_MSG_DEC);
                mMSGPrice = bundleExtra.getString(BundleKey.ARG_CHAT_MSG_PRICE);
                mMSGImg = bundleExtra.getString(BundleKey.ARG_CHAT_MSG_IMAGE);
                mMSGTargetUrl = bundleExtra.getString(BundleKey.ARG_CHAT_MSG_TARGET_URL);
            }

        }

        //可以直接new ChatFragment使用
        String chatFragmentTAG = "chatFragment";
        chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentByTag(chatFragmentTAG);
        if (chatFragment == null) {
            chatFragment = new CustomChatFragment();
            //传入参数
            chatFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment, chatFragmentTAG).commit();
            sendOrderMessage();
        }
    }


    /**
     * 发送订单消息
     * <p>
     * 不发送则是saveMessage
     */
    private void sendOrderMessage() {
        OrderInfo info = ContentFactory.createOrderInfo(null);
        info.title(mMSGTitle)
                .price(mMSGPrice)
                .desc(mMSGDec)
                .imageUrl(mMSGImg)
                .itemUrl(mMSGTargetUrl);
        Message message = Message.createTxtSendMessage(mMSGHeader, toChatUsername);
        message.addContent(info);
        ChatClient.getInstance().chatManager().sendMessage(message);
    }
//
//    /**
//     * 发送轨迹消息
//     * @param selectedIndex
//     */
//    private void sendTrackMessage(int selectedIndex) {
//        Message message = Message.createTxtSendMessage(getMessageContent(selectedIndex), toChatUsername);
//        message.addContent(DemoMessageHelper.createVisitorTrack(this, selectedIndex));
//        ChatClient.getInstance().chatManager().sendMessage(message);
//    }

//    private String getMessageContent(int selectedIndex){
//        switch (selectedIndex){
//            case 1:
//                return getResources().getString(R.string.em_example1_text);
//            case 2:
//                return getResources().getString(R.string.em_example2_text);
//            case 3:
//                return getResources().getString(R.string.em_example3_text);
//            case 4:
//                return getResources().getString(R.string.em_example4_text);
//        }
//        // 内容自己随意定义。
//        return "";
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
        instance = null;
        EaseHelper.getInstance().logout();
//        ChatClient.getInstance().chatManager().cancelVideoConferences(toChatUsername, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(Config.EXTRA_SERVICE_IM_NUMBER);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (chatFragment != null) {
            chatFragment.onBackPressed();
        }
        super.onBackPressed();
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
