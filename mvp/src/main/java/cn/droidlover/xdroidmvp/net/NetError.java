package cn.droidlover.xdroidmvp.net;

import java.util.List;

/**
 * Created by wanglei on 2016/12/24.
 */

public class NetError extends Exception {
    private Throwable exception;
    private int type = NoConnectError;

    public static final int ParseError = 0;   //数据解析异常
    public static final int NoConnectError = 1;   //无连接异常
    public static final int AuthError = 2;   //用户验证异常

    public static final int NoDataError = 3;   //无数据返回异常
    public static final int BusinessError = 4;   //业务异常
    public static final int OtherError = 5;   //其他异常


    public static String NoConnectError_Msg = "Network timeout, request failed";
    public static String AuthError_Msg = "The account has been logged in from other places, please log in again";
    public static String NoDataError_Msg = "数据为空";
    public static String InsufficientError_Msg = "Insufficient authority";
    public static String NetError_Msg = "网络未连接(104)";



    public NetError(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetError(String detailMessage, int type) {
        super(detailMessage);
        this.type = type;
    }

    @Override
    public String getMessage() {
        if (exception != null) return exception.getMessage();
        return super.getMessage();
    }

    public int getType() {
        return type;
    }
}
