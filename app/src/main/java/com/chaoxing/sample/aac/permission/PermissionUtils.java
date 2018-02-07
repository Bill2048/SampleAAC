package com.chaoxing.sample.aac.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUWEI on 2018/2/6.
 */

public class PermissionUtils {

    public static List<String> findDeniedPermissions(Context context, String... permissions) {
        List<String> denyPermissions = new ArrayList<>();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return denyPermissions;
        }

        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(permission);
                continue;
            }

            String op = AppOpsManagerCompat.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) {
                continue;
            }

            result = AppOpsManagerCompat.noteProxyOp(context, op, context.getPackageName());
            if (result != AppOpsManagerCompat.MODE_ALLOWED) {
                denyPermissions.add(permission);
            }
        }

        return denyPermissions;
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String permission) {
        return shouldShowRequestPermissionRationale(fragment.getActivity(), permission);
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        return activity.shouldShowRequestPermissionRationale(permission);
    }

}
