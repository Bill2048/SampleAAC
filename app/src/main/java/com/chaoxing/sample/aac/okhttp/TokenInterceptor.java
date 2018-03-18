package com.chaoxing.sample.aac.okhttp;

import com.chaoxing.sample.aac.api.APIUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HUWEI on 2018/3/6.
 */

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        long time = System.currentTimeMillis();



        StringBuilder infBuilder = new StringBuilder();
        infBuilder.append("token=").append(APIUtils.TOKEN)
                .append("&_time=").append(1520307720294l)
                .append("&DESKey=").append(APIUtils.DESKey);

        String inf_enc = APIUtils.md5(infBuilder.toString());

        HttpUrl.Builder urlBuilder = original.url().newBuilder()
                .scheme(original.url().scheme())
                .host(original.url().host())
                .addQueryParameter("token", APIUtils.TOKEN)
                .addQueryParameter("_time", time + "")
                .addQueryParameter("inf_enc", inf_enc);

        Request request = original.newBuilder()
                .url(urlBuilder.build())
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }

}