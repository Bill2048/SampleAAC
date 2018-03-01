package com.chaoxing.sample.aac.okhttp;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by HUWEI on 2018/2/27.
 */

public interface CookieHandler {

    void save(HttpUrl url, List<Cookie> cookies);

    List<Cookie> get(HttpUrl url);

}
