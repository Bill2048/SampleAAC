package com.chaoxing.sample.aac.app;

import android.os.Environment;

import com.chaoxing.sample.aac.BuildConfig;

import java.io.File;

/**
 * Created by HUWEI on 2018/2/27.
 */

public final class ProductConfig {

    public static final String SP_NAME_CONFIG = "product_config";

    public static final String SP_KEY_CONFIG_UNIQUE_ID = "unique_id";

    public static String getCompanyDirectoryAbsolutePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + BuildConfig.COMPANY_DIRECTORY;
    }

    public static String getProductDirectoryAbsolutePath() {
        return getCompanyDirectoryAbsolutePath()
                + File.separator
                + BuildConfig.PRODUCT_DIRECTORY;
    }


}
