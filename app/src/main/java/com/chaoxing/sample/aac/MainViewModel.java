package com.chaoxing.sample.aac;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.chaoxing.sample.aac.accounts.Credential;
import com.chaoxing.sample.aac.accounts.PassportResult;
import com.chaoxing.sample.aac.retrofit.ApiResult;
import com.chaoxing.sample.aac.retrofit.Result;
import com.chaoxing.sample.aac.util.AbsentLiveData;

/**
 * Created by HUWEI on 2018/3/20.
 */

public class MainViewModel extends AndroidViewModel {


    private final MutableLiveData<Credential> query = new MutableLiveData<>();
    private LiveData<ApiResult<Result<PassportResult>>> result;

    public MainViewModel(@NonNull Application application) {
        super(application);
        result = Transformations.switchMap(query, new Function<Credential, LiveData<ApiResult<Result<PassportResult>>>>() {
            @Override
            public LiveData<ApiResult<Result<PassportResult>>> apply(Credential input) {
                if (input == null) {
                    return AbsentLiveData.create();
                } else {
                    return MainRepository.get().go(input.getAccount(), input.getCode());
                }
            }
        });
    }

//    public MainViewModel(Application application) {
//        this.credential = credential;

//    }

    public LiveData<ApiResult<Result<PassportResult>>> getResult() {
        return result;
    }

    public void singin(Credential credential) {
        query.setValue(credential);
    }
}
