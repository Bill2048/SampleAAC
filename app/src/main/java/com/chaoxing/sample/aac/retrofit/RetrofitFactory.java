package com.chaoxing.sample.aac.retrofit;

import com.chaoxing.sample.aac.okhttp.OkHttpFactory;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class RetrofitFactory {

    private RetrofitFactory() {
    }

    public static Retrofit create(String baseUrl) {
        return create(baseUrl, null, null);
    }

    public static Retrofit create(String baseUrl, ResultConverter<?> converter) {
        return create(baseUrl, converter, null);
    }

    public static Retrofit create(String baseUrl, Gson gson) {
        return create(baseUrl, null, gson);
    }

    private static Retrofit create(String baseUrl, ResultConverter<?> converter, Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpFactory.create());

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
