package com.chaoxing.sample.aac.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by HUWEI on 2018/2/2.
 */

public class PermissionsChecker {

    public static boolean checkSelfPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkSelfPermission(Context context, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

}
