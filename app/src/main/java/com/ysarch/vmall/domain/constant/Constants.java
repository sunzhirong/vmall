package com.ysarch.vmall.domain.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by fysong on 2020-07-12
 **/
public class Constants {

    public static final int SHIPPING_METHOD_SELF = 1;//到店自取
    public static final int SHIPPING_METHOD_DELIVERY = 2;//快递配送

    /**
     * 每页数据数
     */
    public static final int COUNT_PER_PAGE = 10;
    public static final int COUNT_PER_PAGE_GRID = 20;

    public static final long AUTH_COUNT_TIME = 60000;

    public static final int ID_LAN_EN = 0;
    public static final int ID_LAN_KM = 1;
    public static final int ID_LAN_ZH = 2;


    public static final int MODE_ADDRESS_EDIT = 0;
    public static final int MODE_ADDRESS_SELECT = 1;


    public static final int STATUS_ORDER_ALL = -1;                          //全部
    public static final int STATUS_ORDER_UNPAY = 0;                         //待付款
    public static final int STATUS_ORDER_DELIVER_WAITING = 1;               //采购中
    public static final int STATUS_ORDER_DELIVERED = 2;                     //待收货
    public static final int STATUS_ORDER_COMPLETE = 3;                      //已完成
    public static final int STATUS_ORDER_CLOSED = 4;                        //已关闭
    public static final int STATUS_ORDER_AUDIT_WAITING = 5;                 //待审核

    //todo 暂时不知道状态为多少
    public static final int STATUS_ORDER_IN_STOCK = 5;                      //备货中

//    public static final int STATUS_ORDER_INVALID = 5;                       //无效订单
//    public static final int STATUS_ORDER_AUDIT_WAITING = 6;                 //待审核
//    public static final int STATUS_ORDER_SUPPLEMENTARY = 7;                 //待补款
//    public static final int STATUS_ORDER_WAREHOUSE_CHINA = 8;               //在中国仓库
//    public static final int STATUS_ORDER_WAREHOUSE_OVERSEA = 9;             //已发往海外仓库
//    public static final int STATUS_ORDER_WAREHOUSE_OVERSEA_SIGNED = 10;     //海外仓已签收
//    public static final int STATUS_ORDER_USER_SIGNED = 11;                  //用户签收


    public static final int STATUS_COUPON_UNUSE = 0;                //未使用
    public static final int STATUS_COUPON_USED = 1;                 //已使用
    public static final int STATUS_COUPON_OVER_TIME = 2;            //已过期
    public static final int TYPE_PLATFORM_TB = 0;
    public static final int TYPE_PLATFORM_JD = 1;
    public static final int TYPE_PLATFORM_PDD = 2;
    public static final int TYPE_PLATFORM_1688 = 3;
    /*  发货方式 */
    public static final int TYPE_DELIVERY_SEND_ON_ALL_READY = 0;            //到齐发货
    public static final int TYPE_DELIVERY_SEND_ON_ARRIVE = 1;               //货到即发

    /*  运输方式 */
    public static final int TYPE_TRANSPORT_LAND = 0;                        //陆运
    public static final int TYPE_TRANSPORT_SEA = 1;                         //海运
    public static final int TYPE_TRANSPORT_FRIGHT = 2;                      //空运

    public static final int STATUS_RECHARGE_AUDIT_WAITING = 0;              //待审核
    public static final int STATUS_RECHARGE_SUCC = 1;                       //充值成功
    public static final int STATUS_RECHARGE_REJECT = 2;                     //驳回

    //1:在中国仓库;2:已发往海外仓;3:海外仓已签收
    public static final int STATUS_LOGISTICS_CN = 1;
    public static final int STATUS_LOGISTICS_OVERSEA = 2;
    public static final int STATUS_LOGISTICS_OVERSEA_SIGN = 3;


    public static final int TYPE_MSG_ORDER = 1;                             // 订单消息
    public static final int TYPE_MSG_RECHARGE = 2;                          // 充值消息
    public static final int TYPE_MSG_DELIVERY = 3;                          // 提货消息


    @IntDef({TYPE_MSG_ORDER, TYPE_MSG_RECHARGE, TYPE_MSG_DELIVERY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MsgType {
    }

    @IntDef({STATUS_COUPON_UNUSE, STATUS_COUPON_USED, STATUS_COUPON_OVER_TIME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CouponStatus {
    }


    public static final int OPERATETYPE_SEARCH_BY_KEYWORD_SUBPAGE = 0;

    public static final int OPERATETYPE_RECHARGE = 1;
    public static final int OPERATETYPE_CUSTOMER_SERVICE = 2;
    public static final int OPERATETYPE_HELP = 3;
    public static final int OPERATETYPE_SEARCH_BY_KEYWORD = 4;
    public static final int OPERATETYPE_GOODS_H5 = 5;
    public static final int OPERATETYPE_H5 = 6;
}

