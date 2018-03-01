package com.chaoxing.sample.aac.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HUWEI on 2018/2/27.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("User-Agent", OkHttp.getUserAgent())
                .header("Accept-Language", OkHttp.getLanguage())
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }

}
