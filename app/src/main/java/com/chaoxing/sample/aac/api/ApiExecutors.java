package com.chaoxing.sample.aac.api;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by bighu on 2018/3/18.
 */

public class ApiExecutors {

    private static final ApiExecutors INSTANCE = new ApiExecutors();

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    public static ApiExecutors get() {
//        return INSTANCE;
        return new ApiExecutors();
    }

    private ApiExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    private ApiExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadExecutor = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadExecutor.post(command);
        }

    }

}
