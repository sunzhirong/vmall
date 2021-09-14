package cn.droidlover.xdroidmvp.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import cn.droidlover.xdroidmvp.net.ApiException;
import cn.droidlover.xdroidmvp.net.ErrCodeMessage;
import cn.droidlover.xdroidmvp.net.SimpleResponse;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jiang on 2017/3/6.
 */

public class CheckGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson mGson;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    CheckGsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.mGson = new Gson();
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        SimpleResponse result = mGson.fromJson(response, SimpleResponse.class);

        if (!ErrCodeMessage.isSuccess(result.code)) {
            value.close();
            throw new ApiException(result.code, result.message);
        }

        MediaType type = value.contentType();
        Charset charset = type != null ? type.charset(UTF_8) : UTF_8;
        InputStream is = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(is, charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

//    @Override
//    public T convert(ResponseBody value) throws IOException {
//        String response = value.string();
//        HttpResult result = mGson.fromJson(response, HttpResult.class);
//        int responseCode = result.getResponsecode();
//        if (responseCode==0) {
//            value.close();
//            throw new ApiException(responseCode, ApiError.getErrorMessage(responseCode));
//        }
//        MediaType type = value.contentType();
//        Charset charset = type != null ? type.charset(UTF_8) : UTF_8;
//        InputStream is = new ByteArrayInputStream(response.getBytes());
//        InputStreamReader reader = new InputStreamReader(is, charset);
//        JsonReader jsonReader = mGson.newJsonReader(reader);
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
//    }

}
