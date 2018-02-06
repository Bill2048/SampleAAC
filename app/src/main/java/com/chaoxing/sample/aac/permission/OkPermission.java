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
import android.support.v4.app.Fragment;
import android.support.v4.app.ServiceCompat;
import android.support.v7.app.AlertDialog;

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

    private OkPermission(Object host) {
        if (host instanceof PermissionCallback) {
            throw new IllegalArgumentException("页面需要实现 PermissionCallback 接口");
        }

        this.mHost = host;
        Activity activity = getActivity(mHost);
        mPositiveButtonText = activity.getString(android.R.string.ok);
        mNegativeButtonText = activity.getString(android.R.string.cancel);
    }

    private static Activity getActivity(Object host) {
        if (host instanceof Activity) {
            return (Activity) host;
        } else if (host instanceof Fragment) {
            return ((Fragment) host).getActivity();
        } else if (host instanceof android.app.Fragment) {
            return ((android.app.Fragment) host).getActivity();
        }
        return null;
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

    private static void request(final Object host, final String rationale, String positiveButtonText, String negativeButtonText, final int requestCode, final String... permissions) {
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

    private static void executePermissionsRequest(Object host, String[] permissions, int requestCode) {
        if (host instanceof Activity) {
            ((Activity) host).requestPermissions(permissions, requestCode);
        } else if (host instanceof Fragment) {
            ((Fragment) host).requestPermissions(permissions, requestCode);
        } else if (host instanceof android.app.Fragment) {
            ((android.app.Fragment) host).requestPermissions(permissions, requestCode);
        }
    }

    public static void onRequestPermissionsResult(PermissionCallback callback, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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

    public static boolean checkDeniedPermissionsNeverAskAgain(final Object object, String rationale, String... deniedPerms) {
        return checkDeniedPermissionsNeverAskAgain(object, rationale, android.R.string.ok, android.R.string.cancel, null, deniedPerms);
    }


    public static boolean checkDeniedPermissionsNeverAskAgain(final Object object, String rationale, @StringRes int positiveButton, @StringRes int negativeButton, @Nullable DialogInterface.OnClickListener negativeButtonOnClickListener, String... deniedPerms) {

    }
// https://developer.android.com/guide/topics/security/permissions.html?hl=zh-cn
    public static boolean checkDeniedPermissionsNeverAskAgain(final Object object, String rationale,  String positiveButtonText, String negativeButtonText, @Nullable DialogInterface.OnClickListener negativeButtonOnClickListener, String... deniedPerms) {
        boolean shouldShowRationale;
        for (String perm : deniedPerms) {
            shouldShowRationale = PermissionUtils.shouldShowRequestPermissionRationale(object, perm);

            if (!shouldShowRationale) {
                final Activity activity = Utils.getActivity(object);
                if (null == activity) {
                    return true;
                }

                new AlertDialog.Builder(activity).setMessage(rationale)
                        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                intent.setData(uri);
                                startAppSettingsScreen(object, intent);
                            }
                        })
                        .setNegativeButton(negativeButton, negativeButtonOnClickListener)
                        .create()
                        .show();

                return true;
            }
        }

        return false;
    }


    private static void startAppSettingsScreen(Object object, Intent intent) {
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(intent, SETTINGS_REQ_CODE);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(intent, SETTINGS_REQ_CODE);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(intent, SETTINGS_REQ_CODE);
        }
    }

}
