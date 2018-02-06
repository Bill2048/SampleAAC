package com.chaoxing.sample.aac.permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Created by HUWEI on 2018/2/6.
 */

public class OkPermission {

    public interface PermissionCallback {
        void onPermissionsGranted(int requestCode, String... permissions);

        void onPermissionsDenied(int requestCode, String... permissions);
    }

    public interface OnRequestPermissionsResultCallback extends ActivityCompat.OnRequestPermissionsResultCallback, PermissionCallback {

    }

    private Object mHost;
    private String[] mPermissions;
    private String mRationale;
    private int mRequestCode;
    @StringRes
    private int mPositiveButtonText = android.R.string.ok;
    @StringRes
    private int mNegativeButtonText = android.R.string.cancel;

    private OkPermission(Object host) {
        this.mHost = host;
    }

    public static OkPermission with(Activity activity) {
        return new OkPermission(activity);
    }

    public static OkPermission with(Fragment fragment) {
        return new OkPermission(fragment);
    }

    public OkPermission permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public OkPermission rationale(String rationale) {
        this.mRationale = rationale;
        return this;
    }

    public OkPermission addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public OkPermission positveButtonText(@StringRes int positiveButtonText) {
        this.mPositiveButtonText = positiveButtonText;
        return this;
    }

    public OkPermission nagativeButtonText(@StringRes int negativeButtonText) {
        this.mNegativeButtonText = negativeButtonText;
        return this;
    }

    public void request() {
        request(mHost, mRationale, mRequestCode, mPermissions);
    }

    private static void request(final Object host, final String rationale, final int requestCode, final String... permissions) {
        if(!(host instanceof Activity))

        if(host instanceof Activity) {

        } else if(host instanceof Fragment) {

        } else if(host instanceof android.app.Fragment) {

        }
        Context context;
    }

    public static void onRequestPermissionsResult(PermissionCallback callback, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public static void requestPermissions(final Activity host, final String rationale, final int requestCode, final String... permissions) {
        request(host, rationale, requestCode, permissions);
    }

    public static void requestPermissions(final Fragment host, final String rationale, final int requestCode, final String... permissions) {
        request(host, rationale, requestCode, permissions);
    }


}
