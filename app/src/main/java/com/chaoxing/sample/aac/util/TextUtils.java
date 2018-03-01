package com.chaoxing.sample.aac.util;

import java.io.File;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by HUWEI on 2018/3/1.
 */

public class TextUtils {

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static void write(File file, String text) {
        if (file == null) {
            return;
        }

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        BufferedSink bufferedSink = null;

        try {
            bufferedSink = Okio.buffer(Okio.sink(file));
            bufferedSink.writeUtf8(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedSink != null) {
                try {
                    bufferedSink.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(String pathname, String text) {
        write(new File(pathname), text);
    }

    public static String read(File file) {
        String text = null;
        if (file == null || !file.exists()) {
            return text;
        }

        BufferedSource bufferedSource = null;
        try {
            bufferedSource = Okio.buffer(Okio.source(file));
            text = bufferedSource.readUtf8();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedSource != null) {
                try {
                    bufferedSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return text;
    }

    public static String read(String pathname) {
        return read(new File(pathname));
    }

}
