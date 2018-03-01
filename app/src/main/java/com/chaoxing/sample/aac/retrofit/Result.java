package com.chaoxing.sample.aac.retrofit;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class Result<T> {

    public static final int STATUS_ERROR = 0;
    public static final int STATUS_SUCCESS = 1;

    private int status;
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
