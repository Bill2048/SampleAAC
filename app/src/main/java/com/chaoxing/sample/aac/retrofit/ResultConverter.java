package com.chaoxing.sample.aac.retrofit;

import okhttp3.ResponseBody;

/**
 * Created by HUWEI on 2018/3/1.
 */

public interface ResultConverter<T> {

    T convert(ResponseBody value);

}
