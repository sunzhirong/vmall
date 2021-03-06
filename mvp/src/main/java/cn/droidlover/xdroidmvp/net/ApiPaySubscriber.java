//package cn.droidlover.xdroidmvp.net;
//
//import android.text.TextUtils;
//
//import com.google.gson.JsonParseException;
//import com.google.gson.JsonSyntaxException;
//
//import org.json.JSONException;
//
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//
//import cn.droidlover.xdroidmvp.log.XLog;
//import cn.droidlover.xdroidmvp.mvp.IView;
//import io.reactivex.subscribers.ResourceSubscriber;
//
//
///**
// * Created by wanglei on 2016/12/26.
// */
//
////public abstract class ApiSubscriber<T extends IModel> extends ResourceSubscriber<T> {
//public abstract class ApiPaySubscriber<T> extends ResourceSubscriber<T> {
//
//    private static final String TAG = "ApiSubscriber";
//    boolean isShowErrorMsg = true;
//    private boolean isUpload;
//    private IView view;
//    private boolean isNext = false;
//
//    public ApiPaySubscriber() {
//
//    }
//
//    public ApiPaySubscriber(IView iView) {
//        this.view = iView;
//        this.isShowErrorMsg = true;
//    }
//
//    public ApiPaySubscriber(IView iView, boolean isShowErrorMsg) {
//        this.view = iView;
//        this.isShowErrorMsg = isShowErrorMsg;
//    }
//
//    public ApiPaySubscriber(boolean isUpload, IView iView) {
//        this.view = iView;
//        this.isUpload = isUpload;
//    }
//
//    @Override
//    public void onNext(T t) {
//        isNext = true;
//        try {
//            onSuccess(t);
//        } catch (Exception e) {
//            e.printStackTrace();
//            onError(e);
//        }
//    }
//
//    public abstract void onSuccess(T t);
//
//    public abstract void onPayPwdError();
//
//    @Override
//    public void onError(Throwable e) {
//        isNext = false;
//        if (e == null) return;
//
//        NetError error = null;
//        if (!(e instanceof NetError)) {
//            if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
//                error = new NetError("网络超时，请求失败", NetError.NoConnectError);
//            } else if (e instanceof JSONException || e instanceof JsonParseException || e instanceof JsonSyntaxException) {
////                error = new NetError(e, NetError.ParseError);
//                error = new NetError(e, NetError.ParseError);
//            } else if (e instanceof ResponseError) {
//                ResponseError responseError = (ResponseError) e;
//                if ("1002".equals(responseError.getErrorCode())) {
//                    error = new NetError("账号已再其它地点登录，请重新登录", NetError.AuthError);
//                } else if ("2002".equals(responseError.getErrorCode())) {
//                    error = new NetError(responseError.getErrorMsg(), 2002);
//                } else if ("6002".equals(responseError.getErrorCode())) {
////                    view.showTs("权限不足");
////                    view.showTs("权限不足");
//                    view.closeSelf();
//                } else {
//                    error = new NetError(responseError.getErrorMsg(), NetError.OtherError);
//                }
//            } else if (e instanceof ApiException) {
//                ApiException apiException = (ApiException) e;
//                error = new NetError(apiException.getErrorMessage(), NetError.BusinessError);
//                if(apiException.getErrorCode()==203){
//                    //支付密码错误
//                    onPayPwdError();
//                }
//                XLog.d("apiException=>" + apiException.getErrorMessage());
//            } else {
//                error = new NetError(e, NetError.OtherError);
//            }
//        } else {
//            error = (NetError) e;
//        }
//        if (view != null)
//            view.dismissLoadingDialog();
//        if (useCommonErrorHandler() && XApi.getCommonProvider() != null) {
//            if (XApi.getCommonProvider().handleError(error)) {        //使用通用异常处理
//                return;
//            }
//        }
//        XLog.e(TAG, "the error is" + e.getMessage());
//        onFail(error);
//    }
//
//
//    protected void onFail(NetError error) {
//        if (isShowErrorMsg && view != null && !TextUtils.isEmpty(error.getMessage())) {
//            if (NetError.ParseError != error.getType()) {
//                view.showTs(error.getMessage());
//            }
//        }
//    }
//
//    @Override
//    public void onComplete() {
//        if (!isNext) {
//            onError(new Throwable(""));
//        }
//        isNext = false;
//        if (view != null && !isUpload)
//            view.dismissLoadingDialog();
//    }
//
//
//    protected boolean useCommonErrorHandler() {
//        return true;
//    }
//
//
//}
