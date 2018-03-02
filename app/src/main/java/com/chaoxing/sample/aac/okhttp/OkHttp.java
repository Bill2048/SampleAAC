package com.chaoxing.sample.aac.okhttp;

import android.content.Context;
import android.os.LocaleList;

import com.chaoxing.sample.aac.BuildConfig;
import com.chaoxing.sample.aac.util.DeviceUtils;

import java.security.cert.CertificateException;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class OkHttp {

    private OkHttp() {
    }

    private static String sUserAgent = System.getProperty("http.agent");

    public static void initUserAgent(Context context) {
//        IOS  useragent说明：
//        "{1}/{2}_{3}_{4}_ios_phone_{5} ({6}; {7} {8}; {9})AppleWebKit/601.1.46_Mobile_{10}"
//        {1}  :  产品代码，即BoundleName
//        {2}  :  公司产品线，学习通产品线指定为“ChaoXingStudy”，包括所有学习通定制
//        {3}  :  产品定制客户端id，整型数字
//        {4}  :  客户端版本号，发布的第几版
//        {5}  :  客户端内部开发版本号，一般为提交日期
//        {6}  :  设备名称，  iphone、ipad、itouch等
//        {7}  :  操作系统名称， 如，iOS
//        {8}  :  操作系统版本号， 如， 10.3.3
//        {9}  :  本地化信息，通常只含有语言和国家，如，zh_CN
//        {10} :  由客户端随机生成的终端号，当用户有卸载或重装操作系统的行为，则会重新生成。如，-1479899687365026026
//
//        IOS 示例：
//        ChaoXingStudy/ChaoXingStudy_3_3.0.1_ios_phone_201709062150 (iPhone; iOS 10.3.3; zh_CN)AppleWebKit/601.1.46_Mobile_-1479899687365026026

        try {
            Object[] args = new String[11];
            args[0] = "" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
            args[1] = "ChaoXingStudy";
            args[2] = "" + BuildConfig.PRODUCT_ID;// + ProductConfig.productId;
            args[3] = "" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            args[4] = "" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            args[5] = "" + BuildConfig.USER_AGENT_VERSION;//+ BuildConfig.STUDY_AGENT_CODE;
            args[6] = "" + android.os.Build.MODEL;
            args[7] = "Android";
            args[8] = "" + android.os.Build.VERSION.RELEASE;
            args[9] = "" + getLanguage();
            args[10] = "" + DeviceUtils.getUniqueId(context);// + getDeviceUniqueId();
            sUserAgent = System.getProperty("http.agent") + String.format(" %s/%s_%s_%s_android_phone_%s_%s (%s; %s %s; %s)_%s", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUserAgent() {
        return sUserAgent;
    }

    public static String getLanguage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return LocaleList.getAdjustedDefault().get(0).toString();
        } else {
            return Locale.getDefault().toString();
        }
    }

    public static OkHttpClient.Builder setTrustHttpClient(OkHttpClient.Builder builder) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder;
    }

}
