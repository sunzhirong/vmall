package cn.droidlover.xdroidmvp.net;

/**
 * @author Morphine
 * @date 2018-04-25.
 * 响应错误
 */

public class ResponseError extends Throwable {

    public ResponseError(){}

    public ResponseError(String errorCode,String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    private String errorCode;

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
