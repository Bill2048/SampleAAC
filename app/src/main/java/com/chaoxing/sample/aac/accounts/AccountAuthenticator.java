package com.chaoxing.sample.aac.accounts;

import android.content.Context;
import android.widget.Toast;

import com.chaoxing.sample.aac.APIService;
import com.chaoxing.sample.aac.retrofit.Result;
import com.chaoxing.sample.aac.retrofit.ResultConverter;
import com.chaoxing.sample.aac.retrofit.RetrofitFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void confirmCredentials(final Credential credential) {

//        Call<ResponseBody> call = apiService.signInToPassport(credential.getAccount(), credential.getCode(), 0, true);

        RetrofitFactory.create(APIService.DOMAIN_PASSPORT2_CHAOXING_COM, new ResultConverter<Result<User>>() {
            @Override
            public Result<User> convert(ResponseBody value) {
                return null;
            }
        }).create(APIService.class)
                .signInToPassport()
                .enqueue(new Callback<Result<User>>() {
                    @Override
                    public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                        try {
                            Toast.makeText(mContext, response.raw().body().string(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<User>> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
