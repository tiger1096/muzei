package com.alexqzhang.print;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alexqzhang.base.config.PictureConfig;
import com.alexqzhang.base.config.TextConfig;
import com.alexqzhang.print.utils.BitmapUtil;

public class PrintService {
    public static Bitmap print(Bitmap inputBitmap, PictureConfig pictureConfig,
                               TextConfig textConfig) {
        if (inputBitmap == null || inputBitmap.isRecycled()
            || pictureConfig == null || textConfig == null) {
            return inputBitmap;
        }

//        Log.e("alex", "inputBitmap = " + inputBitmap.getWidth() + ", " + inputBitmap.getHeight());
//        Log.e("alex", "screenWidth = " + pictureConfig.screenWidth + ", " + pictureConfig.screenHeight);
        Bitmap cropBitmap = crop(inputBitmap,
                pictureConfig.screenWidth, pictureConfig.screenHeight);
        Bitmap outputBitmap = write(cropBitmap, pictureConfig, textConfig);
//        Log.e("alex", "PrintService print");

        return outputBitmap;
    }

    public static Bitmap crop(Bitmap inputBitmap, int width, int height) {
        if (!BitmapUtil.isIllegal(inputBitmap) || width * height <= 0) {
            return inputBitmap;
        }

        int inputWidth = inputBitmap.getWidth();
        int inputHeight = inputBitmap.getHeight();

        int outputWidth = inputWidth;
        int outputHeight = inputHeight;
        int startX = 0;
        int startY = 0;
        if (width * inputHeight == height * inputWidth) {
            return inputBitmap;
        } else if (width * inputHeight > height * inputWidth) {
            outputHeight = height * inputWidth / width;
            startY = (inputHeight - outputHeight) / 2;
        } else {
            outputWidth = width * inputHeight / height;
            startX = (inputWidth - outputWidth) / 2;
        }

        return Bitmap.createBitmap(inputBitmap, startX, startY, outputWidth, outputHeight);
    }

    public static Bitmap write(Bitmap inputBitmap, PictureConfig pictureConfig,
                               TextConfig textConfig) {
        Bitmap outputBitmap = inputBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(outputBitmap);
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(120);
        paint.setColor(Color.BLACK);

        String text = textConfig.texts.get(0);
        canvas.drawText(text, pictureConfig.blankX, pictureConfig.blankY, paint);
        canvas.save();
        canvas.restore();
        return outputBitmap;

//        for (int i = 0; i < textConfig.textNum; i ++) {
//            String text = textConfig.texts.get(i);
//
//            paint.setTextSize(textConfig.fonts.get(i).size);
//            Rect bounds = new Rect();
//            paint.getTextBounds(text, 0, text.length(), bounds);
//            canvas.drawText(text, pictureConfig.blankX + bounds.bottom,
//                    pictureConfig.blankY + bounds.bottom , paint);
//
//            Log.e("alex", "bounds = " + bounds.left + ", "
//                    + bounds.top + ", " + bounds.right + ", " + bounds.bottom);
//        }
    }
}
