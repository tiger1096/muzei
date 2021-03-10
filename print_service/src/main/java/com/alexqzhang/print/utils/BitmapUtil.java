package com.alexqzhang.print.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {

    public static Bitmap getBitmapFromAssets(Context context, String path) {
        if (context == null || TextUtils.isEmpty(path)) {
            return null;
        }

        Bitmap bitmap = null;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(path);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean isIllegal(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled();
    }

    public static void saveBitmap2PNG(Bitmap bitmap, Context context, String path) {
        if (!isIllegal(bitmap) || TextUtils.isEmpty(path)) {
            return ;
        }

        String filePath = context.getExternalFilesDir(null).getAbsolutePath() + File.separator + path;

        Log.e("alex", "filePath = " + filePath);

        FileOutputStream out = null;
        try {
            File file = new File(context.getExternalFilesDir(null).getAbsolutePath(), path);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(filePath);
            if (bitmap != null) {
                // PNG formats is lossless, thus will ignore the quality setting
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
            }
            out.close();
        } catch (Exception e) {
            Log.e("alex", e.getMessage());
        } catch (OutOfMemoryError error) {
            Log.e("alex", error.getMessage());
        }
    }
}
