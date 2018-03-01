package com.chaoxing.sample.aac.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class JsonConverterFactory extends Converter.Factory {

    private Gson gson = new Gson();

    private ResultConverter<?> converter;

    public static JsonConverterFactory create(ResultConverter<?> converter) {
        return new JsonConverterFactory(converter);
    }

    private JsonConverterFactory(ResultConverter<?> converter) {
        this.converter = converter;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonResponseBodyConverter<>(converter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

}
