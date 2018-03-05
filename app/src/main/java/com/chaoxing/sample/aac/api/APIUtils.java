package com.chaoxing.sample.aac.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HUWEI on 2018/3/5.
 */

public class APIUtils {

    private static final String TOKEN = "4faa8662c59590c6f43ae9fe5b002b42";
    private static final String DESKey = "Z(AfY@XS";
    private static final String LOGIN_Key = "L(AfY@DE";
    private static final String VALI_TOKEN = "de2ffb63dea8a76f056756e174f68bad";

    public static String getToken() {
        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append("token=").append(TOKEN).append("&_time=").append(System.currentTimeMillis());
        return new StringBuilder(tokenBuilder).append("&inf_enc=").append(md5(tokenBuilder.toString())).toString();
    }

    public static String md5(String str) {
        if (str != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte digest[] = md.digest();
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < digest.length; i++) {
                    strBuilder.append(String.format("%02x", digest[i]));
                }
                return strBuilder.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String md516(String str) {
        return md5(str).substring(8, 24);
    }


}
