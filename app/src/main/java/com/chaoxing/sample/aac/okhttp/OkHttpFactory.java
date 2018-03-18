package com.chaoxing.sample.aac.okhttp;

import com.chaoxing.sample.aac.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class OkHttpFactory {

    private Interceptor[] interceptors;

    private OkHttpFactory() {
    }

    public static OkHttpFactory get() {
        return new OkHttpFactory();
    }

    public OkHttpFactory setInterceptors(Interceptor... interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public OkHttpClient create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new HeaderInterceptor());
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.cookieJar(new PersistentCookieJar(new WebViewCookieHandler()));
        OkHttp.setTrustHttpClient(builder);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }

}
