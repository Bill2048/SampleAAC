package com.chaoxing.sample.aac.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.chaoxing.sample.aac.api.ApiExecutors;
import com.chaoxing.sample.aac.retrofit.ApiResponse;
import com.chaoxing.sample.aac.retrofit.ApiResult;
import com.chaoxing.sample.aac.util.AbsentLiveData;
import com.chaoxing.sample.aac.util.Objects;

/**
 * Created by bighu on 2018/3/18.
 */

public abstract class NetworkBoundResult<RequestType, ResultType> {

    private final ApiExecutors appExecutors;
    private final boolean saveDb;

    private final MediatorLiveData<ApiResult<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResult(ApiExecutors appExecutors, boolean saveDb) {
        this.appExecutors = appExecutors;
        this.saveDb = saveDb;
        result.setValue(ApiResult.loading((ResultType) null));
        final LiveData<ResultType> dbSource = saveDb ? loadFromDb() : AbsentLiveData.<ResultType>create();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                result.removeSource(dbSource);
                if (shouldFetch(resultType)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            setValue(ApiResult.success(resultType));
                        }
                    });
                }
            }
        });
    }

    private void setValue(ApiResult<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                setValue(ApiResult.loading(resultType));
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);

                if (response.isSuccessful()) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (saveDb) {
                                saveCallResult(processResponse(response));
                            }
                            appExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (saveDb) {
                                        result.addSource(loadFromDb(), new Observer<ResultType>() {
                                            @Override
                                            public void onChanged(@Nullable ResultType resultType) {
                                                setValue(ApiResult.success(resultType));
                                            }
                                        });
                                    } else {
                                        setValue(ApiResult.success(requestTypeTo(response)));
                                    }
                                }
                            });
                        }
                    });

                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            setValue(ApiResult.error(response.errorMessage, resultType));
                        }
                    });
                }

            }
        });
    }

    protected void onFetchFailed() {

    }

    public LiveData<ApiResult<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    protected ResultType requestTypeTo(ApiResponse<RequestType> response) {
        return (ResultType) response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@Nullable RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
