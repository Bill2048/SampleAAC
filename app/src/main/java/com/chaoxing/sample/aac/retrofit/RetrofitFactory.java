package com.chaoxing.sample.aac.retrofit;

import com.chaoxing.sample.aac.okhttp.OkHttpFactory;
import com.google.gson.Gson;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class RetrofitFactory {

    private String baseUrl;
    private Interceptor[] interceptors;
    private ResultConverter<?> converter;
    private Gson gson;

    private RetrofitFactory() {
    }

    public static RetrofitFactory get() {
        return new RetrofitFactory();
    }

    public RetrofitFactory setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitFactory setInterceptors(Interceptor... interceptors) {
        this.interceptors = interceptors;
        return this;
    }


    public RetrofitFactory setConverter(ResultConverter<?> converter) {
        this.converter = converter;
        return this;
    }

    public RetrofitFactory setGson(Gson gson) {
        this.gson = gson;
        return this;
    }

    public Retrofit create() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpFactory.get().setInterceptors(interceptors).create());

        if (converter != null) {
            builder.addConverterFactory(JsonConverterFactory.create(converter));
        } else {
            if (gson != null) {
                builder.addConverterFactory(GsonConverterFactory.create(gson));
            } else {
                builder.addConverterFactory(GsonConverterFactory.create());
            }
        }

        return builder.build();
    }


}
