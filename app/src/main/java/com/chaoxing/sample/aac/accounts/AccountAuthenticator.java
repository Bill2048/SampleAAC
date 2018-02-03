package com.chaoxing.sample.aac.accounts;

import android.content.Context;

import com.chaoxing.sample.aac.APIService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by HUWEI on 2018/2/1.
 */

public class AccountAuthenticator {

    private static AccountAuthenticator sInstance;

    private Context mContext;

    public static AccountAuthenticator get(Context context) {
        if (sInstance == null) {
            synchronized (AccountAuthenticator.class) {
                if (sInstance == null) {
                    sInstance = new AccountAuthenticator(context);
                }
            }
        }
        return sInstance;
    }

    public AccountAuthenticator() {
    }

    private AccountAuthenticator(Context context) {
        mContext = context.getApplicationContext();
    }

    public String confirmCredentials(Credential credential) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN_PASSPORT2_CHAOXING_COM) // 设置网络请求的Url地址
//                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();
        try {
            APIService apiService = retrofit.create(APIService.class);
            Call<ResponseBody> call = apiService.signInToPassport("huwei@chaoxing.com", "098909", 0, true);

            Response<ResponseBody> response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
