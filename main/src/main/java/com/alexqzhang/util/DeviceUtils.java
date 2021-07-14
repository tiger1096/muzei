package com.alexqzhang.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class DeviceUtils {
    public static File getExternalFilesDir(Context context, String folder) {
        String path = null;
        File file = new File(path + File.separator + folder);
        try {
            if (file.exists() && file.isFile()) {
                file.delete();
            }

            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            //空异常处理
        }

        return file;
    }
}
