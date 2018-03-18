package com.chaoxing.sample.aac.accounts;

import android.content.Context;

import com.chaoxing.sample.aac.api.APIService;
import com.chaoxing.sample.aac.okhttp.TokenInterceptor;
import com.chaoxing.sample.aac.retrofit.Result;
import com.chaoxing.sample.aac.retrofit.ResultConverter;
import com.chaoxing.sample.aac.retrofit.RetrofitFactory;
import com.chaoxing.sample.aac.rxjava.SimpleObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by HUWEI on 2018/2/1.
 */

public class AccountAuthenticator {

    private static AccountAuthenticator sInstance;

    private Context mContext;

    public static AccountAuthenticator get(Context context) {
        if (sInstance == null) {
            synchronized (AccountAuthenticator.class) {
                if (sInstance == null) {
                    sInstance = new AccountAuthenticator(context);
                }
            }
        }
        return sInstance;
    }

    public AccountAuthenticator() {
    }

    private AccountAuthenticator(Context context) {
        mContext = context.getApplicationContext();
    }

    public void passportAuthentication() {

    }

    public void confirmCredentials(final Credential credential) {

//        Call<ResponseBody> call = apiService.signInToPassport(credential.getAccount(), credential.getCode(), 0, true);

        RetrofitFactory.get()
                .setBaseUrl(APIService.DOMAIN_PASSPORT2_CHAOXING_COM)
                .setInterceptors(new TokenInterceptor())
                .setConverter(new ResultConverter<Result<User>>() {
                    @Override
                    public Result<User> convert(ResponseBody value) {
                        return new Result<>();
                    }
                }).create()
                .create(APIService.class)
                .passportAuthentication(credential.getAccount(), credential.getCode(), 1, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<Result<User>>() {
                    @Override
                    public void onNext(Result<User> userResult) {
                        super.onNext(userResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

}
