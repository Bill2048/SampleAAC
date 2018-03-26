package com.chaoxing.sample.aac;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.CookieSyncManager;

import com.chaoxing.sample.aac.okhttp.OkHttp;

import timber.log.Timber;

/**
 * Created by HUWEI on 2018/2/26.
 */

public class BaseApplication extends Application {

    private String userAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        initCookieManager(this);
        OkHttp.initUserAgent(this);
    }

    private void initCookieManager(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context.getApplicationContext());
        }
    }

}
