package com.alexqzhang.print;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.alexqzhang.base.config.PictureConfig;
import com.alexqzhang.base.config.TextConfig;

public class PrintService {
    public static Bitmap print(Bitmap inputBitmap, PictureConfig pictureConfig,
                               TextConfig textConfig) {
        if (inputBitmap == null || inputBitmap.isRecycled()
            || pictureConfig == null || textConfig == null) {
            return inputBitmap;
        }

        Bitmap cropBitmap = crop(inputBitmap,
                pictureConfig.screenWidth, pictureConfig.screenHeight);

        return inputBitmap;
    }

    public static Bitmap crop(Bitmap inputBitmap, int width, int height) {
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        return inputBitmap;
    }
}
