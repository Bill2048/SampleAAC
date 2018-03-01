package com.chaoxing.sample.aac.okhttp;

import com.chaoxing.sample.aac.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by HUWEI on 2018/2/26.
 */

public final class PersistentCookieJar implements CookieJar {

    private CookieHandler mCookieHandler;

    public PersistentCookieJar(CookieHandler cookieHandler) {
        this.mCookieHandler = cookieHandler;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (mCookieHandler == null) {
            return;
        }
        mCookieHandler.save(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = new ArrayList<>();

        if (mCookieHandler != null) {
            List<Cookie> cookieList = mCookieHandler.get(url);
            if (!CollectionUtils.isEmpty(cookieList)) {
                cookies.addAll(cookieList);
            }
        }

        return cookies;
    }

}