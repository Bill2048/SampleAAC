package com.chaoxing.sample.aac.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.ServiceCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.chaoxing.sample.aac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUWEI on 2018/2/6.
 */

public class OkPermission {

    public interface PermissionCallback extends ActivityCompat.OnRequestPermissionsResultCallback {
        void onPermissionsGranted(int requestCode, String... permissions);

        void onPermissionsDenied(int requestCode, String... permissions);
    }

    private Object mHost;
    private String[] mPermissions;
    private String mRationale;
    private int mRequestCode;
    private String mPositiveButtonText;
    private String mNegativeButtonText;

    private OkPermission(@NonNull Object host) {
        if (!(host instanceof PermissionCallback)) {
            throw new IllegalArgumentException("页面需要实现 PermissionCallback 接口");
        }

        this.mHost = host;
        Activity activity = getActivity(mHost);
        mPositiveButtonText = activity.getString(android.R.string.ok);
        mNegativeButtonText = activity.getString(android.R.string.cancel);
    }

    private static Activity getActivity(@NonNull Object host) {
        if (host instanceof Activity) {
            return (Activity) host;
        } else if (host instanceof Fragment) {
            return ((Fragment) host).getActivity();
        } else if (host instanceof android.app.Fragment) {
            return ((android.app.Fragment) host).getActivity();
        }
        return null;
    }

    public static OkPermission with(@NonNull Activity activity) {
        return new OkPermission(activity);
    }

    public static OkPermission with(@NonNull Fragment fragment) {
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
        this.mPositiveButtonText = getActivity(mHost).getString(positiveButtonText);
        return this;
    }

    public OkPermission positveButtonText(String positiveButtonText) {
        this.mPositiveButtonText = positiveButtonText;
        return this;
    }

    public OkPermission nagativeButtonText(@StringRes int negativeButtonText) {
        this.mNegativeButtonText = getActivity(mHost).getString(negativeButtonText);
        return this;
    }

    public OkPermission nagativeButtonText(String negativeButtonText) {
        this.mNegativeButtonText = negativeButtonText;
        return this;
    }

    public void request() {
        request(mHost, mRationale, mPositiveButtonText, mNegativeButtonText, mRequestCode, mPermissions);
    }

    private static void request(@NonNull final Object host, final String rationale, String positiveButtonText, String negativeButtonText, final int requestCode, final String... permissions) {
        Activity activity = getActivity(host);
        if (activity == null) {
            return;
        }

        if (!(host instanceof PermissionCallback)) {
            return;
        }

        final PermissionCallback callback = (PermissionCallback) host;

        List<String> deniedPermissionList = PermissionUtils.findDeniedPermissions(activity, permissions);
        if (deniedPermissionList.isEmpty()) {
            callback.onPermissionsGranted(requestCode, permissions);
            return;
        }

        boolean shouldShowRationale = false;
        if (rationale != null) {
            for (String permission : deniedPermissionList) {
                shouldShowRationale = shouldShowRationale || PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
                if (shouldShowRationale) {
                    break;
                }
            }
        }

        final String[] deniedPermissionArray = deniedPermissionList.toArray(new String[deniedPermissionList.size()]);

        if (shouldShowRationale) {
            new AlertDialog.Builder(activity).setMessage(rationale)
                    .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executePermissionsRequest(host, deniedPermissionArray, requestCode);
                        }
                    })
                    .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.onPermissionsDenied(requestCode, deniedPermissionArray);
                        }
                    })
                    .create()
                    .show();
        } else {
            executePermissionsRequest(host, deniedPermissionArray, requestCode);
        }
    }

    private static void executePermissionsRequest(@NonNull Object host, @NonNull String[] permissions, @NonNull int requestCode) {
        if (host instanceof Activity) {
            ((Activity) host).requestPermissions(permissions, requestCode);
        } else if (host instanceof Fragment) {
            ((Fragment) host).requestPermissions(permissions, requestCode);
        } else if (host instanceof android.app.Fragment) {
            ((android.app.Fragment) host).requestPermissions(permissions, requestCode);
        }
    }

    public static void onRequestPermissionsResult(@NonNull PermissionCallback callback, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> deniedPermissionList = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permissions[i]);
            }
        }

        if (deniedPermissionList.isEmpty()) {
            callback.onPermissionsGranted(requestCode, permissions);
        } else {
            callback.onPermissionsDenied(requestCode, deniedPermissionList.toArray(new String[deniedPermissionList.size()]));
        }
    }

    public static void openApplicationDetailsSettings(@NonNull Activity host, String rationale, DialogInterface.OnClickListener negativeButtonOnClickListener, final int requestCode) {
        openApplicationDetailsSettings(host, rationale, host.getString(android.R.string.ok), host.getString(android.R.string.cancel), negativeButtonOnClickListener, requestCode);
    }

    public static void openApplicationDetailsSettings(@NonNull Fragment host, String rationale, DialogInterface.OnClickListener negativeButtonOnClickListener, final int requestCode) {
        openApplicationDetailsSettings(host, rationale, host.getString(android.R.string.ok), host.getString(android.R.string.cancel), negativeButtonOnClickListener, requestCode);
    }

    public static void openApplicationDetailsSettings(@NonNull Activity host, String rationale, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener negativeButtonOnClickListener, final int requestCode) {
        openSettings(host, rationale, positiveButtonText, negativeButtonText, negativeButtonOnClickListener, requestCode);
    }

    public static void openApplicationDetailsSettings(@NonNull Fragment host, String rationale, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener negativeButtonOnClickListener, final int requestCode) {
        openSettings(host, rationale, positiveButtonText, negativeButtonText, negativeButtonOnClickListener, requestCode);
    }

    private static void openSettings(@NonNull final Object host, String rationale, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener negativeButtonOnClickListener, final int requestCode) {
        final Activity activity = getActivity(host);
        if (activity == null) {
            return;
        }

        new AlertDialog.Builder(activity).setMessage(rationale)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(host, intent, requestCode);
                    }
                })
                .setNegativeButton(negativeButtonText, negativeButtonOnClickListener)
                .create()
                .show();
    }


    private static void startActivityForResult(@NonNull Object object, Intent intent, int requestCode) {
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(intent, requestCode);
        }
    }

    public static boolean hasPermissions(@NonNull Context context, String... permissions) {
        return PermissionUtils.findDeniedPermissions(context, permissions).isEmpty();
    }

}
