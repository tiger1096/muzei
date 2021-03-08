package com.alexqzhang.print;

import android.graphics.Bitmap;

import com.alexqzhang.base.config.PictureConfig;
import com.alexqzhang.base.config.TextConfig;

public class PrintService {
    public static Bitmap print(Bitmap inputBitmap, PictureConfig pictureConfig,
                               TextConfig textConfig) {
        return inputBitmap;
    }

    public static Bitmap crop(Bitmap inputBitmap, int width, int height) {
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        return inputBitmap;
    }
}
