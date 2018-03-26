package com.chaoxing.sample.aac.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by HUWEI on 2018/3/20.
 */

public class ApiResult<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public ApiResult(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResult<T> success(@Nullable T data) {
        return new ApiResult<>(Status.SUCCESS, data, null);
    }

    public static <T> ApiResult<T> error(String msg, @Nullable T data) {
        return new ApiResult<>(Status.ERROR, data, msg);
    }

    public static <T> ApiResult<T> loading(@Nullable T data) {
        return new ApiResult<>(Status.LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiResult<?> resource = (ApiResult<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
