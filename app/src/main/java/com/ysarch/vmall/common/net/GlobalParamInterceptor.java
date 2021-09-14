package com.ysarch.vmall.common.net;

import android.text.TextUtils;

import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.utils.VMallUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 全局参数拦截器
 */

public class GlobalParamInterceptor implements Interceptor {

    private static final String TAG = "GlobalParamInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

//        Map paramsForAll = new HashMap();
////        if (UserInfoManager.isLogin()) {
////            paramsForAll.put("token", UserInfoManager.getUser().getToken());
////            paramsForAll.put("user_id", UserInfoManager.getUser().getId());
////        }
//        paramsForAll.put("platform", "1");
//
//        paramsForAll.put("channel", AppContext.getsInstance().getChannelId());
//        paramsForAll.put("version", VMallBuildConfig.getVersionName());
//        paramsForAll.put("mac", AppContext.getsInstance().getDeviceId());
//        paramsForAll.put("device", AppContext.getsInstance().getDeviceName());

        Request requestOriginal = chain.request();
        if ("GET".equals(requestOriginal.method())) {
            HttpUrl url = requestOriginal.url();
//            HttpUrl newUrl = addParamsForQuery(url, paramsForAll);

            Request.Builder builder = requestOriginal.newBuilder();

            addHeaders(builder);

            requestOriginal = builder.url(url).build();
        } else { // post delete patch
            Request.Builder requestBuilder = requestOriginal.newBuilder();
            FormBody.Builder newFormBody = new FormBody.Builder();
            RequestBody requestBody = requestOriginal.body();

            Map paramsForAll = new HashMap();

            if (requestBody != null && requestBody instanceof FormBody) {
                FormBody oidFormBody = (FormBody) requestOriginal.body();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    String key = oidFormBody.encodedName(i);
                    paramsForAll.put(key, oidFormBody.value(i));
//                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                }
            }

            if(paramsForAll.size() > 0){
                requestBuilder.method(requestOriginal.method(), addParamsForForm(paramsForAll));
                addHeaders(requestBuilder);
                requestOriginal = requestBuilder.build();
            } else {
                //针对部分参数是数组形式，需直接走requestBody请求，使用Retrofit的Call，而不是flowable
                requestBuilder.method(requestOriginal.method(), requestBody);
                addHeaders(requestBuilder);
                requestOriginal = requestBuilder.build();
            }
        }

        return chain.proceed(requestOriginal);
    }


    private void addHeaders(Request.Builder builder) {
        if (UserInfoManager.isLogin()) {
            builder.addHeader("Authorization", UserInfoManager.getUser().getTokenHeader()
                    + UserInfoManager.getUser().getToken());
        }
//        builder.addHeader("lan", AppContext.getsInstance().getLanguageEntity().getShortCut());
        builder.addHeader("play", "0");
        builder.addHeader("mac", AppContext.getsInstance().getDeviceId());
        builder.addHeader("channel", AppContext.getsInstance().getChannelId());
        builder.addHeader("Content-Type","application/json");
//        builder.addHeader("lan",);zh,en,km
        String lan = AppContext.getsInstance().getLanguageEntity().getShortCut();
        builder.addHeader("lan",lan);

    }

    private RequestBody addParamsForForm(Map<String, Object> map) {
        JSONObject requestData = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                Object value = entry.getValue();
//                if(value instanceof String){
//                    requestData.put(entry.getKey(), VMallUtils.decodeString((String) value));
//                } else {
//                    requestData.put(entry.getKey(), entry.getValue());
//                }
                requestData.put(entry.getKey(), entry.getValue());

            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json"), requestData.toString());
    }

    private FormBody.Builder addParamsForForm(FormBody.Builder builder, Map<String, Object> map) {

        if (map == null || map.size() == 0) return builder;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && !TextUtils.isEmpty("" + entry.getValue())) {
                builder.add(entry.getKey(), "" + entry.getValue());
            }
        }
        return builder;
    }

    private HttpUrl addParamsForQuery(HttpUrl httpUrl, Map<String, Object> map) {

        if (map == null || map.size() == 0) return httpUrl;

        HttpUrl.Builder builder = httpUrl.newBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && !TextUtils.isEmpty("" + entry.getValue())) {
                builder.removeAllQueryParameters(entry.getKey());
                builder.addEncodedQueryParameter(entry.getKey(), "" + entry.getValue());
            }
        }
        return builder.build();
    }

}
