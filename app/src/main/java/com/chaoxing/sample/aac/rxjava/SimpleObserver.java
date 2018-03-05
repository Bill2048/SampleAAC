package com.chaoxing.sample.aac.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by HUWEI on 2018/3/2.
 */

public class SimpleObserver<T> implements Observer<T> {

    protected Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
