package com.chaoxing.sample.aac.app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.chaoxing.sample.aac.permission.OkPermission;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HUWEI on 2018/2/2.
 */

public class BaseActivity extends AppCompatActivity implements OkPermission.PermissionCallback {

    private PermissionsRequester mPermissionsRequester;

    protected boolean mayRequestPermission(final String[] permission, String alert, PermissionsRequester requester) {
        if (PermissionsChecker.checkSelfPermissions(this, permission)) {
            return true;
        }

        if (requester == null) {
            requester = new PermissionsRequester(permission, 0);
        }

        mPermissionsRequester = requester;

        if (mPermissionsRequester.getPermissions().length == 1) {
            if (shouldShowRequestPermissionRationale(mPermissionsRequester.getPermissions()[0])) {
                mPermissionsRequester.alert(this);
            } else {
                requestPermissions(mPermissionsRequester.getPermissions(), mPermissionsRequester.getRequestCode());
            }
        } else {
            requestPermissions(mPermissionsRequester.getPermissions(), mPermissionsRequester.getRequestCode());
        }

        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (mPermissionsRequester != null && mPermissionsRequester.getRequestCode() == requestCode) {
//            if(!mPermissionsRequester.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
//
//            }
//        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, String... permissions) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, String... permissions) {

    }
}
