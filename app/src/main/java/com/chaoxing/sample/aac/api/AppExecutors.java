package com.chaoxing.sample.aac.api;

import java.util.concurrent.Executor;

/**
 * Created by bighu on 2018/3/18.
 */

public class AppExecutors {

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThrid;

    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThrid) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThrid = mainThrid;
    }

}
