package com.chaoxing.sample.aac.retrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private ResultConverter<T> converter;

    public JsonResponseBodyConverter(ResultConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return converter.convert(value);
    }

}
