package com.chaoxing.sample.aac.okhttp;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.chaoxing.sample.aac.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by HUWEI on 2018/2/27.
 */

public class WebViewCookieHandler implements CookieHandler {

    @Override
    public void save(HttpUrl url, List<Cookie> cookies) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        for (Cookie cookie : cookies) {
            cookieManager.setCookie(url.url().toString(), cookie.toString());
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    public List<Cookie> get(HttpUrl url) {
        List<Cookie> cookies = new ArrayList<>();

        CookieManager cookieManager = CookieManager.getInstance();
        String setCookies = cookieManager.getCookie(url.url().toString());

        if (!TextUtils.isBlank(setCookies)) {
            String[] setCookieArr = setCookies.split(";");
            for (String setCookie : setCookieArr) {
                Cookie cookie = Cookie.parse(url, setCookie);
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }
        }

        return cookies;
    }

}
