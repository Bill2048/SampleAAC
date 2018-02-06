package com.chaoxing.sample.aac.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUWEI on 2018/2/6.
 */

public class PermissionUtils {

    static List<String> findDeniedPermissions(Context context, String... permissions) {

        List<String> denyPermissions = new ArrayList<>();

        if (context == null) {
            return denyPermissions;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    denyPermissions.add(permission);
                }
            }
        }

        return denyPermissions;
    }

}
