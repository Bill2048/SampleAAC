package com.chaoxing.sample.aac.util;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;

import com.chaoxing.sample.aac.app.ProductConfig;
import com.chaoxing.sample.aac.permission.PermissionUtils;

import java.io.File;
import java.util.UUID;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class DeviceUtils {

    public static String getUniqueId(Context context) {
        String uniqueId = null;

        SharedPreferences sp = context.getSharedPreferences(ProductConfig.SP_NAME_CONFIG, Context.MODE_PRIVATE);
        File uniqueIdFile = new File(ProductConfig.getProductDirectoryAbsolutePath(), File.separator + "ui");

        if (PermissionUtils.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            uniqueId = TextUtils.read(uniqueIdFile);

            if (TextUtils.isBlank(uniqueId)) {
                uniqueId = sp.getString(ProductConfig.SP_KEY_CONFIG_UNIQUE_ID, null);
                if (!TextUtils.isBlank(uniqueId)) {
                    TextUtils.write(uniqueIdFile, uniqueId);
                }
            }

            if (TextUtils.isBlank(uniqueId)) {
                uniqueId = createUniqueId(context);
            }

        } else {
            uniqueId = sp.getString(ProductConfig.SP_KEY_CONFIG_UNIQUE_ID, null);
            if (TextUtils.isBlank(uniqueId)) {
                uniqueId = createUniqueId(context);
            }
        }

        return uniqueId;
    }

    private static String createUniqueId(Context context) {
        String uniqueId = UUID.randomUUID().toString();

        SharedPreferences sp = context.getSharedPreferences(ProductConfig.SP_NAME_CONFIG, Context.MODE_PRIVATE);
        sp.edit().putString(ProductConfig.SP_KEY_CONFIG_UNIQUE_ID, uniqueId).commit();

        File uniqueIdFile = new File(ProductConfig.getProductDirectoryAbsolutePath(), File.separator + "ui");
        if (PermissionUtils.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            TextUtils.write(uniqueIdFile, uniqueId);
        }

        return uniqueId;
    }

}
