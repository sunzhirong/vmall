package com.ysarch.vmall.page.chat.chatrow;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.adapter.MessageAdapter;
import com.hyphenate.helpdesk.easeui.widget.chatrow.ChatRow;
import com.hyphenate.helpdesk.model.MessageHelper;
import com.hyphenate.helpdesk.model.OrderInfo;
import com.hyphenate.helpdesk.util.Log;
import com.ysarch.vmall.R;
import com.yslibrary.utils.ToastUtils;

public class ChatRowOrderV2 extends ChatRow {
    ImageView mImageView;
    TextView mTVTitle;
    TextView mTVPrice;
    TextView mTVChatContent;

    public ChatRowOrderV2(Context context, Message message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);

    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct() == Message.Direct.RECEIVE ? R.layout.hd_row_received_message : R.layout.em_row_sent_order_v2, this);
    }

    @Override
    protected void onFindViewById() {
        if (message.direct() == Message.Direct.SEND) {
//            mTVTitle = (TextView) findViewById(R.id.tv_description);
//            mTVPrice = (TextView) findViewById(R.id.tv_price);
//            mImageView = (ImageView) findViewById(R.id.iv_picture);
//            mTVChatContent = (TextView) findViewById(R.id.tv_chatcontent);
//            mBtnSend = (Button) findViewById(R.id.button_send);
//            mTVHeader = (TextView) findViewById(R.id.tv_title);
            mTVChatContent = findViewById(R.id.tv_content_order_chat_item);
            mImageView = findViewById(R.id.iv_cover_order_chat_item);
            mTVTitle = findViewById(R.id.tv_title_order_chat_item);
            mTVPrice = findViewById(R.id.tv_price_order_chat_item);
        }
    }

    @Override
    protected void onUpdateView() {
    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.body();
        if (message.direct() == Message.Direct.RECEIVE) {
            //设置内容
            mTVChatContent.setText(txtBody.getMessage());
            //设置长按事件监听
//            mTVChatContent.setOnLongClickListener(new OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    activity.startActivityForResult(new Intent(activity, ContextMenuActivity.class)
//                            .putExtra("position", position)
//                            .putExtra("type", Message.Type.TXT.ordinal()), CustomChatFragment.REQUEST_CODE_CONTEXT_MENU);
//                    return true;
//                }
//            });
            return;
        }
        OrderInfo orderInfo = MessageHelper.getOrderInfo(message);
        if (orderInfo == null) {
            return;
        }
        mTVPrice.setText(orderInfo.getPrice());
        mTVTitle.setText(orderInfo.getTitle());
        String imageUrl = orderInfo.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)){
            Glide.with(context).load(imageUrl).apply(RequestOptions.placeholderOf(com.hyphenate.helpdesk.R.drawable.hd_default_image).diskCacheStrategy(DiskCacheStrategy.ALL)).into(mImageView);
        }

        message.setMessageStatusCallback(new Callback() {
            @Override
            public void onSuccess() {
                ChatClient.getInstance().chatManager().getConversation(message.to());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter instanceof MessageAdapter) {
                            ((MessageAdapter) adapter).refresh();
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "error:" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

//        mBtnSend.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (message.status() == Message.Status.INPROGRESS){
//                    ToastUtils.showShortToast(context, R.string.em_notice_sending);
//                    return;
//                }
//
//                ChatClient.getInstance().chatManager().resendMessage(message);
//            }
//        });


    }

    @Override
    protected void onBubbleClick() {

    }
}
