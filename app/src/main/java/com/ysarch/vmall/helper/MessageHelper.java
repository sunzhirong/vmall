package com.ysarch.vmall.helper;

import android.os.Message;

/**
 * Created by fysong on 2017/11/7.
 */

public class MessageHelper {

    public static Message obtain(int what) {
        Message msg = Message.obtain();
        msg.what = what;
        return msg;
    }

    public static Message obtain(int what, int arg1) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        return msg;
    }

    public static Message obtain(int what, int arg1, int arg2) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        return msg;
    }

    public static Message obtain(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        return msg;
    }
}
