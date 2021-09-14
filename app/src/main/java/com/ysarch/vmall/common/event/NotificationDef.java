package com.ysarch.vmall.common.event;

/**
 * Created by fysong on 15/08/2020.
 */

public class NotificationDef {

    public static final int EVENT_USER_ACCOUNT_CHANGE = 1;
    public static final int EVENT_USER_INFO_CHANGE = 2;

    public static final int EVENT_MERCHANT_INFO_UPDATE = 3;
    public static final int EVENT_ORDER_COUNT_UPDATE = 4;
    public static final int EVENT_COMMENT_COUNT_UPDATE = 5;


    public static final int EVENT_ORDER_CANCEL = 6;
    public static final int EVENT_ORDER_DELETE = 7;
    public static final int EVENT_ORDER_PAY_FINISH = 8;

    public static final int EVENT_KEYWORD_CHANGED = 9;
    public static final int EVENT_ORDER_STATUS_CHANGED = 10;
    public static final int EVENT_MSG_NEW = 11;
    public static final int EVENT_MSG_NEW_NONE = 12;
}