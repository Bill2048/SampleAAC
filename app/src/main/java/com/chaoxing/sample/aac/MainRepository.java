package com.chaoxing.sample.aac;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chaoxing.sample.aac.accounts.PassportResult;
import com.chaoxing.sample.aac.api.ApiExecutors;
import com.chaoxing.sample.aac.api.ApiService;
import com.chaoxing.sample.aac.okhttp.TokenInterceptor;
import com.chaoxing.sample.aac.repository.NetworkBoundResult;
import com.chaoxing.sample.aac.retrofit.ApiResponse;
import com.chaoxing.sample.aac.retrofit.ApiResult;
import com.chaoxing.sample.aac.retrofit.Result;
import com.chaoxing.sample.aac.retrofit.ResultConverter;
import com.chaoxing.sample.aac.retrofit.RetrofitFactory;

import okhttp3.ResponseBody;

/**
 * Created by HUWEI on 2018/3/20.
 */

public class MainRepository {

    private static final MainRepository INSTANCE = new MainRepository();

    private MainRepository() {

    }

    public static MainRepository get() {
        return INSTANCE;
    }

    public LiveData<ApiResult<Result<PassportResult>>> go(final String a, final String b) {
        return new NetworkBoundResult<Result<PassportResult>, Result<PassportResult>>(ApiExecutors.get(), false) {

            @Override
            protected void saveCallResult(@Nullable Result<PassportResult> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable Result<PassportResult> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Result<PassportResult>> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Result<PassportResult>>> createCall() {
                return RetrofitFactory.get()
                        .setBaseUrl(ApiService.DOMAIN_PASSPORT2_CHAOXING_COM)
                        .setInterceptors(new TokenInterceptor())
                        .setConverter(new ResultConverter<Result<PassportResult>>() {

                            @Override
                            public Result<PassportResult> convert(ResponseBody value) {
                                Result result = new Result();
                                try {
                                    String raw = value.string();
                                    result.setRaw(raw);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return result;
                            }

                        })
                        .createWithLiveData().create(ApiService.class)
                        .passport2(a, b, 1, true);
            }
        }.asLiveData();

    }
}
