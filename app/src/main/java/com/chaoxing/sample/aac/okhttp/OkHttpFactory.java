package com.chaoxing.sample.aac.okhttp;

import com.chaoxing.sample.aac.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class OkHttpFactory {

    private OkHttpFactory() {
    }

    public static OkHttpClient create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new HeaderInterceptor());
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.cookieJar(new PersistentCookieJar(new WebViewCookieHandler()));
        OkHttpUtils.setTrustHttpClient(builder);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }


}
