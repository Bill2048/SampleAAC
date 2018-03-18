package com.chaoxing.sample.aac.retrofit;

import android.support.annotation.NonNull;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class Result<T> {

    public static final int STATUS_ERROR = 0;
    public static final int STATUS_SUCCESS = 1;

    private int status;
    private T data;
    private String message;
    private String raw;  // 原始数据

    public static <T> Result<T> success(@NonNull T data) {
        return success(data, null, null);
    }

    public static <T> Result<T> success(@NonNull T data, String raw) {
        return success(data, null, raw);
    }

    public static <T> Result<T> success(@NonNull T data, String message, String raw) {
        Result<T> result = new Result<>();
        result.setStatus(STATUS_SUCCESS);
        result.setData(data);
        result.setMessage(message);
        result.setRaw(raw);
        return result;
    }

    public static <T> Result<T> error(String message) {
        return error(message, null, null);
    }

    public static <T> Result<T> error(String message, String raw) {
        return error(message, raw, null);
    }

    public static <T> Result<T> error(String message, String raw, T data) {
        Result<T> result = new Result<>();
        result.setStatus(STATUS_ERROR);
        result.setData(data);
        result.setMessage(message);
        result.setRaw(raw);
        return result;
    }

    public boolean isSuccessful() {
        return status == STATUS_SUCCESS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
