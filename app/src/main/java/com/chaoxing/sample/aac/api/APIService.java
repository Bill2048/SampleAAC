package com.chaoxing.sample.aac.api;

import com.chaoxing.sample.aac.accounts.User;
import com.chaoxing.sample.aac.retrofit.Result;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by HUWEI on 2018/2/1.
 */

public interface APIService {

    //    public static final String DOMAIN_PASSPORT2_CHAOXING_COM = "http://passport2.chaoxing.com/";
    public static final String DOMAIN_PASSPORT2_CHAOXING_COM = "https://pan-yz.chaoxing.com/";


    @FormUrlEncoded
    @POST("xxt/loginregisternew")
    Call<ResponseBody> signInToPassport(@Field("uname") String account, @Field("code") String code, @Field("loginType") int loginType, @Field("roleSelect") boolean roleSelect);

    // https://pan-yz.chaoxing.com/api/token/uservalid
    @POST("api/token/uservalid")
    Observable<Result<User>> signInToPassport();

}
