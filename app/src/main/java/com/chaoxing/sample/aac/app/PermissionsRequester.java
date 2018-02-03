package com.chaoxing.sample.aac.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by HUWEI on 2018/2/2.
 */

public class PermissionsRequester {

    protected String[] mPermissions;
    protected int mRequestCode;

    public String[] getPermissions() {
        return mPermissions;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public PermissionsRequester(String[] permissions, int requestCode) {
        mPermissions = permissions;
        mRequestCode = requestCode;
    }

    public void alert(Activity activity) {
        activity.requestPermissions(mPermissions, 0);
    }

    public void alert(Fragment fragment) {
        fragment.requestPermissions(mPermissions, 0);
    }

    public void onRequestSuccess() {

    }

    public void onRequestFailed() {

    }

    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        return false;
    }

}
