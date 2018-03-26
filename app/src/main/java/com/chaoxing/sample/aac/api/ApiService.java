package com.chaoxing.sample.aac.api;

import android.arch.lifecycle.LiveData;

import com.chaoxing.sample.aac.accounts.PassportResult;
import com.chaoxing.sample.aac.accounts.User;
import com.chaoxing.sample.aac.retrofit.ApiResponse;
import com.chaoxing.sample.aac.retrofit.Result;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by HUWEI on 2018/2/1.
 */

public interface ApiService {

    public static final String DOMAIN_PASSPORT2_CHAOXING_COM = "http://passport2.chaoxing.com/";


    @FormUrlEncoded
    @POST("xxt/loginregisternew")
    Observable<Result<User>> passportAuthentication(@Field("uname") String account, @Field("code") String code, @Field("loginType") int loginType, @Field("roleSelect") boolean roleSelect);

    @FormUrlEncoded
    @POST("xxt/loginregisternew")
    Flowable<Result<PassportResult>> passport(@Field("uname") String account, @Field("code") String code, @Field("loginType") int loginType, @Field("roleSelect") boolean roleSelect);

    @FormUrlEncoded
    @POST("xxt/loginregisternew")
    LiveData<ApiResponse<Result<PassportResult>>> passport2(@Field("uname") String account, @Field("code") String code, @Field("loginType") int loginType, @Field("roleSelect") boolean roleSelect);

}
