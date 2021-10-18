package cn.droidlover.xdroidmvp.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wanglei on 2016/12/24.
 */

public class XInterceptor implements Interceptor {

    RequestHandler handler;

    public XInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            Response tmp = handler.onAfterRequest(response, chain);
            if (tmp != null) {
                return tmp;
            }

        }

        Log.d("retrofit", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers(),request.headers("Cookie")) );
        ResponseBody body = response.peekBody(1024*1024);
        String responseString = body.string();
        Log.d("retrofit", request.url()+"---------"+ responseString);
        return response;
    }
}
