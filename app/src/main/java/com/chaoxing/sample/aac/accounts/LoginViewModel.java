package com.chaoxing.sample.aac.accounts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chaoxing.sample.aac.api.APIService;
import com.chaoxing.sample.aac.okhttp.TokenInterceptor;
import com.chaoxing.sample.aac.retrofit.Result;
import com.chaoxing.sample.aac.retrofit.ResultConverter;
import com.chaoxing.sample.aac.retrofit.RetrofitFactory;
import com.google.gson.Gson;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by HUWEI on 2018/3/13.
 */

public class LoginViewModel extends ViewModel {

    private Fragment fragment;
    private final MediatorLiveData<Result<PassportResult>> passportResult = new MediatorLiveData<>();

    public LoginViewModel() {

    }

    @MainThread
    public LiveData<Result<PassportResult>> passport(Credential credential) {
        Flowable<Result<PassportResult>> observable = RetrofitFactory.get()
                .setBaseUrl(APIService.DOMAIN_PASSPORT2_CHAOXING_COM)
                .setInterceptors(new TokenInterceptor())
                .setConverter(new ResultConverter<Result<PassportResult>>() {
                    @Override
                    public Result<PassportResult> convert(ResponseBody value) {
                        Result<PassportResult> result = new Result<>();
                        try {
                            String raw = value.string();
                            PassportResult passportResult = new Gson().fromJson(raw, PassportResult.class);
                            if (passportResult != null) {
                                if (passportResult.isStatus()) {
                                    result.setStatus(Result.STATUS_SUCCESS);
                                    result.setData(passportResult);
                                    result.setMessage(passportResult.getMes());
                                } else {
                                    result.setStatus(Result.STATUS_ERROR);
                                    result.setMessage(passportResult.getMes());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return result;
                    }
                }).create()
                .create(APIService.class).passport(credential.getAccount(), credential.getCode(), 1, true);

        LiveData<Result<PassportResult>> products = LiveDataReactiveStreams.fromPublisher(observable);

        // observe the changes of the products from the database and forward them
        passportResult.addSource(products, new Observer<Result<PassportResult>>() {
            @Override
            public void onChanged(@Nullable Result<PassportResult> result) {
                if (result != null) {

                }
            }
        });
        return passportResult;
    }

}
